package com.br.consultas.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "pacientes")
public class Paciente extends Pessoa {

    @OneToMany(mappedBy = "paciente", cascade = CascadeType.ALL)
    private List<Consulta> consultas;

    // Getters e Setters
    public List<Consulta> getConsultas() { 
    	return consultas; 
    }
    
    public void setConsultas(List<Consulta> consultas) { 
    	this.consultas = consultas; 
    }
}
