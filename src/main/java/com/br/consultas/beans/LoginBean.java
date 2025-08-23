package com.br.consultas.beans;

import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;

import java.io.Serializable;

import com.br.consultas.model.Usuario;
import com.br.consultas.utils.HashUtil;
import com.br.consultas.controller.UsuarioController;

@Named("loginBean")
@SessionScoped
public class LoginBean implements Serializable {
	
	private static final long serialVersionUID = 1L;

    private String email;
    private String senha;

    private Usuario novoUsuario = new Usuario();
    private Usuario usuarioLogado;

    private UsuarioController usuarioController = new UsuarioController();

    // Login
    public String logar() {
        Usuario u = usuarioController.buscarUsuarioPorEmail(email);
        
        //System.out.println(u.getEmail());

        if (u != null && senha != null && HashUtil.checkPassword(senha, u.getSenha())) {
        	// Cria sessão
            FacesContext.getCurrentInstance().getExternalContext()
                        .getSessionMap().put("usuarioLogado", u);

            return "/views/home.xhtml?faces-redirect=true";
        }

        FacesContext.getCurrentInstance().addMessage(null,
            new FacesMessage(FacesMessage.SEVERITY_ERROR,
                             "Login inválido", "E-mail ou senha incorretos."));
        return null; // permanece na mesma página para mostrar a mensagem
    }
    
    // Redirecionar
    public String redirecionarSeLogado() {
        if (usuarioLogado != null) {
            return "home.xhtml?faces-redirect=true";
        }
        return null; // continua na página de login
    }
    
    // método de logout
    public String logout() {
    	FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        usuarioLogado = null;
        return "index.xhtml?faces-redirect=true"; // redireciona para a tela de login
    }


    // Getters e Setters
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }

    public Usuario getNovoUsuario() { return novoUsuario; }
    public void setNovoUsuario(Usuario novoUsuario) { this.novoUsuario = novoUsuario; }

    public Usuario getUsuarioLogado() { return usuarioLogado; }
}
