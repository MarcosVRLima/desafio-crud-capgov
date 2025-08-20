package com.br.consultas.controller;

import com.br.consultas.dao.MedicoDAO;
import com.br.consultas.model.Medico;
import java.util.List;

public class MedicoController {
    
	private final MedicoDAO medicoDAO;

    public MedicoController() {
        this.medicoDAO = new MedicoDAO();
    }

    public void salvarMedico(Medico medico) {
        medicoDAO.save(medico);
    }

    public void atualizarMedico(Medico medico) {
        medicoDAO.update(medico);
    }

    public void excluirMedico(Medico medico) {
        medicoDAO.delete(medico);
    }

    public Medico buscarMedicoPorId(Long id) {
        return medicoDAO.findById(id);
    }

    public List<Medico> listarMedicos() {
        return medicoDAO.findAll();
    }
}
