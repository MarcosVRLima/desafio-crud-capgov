package com.br.consultas.controller;

import com.br.consultas.dao.PacienteDAO;
import com.br.consultas.model.Paciente;
import java.util.List;

public class PacienteController {
    
	private final PacienteDAO pacienteDAO;

    public PacienteController() {
        this.pacienteDAO = new PacienteDAO();
    }

    public void salvarPaciente(Paciente paciente) {
        pacienteDAO.save(paciente);
    }

    public void atualizarPaciente(Paciente paciente) {
        pacienteDAO.update(paciente);
    }

    public void excluirPaciente(Paciente paciente) {
        pacienteDAO.delete(paciente);
    }

    public Paciente buscarPacientePorId(Long id) {
        return pacienteDAO.findById(id);
    }

    public List<Paciente> listarPacientes() {
        return pacienteDAO.findAll();
    }
}
