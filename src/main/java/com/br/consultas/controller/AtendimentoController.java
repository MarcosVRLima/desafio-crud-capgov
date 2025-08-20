package com.br.consultas.controller;

import com.br.consultas.dao.AtendimentoDAO;
import com.br.consultas.model.Atendimento;
import java.util.List;

public class AtendimentoController {
    
	private final AtendimentoDAO atendimentoDAO;

    public AtendimentoController() {
        this.atendimentoDAO = new AtendimentoDAO();
    }

    public void salvarAtendimento(Atendimento atendimento) {
        atendimentoDAO.save(atendimento);
    }

    public void atualizarAtendimento(Atendimento atendimento) {
        atendimentoDAO.update(atendimento);
    }

    public void excluirAtendimento(Atendimento atendimento) {
        atendimentoDAO.delete(atendimento);
    }

    public Atendimento buscarAtendimentoPorId(Long id) {
        return atendimentoDAO.findById(id);
    }

    public List<Atendimento> listarAtendimentos() {
        return atendimentoDAO.findAll();
    }
}
