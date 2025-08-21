package com.br.consultas.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "pacientes")
public class Paciente{
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "paciente", cascade = CascadeType.ALL)
    private List<Agendamento> agendamento;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<Agendamento> getAgendamento() {
		return agendamento;
	}

	public void setAgendamento(List<Agendamento> agendamento) {
		this.agendamento = agendamento;
	}
}
