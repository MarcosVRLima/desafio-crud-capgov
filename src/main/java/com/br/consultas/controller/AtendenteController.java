package com.br.consultas.controller;

import com.br.consultas.dao.AtendenteDAO;
import com.br.consultas.model.Atendente;

import java.util.List;

public class AtendenteController {
	
	private final AtendenteDAO atendenteDAO;

	public AtendenteController() {
		this.atendenteDAO = new AtendenteDAO();
	}
	
	public void salvarAtendente(Atendente atendente) {
        atendenteDAO.save(atendente);
    }

    public void atualizarAtendente(Atendente atendente) {
        atendenteDAO.update(atendente);
    }

    public void excluirAtendente(Atendente atendente) {
    	atendenteDAO.delete(atendente);
    }

    public Atendente buscarAtendentePorId(Long id) {
        return atendenteDAO.findById(id);
    }

    public List<Atendente> listarAtendente() {
        return atendenteDAO.findAll();
    }
}
