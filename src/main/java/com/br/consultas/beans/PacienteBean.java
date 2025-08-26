package com.br.consultas.beans;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.br.consultas.controller.PacienteController;
import com.br.consultas.controller.PessoaController;
import com.br.consultas.controller.UsuarioController;

import com.br.consultas.model.Paciente;
import com.br.consultas.model.Pessoa;
import com.br.consultas.model.Usuario;

import com.br.consultas.utils.HashUtil;

import jakarta.annotation.PostConstruct;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;

@Named("pacienteBean")
@ViewScoped
public class PacienteBean implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Pessoa pessoa = new Pessoa();
	private PessoaController pessoaController = new PessoaController();
	
	private Usuario usuario = new Usuario();
	private UsuarioController usuarioController = new UsuarioController();
	
	private List<Pessoa> lista;
	
	private PacienteController pacienteController = new PacienteController();
	
	// Salvar ou atualizar pessoa
    public String salvar() {
    	Paciente paciente = new Paciente();
    	pacienteController.salvarPaciente(paciente);
    	
    	Map<String, Long> role = new HashMap<>();
    	role.put("PACIENTE", paciente.getId());
    	pessoa.setIdsRoles(role);
    	pessoaController.salvarPessoa(pessoa);
    		
        usuario.setId(pessoa.getId());
        usuario.setSenha(HashUtil.encryptPassword(usuario.getSenha()));
        usuarioController.salvarUsuario(usuario);
        
        // limpa o formulário
        pessoa = new Pessoa();
        usuario = new Usuario();
        
        return "/views/pages/pacientes.xhtml?faces-redirect=true";
    }
    
    // Redireciona para a página de edição
    public String editar(Pessoa p) {
    	// guarda a pessoa no FlashScope
        FacesContext.getCurrentInstance()
                    .getExternalContext()
                    .getFlash()
                    .put("pessoaSelecionada", p);
        
        return "/views/pages/pacientes/editar.xhtml?faces-redirect=true";
    }
    
    // Atualiza o atendente no banco
    public String atualizar() {
        // atualiza a pessoa
        pessoaController.atualizarPessoa(pessoa);
        usuarioController.atualizarUsuario(usuario);

        // limpa o formulário
        pessoa = new Pessoa();
        usuario = new Usuario();
        
        // volta para a lista
        return "/views/pages/pacientes.xhtml?faces-redirect=true";
    }
    
    public String deletar(Pessoa p) {
    	
    	Paciente paciente = pacienteController.buscarPacientePorId(p.getIdsRoles().get("PACIENTE"));
    	pacienteController.excluirPaciente(paciente);
    	
    	p.getIdsRoles().remove("PACIENTE");
    	pessoaController.atualizarPessoa(p);
    	
    	pessoa = new Pessoa();
    	usuario = new Usuario();
    	
    	return "/views/pages/pacientes.xhtml?faces-redirect=true";
    }
    
    // Redireciona para página de criação de atendente
    public String novo() {
        pessoa = new Pessoa();
        usuario = new Usuario();
        return "/views/pages/pacientes/novo.xhtml?faces-redirect=true";
    }
    
    @PostConstruct
    public void init() {
    	String viewId = FacesContext.getCurrentInstance().getViewRoot().getViewId();

        if (viewId.contains("pacientes.xhtml")) {
            lista = pessoaController.listarPessoasPorTipo("PACIENTE");
        } 
        else if (viewId.contains("editar.xhtml")) {
            // Recupera do FlashScope
            Pessoa selecionada = (Pessoa) FacesContext.getCurrentInstance()
                                .getExternalContext()
                                .getFlash()
                                .get("pessoaSelecionada");

            if (selecionada != null) {
                this.pessoa = selecionada;
                this.usuario = usuarioController.buscarUsuarioPorId(pessoa.getId());
                System.out.println("Pessoa carregada do Flash: " + pessoa.getId());
            }
        }
    }

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<Pessoa> getLista() {
		return lista;
	}

	public void setLista(List<Pessoa> lista) {
		this.lista = lista;
	}

}
