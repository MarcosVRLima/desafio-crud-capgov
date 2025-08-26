package com.br.consultas.beans;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.br.consultas.controller.MedicoController;
import com.br.consultas.controller.PessoaController;
import com.br.consultas.controller.UsuarioController;

import com.br.consultas.model.Medico;
import com.br.consultas.model.Pessoa;
import com.br.consultas.model.Usuario;

import com.br.consultas.utils.HashUtil;

import jakarta.annotation.PostConstruct;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;

@Named("medicoBean")
@ViewScoped
public class MedicoBean implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Pessoa pessoa = new Pessoa();
	private PessoaController pessoaController = new PessoaController();
	
	private Usuario usuario = new Usuario();
	private UsuarioController usuarioController = new UsuarioController();
	
	private Medico medico = new Medico();
	private MedicoController medicoController = new MedicoController();
	
	// Salvar pessoa
    public String salvar() {
    	medicoController.salvarMedico(medico);
    	
    	Map<String, Long> role = new HashMap<>();
    	role.put("MEDICO", medico.getId());
    	pessoa.setIdsRoles(role);
    	pessoaController.salvarPessoa(pessoa);
    	
        usuario.setId(pessoa.getId());
        usuario.setSenha(HashUtil.encryptPassword(usuario.getSenha()));
        usuarioController.salvarUsuario(usuario);
        
        // limpa o formulário
        pessoa = new Pessoa();
        usuario = new Usuario();
        medico = new Medico();
        
        return "/views/pages/atendentes.xhtml?faces-redirect=true";
    }
    
    // Atualiza o medico no banco
    public String atualizar() {
        // atualiza a pessoa
        pessoaController.atualizarPessoa(pessoa); // supondo que salvarPessoa atualiza se o ID já existe
        
        usuarioController.atualizarUsuario(usuario);
        
        medicoController.atualizarMedico(medico);

        // limpa o formulário
        pessoa = new Pessoa();
        usuario = new Usuario();
        medico = new Medico();
        
        // volta para a lista
        return "/views/pages/medicos.xhtml?faces-redirect=true";
    }
    
    public String deletar(Pessoa p) {
        //System.out.println(pessoa.getNome());
    	
    	medicoController.excluirMedico(medico);
    	
    	p.getIdsRoles().remove("MEDICO");
    	pessoaController.atualizarPessoa(p);
    	
    	pessoa = new Pessoa();
    	usuario = new Usuario();
    	medico = new Medico();
    	
    	return "/views/pages/medicos.xhtml?faces-redirect=true";
    }
	
	// Redireciona para página de criação de atendente
    public String novo() {
        pessoa = new Pessoa();
        usuario = new Usuario();
        medico = new Medico();
        
        return "/views/pages/medicos/novo.xhtml?faces-redirect=true";
    }
    
    // Redireciona para a página de edição
    public String editar(Pessoa p) {
    	// guarda a pessoa no FlashScope
        FacesContext.getCurrentInstance()
                    .getExternalContext()
                    .getFlash()
                    .put("pessoaSelecionada", p);
        
        return "/views/pages/medicos/editar.xhtml?faces-redirect=true";
    }
    
    @PostConstruct
    public void init() {
    	String viewId = FacesContext.getCurrentInstance().getViewRoot().getViewId();

        if (viewId.contains("medicos.xhtml")) {
            lista = pessoaController.listarPessoasPorTipo("MEDICO");
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
                this.medico = medicoController.buscarMedicoPorId(pessoa.getIdsRoles().get("MEDICO"));
                
                System.out.println("Pessoa carregada do Flash: " + pessoa.getId());
            }
        }
    }
	
	private List<Pessoa> lista;

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

	public Medico getMedico() {
		return medico;
	}

	public void setMedico(Medico medico) {
		this.medico = medico;
	}

	public List<Pessoa> getLista() {
		return lista;
	}

	public void setLista(List<Pessoa> lista) {
		this.lista = lista;
	}
	
	
}
