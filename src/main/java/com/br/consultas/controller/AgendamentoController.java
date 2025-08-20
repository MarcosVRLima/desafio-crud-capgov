package com.br.consultas.controller;

import com.br.consultas.dao.AgendamentoDAO;
import com.br.consultas.model.Agendamento;
import java.util.List;

public class AgendamentoController {
    
	private final AgendamentoDAO agendamentoDAO;

    public AgendamentoController() {
        this.agendamentoDAO = new AgendamentoDAO();
    }

    public void salvarAgendamento(Agendamento agendamento) {
        agendamentoDAO.save(agendamento);
    }

    public void atualizarAgendamento(Agendamento agendamento) {
        agendamentoDAO.update(agendamento);
    }

    public void excluirAgendamento(Agendamento agendamento) {
        agendamentoDAO.delete(agendamento);
    }

    public Agendamento buscarAgendamentoPorId(Long id) {
        return agendamentoDAO.findById(id);
    }

    public List<Agendamento> listarAgendamentos() {
        return agendamentoDAO.findAll();
    }
}
