package com.br.consultas.model;

import jakarta.persistence.*;

@Entity
@Table(name = "usuarios")
public class Usuario {
	
	@Id
    private Long id;
	
	@OneToOne
    @MapsId // indica que o id deste Usuario vem da Pessoa
    @JoinColumn(name = "id")
    private Pessoa pessoa;

	private String email;
	
	private String login;
	
	private String senha;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
}
