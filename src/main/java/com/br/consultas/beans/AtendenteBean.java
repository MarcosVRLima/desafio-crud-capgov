package com.br.consultas.beans;

import jakarta.annotation.PostConstruct;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.br.consultas.controller.AtendenteController;
import com.br.consultas.controller.PessoaController;
import com.br.consultas.controller.UsuarioController;
import com.br.consultas.model.Atendente;
import com.br.consultas.model.Pessoa;
import com.br.consultas.model.Usuario;
import com.br.consultas.utils.HashUtil;

@Named("atendenteBean")
@ViewScoped
public class AtendenteBean implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Pessoa pessoa = new Pessoa();
	private PessoaController pessoaController = new PessoaController();
	
	private Usuario usuario = new Usuario();
	private UsuarioController usuarioController = new UsuarioController();

	private AtendenteController atendenteController = new AtendenteController();

	private List<Pessoa> lista;

    // Redireciona para página de criação de atendente
    public String novo() {
        pessoa = new Pessoa();
        usuario = new Usuario();
        return "/views/pages/atendentes/novo.xhtml?faces-redirect=true";
    }
    
    // Salvar ou atualizar pessoa
    public String salvar() {
    	Atendente atendente = new Atendente();
    	atendenteController.salvarAtendente(atendente);
    	
    	Map<String, Long> role = new HashMap<>();
    	role.put("ATENDENTE", atendente.getId());
    	pessoa.setIdsRoles(role);
    	pessoaController.salvarPessoa(pessoa);
    	
    	
        usuario.setId(pessoa.getId());
        usuario.setSenha(HashUtil.encryptPassword(usuario.getSenha()));
        usuarioController.salvarUsuario(usuario);
        
        // limpa o formulário
        pessoa = new Pessoa();
        usuario = new Usuario();
        
        return "/views/pages/atendentes.xhtml?faces-redirect=true";
    }
    
    // Redireciona para a página de edição
    public String editar(Pessoa p) {
    	// guarda a pessoa no FlashScope
        FacesContext.getCurrentInstance()
                    .getExternalContext()
                    .getFlash()
                    .put("pessoaSelecionada", p);
        
        return "/views/pages/atendentes/editar.xhtml?faces-redirect=true";
    }
    
    // Atualiza o atendente no banco
    public String atualizar() {
        // atualiza a pessoa
        pessoaController.atualizarPessoa(pessoa); // supondo que salvarPessoa atualiza se o ID já existe
        
        usuarioController.atualizarUsuario(usuario);

        // limpa o formulário
        pessoa = new Pessoa();
        usuario = new Usuario();
        
        // volta para a lista
        return "/views/pages/atendentes.xhtml?faces-redirect=true";
    }
    
    public String deletar(Pessoa p) {
        //System.out.println(pessoa.getNome());
    	
    	Atendente atendente = atendenteController.buscarAtendentePorId(p.getIdsRoles().get("ATENDENTE"));
    	atendenteController.excluirAtendente(atendente);
    	
    	p.getIdsRoles().remove("ATENDENTE");
    	pessoaController.atualizarPessoa(p);
    	
    	pessoa = new Pessoa();
    	usuario = new Usuario();
    	
    	return "/views/pages/atendentes.xhtml?faces-redirect=true";
    }
  
    @PostConstruct
    public void init() {
    	String viewId = FacesContext.getCurrentInstance().getViewRoot().getViewId();

        if (viewId.contains("atendentes.xhtml")) {
            lista = pessoaController.listarPessoasPorTipo("ATENDENTE");
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
	

}
