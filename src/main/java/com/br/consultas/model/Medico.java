package com.br.consultas.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "medicos")
public class Medico{
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	private String especialidade;

    @OneToMany(mappedBy = "medico", cascade = CascadeType.ALL)
    private List<Agendamento> agendamento;

    // Getters e Setters
	public String getEspecialidade() {
		return especialidade;
	}

	public void setEspecialidade(String especialidade) {
		this.especialidade = especialidade;
	}

	public List<Agendamento> getAgendamento() {
		return agendamento;
	}

	public void setAgendamento(List<Agendamento> agendamento) {
		this.agendamento = agendamento;
	}
}
