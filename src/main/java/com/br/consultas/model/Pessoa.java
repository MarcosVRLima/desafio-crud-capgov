package com.br.consultas.model;

import jakarta.persistence.*;
import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name = "pessoas")
public class Pessoa{

	@Id
    private String id;

	@Column(nullable = false, length = 100)
    private String nome;

	@Column(unique = true, nullable = false, length = 14)
    private String cpf;

	@Column(length = 15)
    private String telefone;
    
    // Map de roles: chave = tipo do papel, valor = ID do registro do papel
    @ElementCollection
    @CollectionTable(name = "pessoa_roles", joinColumns = @JoinColumn(name = "pessoa_id"))
    @MapKeyColumn(name = "tipo_role") // MEDICO, PACIENTE, ATENDENTE
    @Column(name = "id_role")
    private Map<String, Long> idsRoles = new HashMap<>();

    // Getters e Setters
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public Map<String, Long> getIdsRoles() {
		return idsRoles;
	}

	public void setIdsRoles(Map<String, Long> idsRoles) {
		this.idsRoles = idsRoles;
	}
}
