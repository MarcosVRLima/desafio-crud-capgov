package com.br.consultas.beans;

import com.br.consultas.model.Pessoa;
import com.br.consultas.model.Usuario;

import jakarta.inject.Named;
import jakarta.enterprise.context.RequestScoped;

import com.br.consultas.controller.PessoaController;
import com.br.consultas.controller.UsuarioController;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
//import java.util.List;

import com.br.consultas.utils.HashUtil;


@Named("cadastroBean")
@RequestScoped
public class CadastroBean implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Pessoa pessoa = new Pessoa();
	private PessoaController pessoaController = new PessoaController();
	
	private Usuario usuario = new Usuario();
	private UsuarioController usuarioController = new UsuarioController();
	
//	private List<Pessoa> pessoas;

	public Pessoa getPessoa()  {
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

	// Salvar ou atualizar pessoa
    public String salvar() {
    	Map<String, Long> role = new HashMap<>();
    	role.put("ADMIN", -1L);
    	pessoa.setIdsRoles(role);
    	System.out.println(pessoa.getIdsRoles().containsKey("ADMIN"));
    	pessoaController.salvarPessoa(pessoa);
    	
    	
        usuario.setId(pessoa.getId());
        usuario.setSenha(HashUtil.encryptPassword(usuario.getSenha()));
        usuarioController.salvarUsuario(usuario);
        
        // limpa o formulário
        pessoa = new Pessoa();
        usuario = new Usuario();
        
        return "listar-pessoas.xhtml?faces-redirect=true";
    }
//    
//    // Buscar todas pessoas
//    public List<Pessoa> getPessoas() {
//        if (pessoas == null) {
//            pessoas = pessoaController.listarPessoas();
//        }
//        return pessoas;
//    }
//
//    // Carregar pessoa para edição
//    public String editar(Pessoa p) {
//        this.pessoa = p;
//        return "cadastrar-pessoa.xhtml";
//    }
//
//    // Deletar pessoa
//    public void deletar(Pessoa p) {
//        pessoaController.excluirPessoa(p);
//        pessoas = null; // força recarregar a lista
//    }

}
