package com.br.consultas.controller;

import com.br.consultas.dao.PessoaDAO;
import com.br.consultas.model.Pessoa;
import java.util.List;

public class PessoaController {

	private final PessoaDAO pessoaDAO;

    public PessoaController() {
        this.pessoaDAO = new PessoaDAO();
    }

    public void salvarPessoa(Pessoa pessoa) {
        pessoaDAO.save(pessoa);
    }

    public void atualizarPessoa(Pessoa pessoa) {
        pessoaDAO.update(pessoa);
    }

    public void excluirPessoa(Pessoa pessoa) {
        pessoaDAO.delete(pessoa);
    }

    public Pessoa buscarPessoaPorId(Long id) {
        return pessoaDAO.findById(id);
    }

    public List<Pessoa> listarPessoas() {
        return pessoaDAO.findAll();
    }
}
