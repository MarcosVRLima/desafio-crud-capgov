package com.br.consultas.controller;

import com.br.consultas.dao.PessoaDAO;
import com.br.consultas.model.Pessoa;

import com.br.consultas.utils.HashUtil;

import java.util.ArrayList;
import java.util.List;
//import java.util.stream.Collectors;

public class PessoaController {

	private final PessoaDAO pessoaDAO;

    public PessoaController() {
        this.pessoaDAO = new PessoaDAO();
    }

    public void salvarPessoa(Pessoa pessoa) {
    	pessoa.setId(HashUtil.gerarId(pessoa.getCpf()));
        pessoaDAO.save(pessoa);
    }

    public void atualizarPessoa(Pessoa pessoa) {
        pessoaDAO.update(pessoa);
    }

    public void excluirPessoa(Pessoa pessoa) {
        pessoaDAO.delete(pessoa);
    }

    public Pessoa buscarPessoaPorId(String id) {
        return pessoaDAO.findById(id);
    }

    public List<Pessoa> listarPessoas() {
        return pessoaDAO.findAll();
    }
    
    public List<Pessoa> listarPessoasPorTipo(String role){
    	List<Pessoa> pessoas = new ArrayList<Pessoa>();
    	List<Pessoa> lista = pessoaDAO.findAll();
    	
    	for(Pessoa pessoa : lista) {
    		//System.out.println(pessoa.getIdsRoles() + " - " + pessoa.getIdsRoles().containsKey(role));
    		if(pessoa.getIdsRoles() != null && pessoa.getIdsRoles().containsKey(role)) {
    			pessoas.add(pessoa);
    		}
    	}
    	
    	return pessoas;
    	
    	//tentar usar esse modo futuramente ou criar uma query no dao
    	
//    	return pessoaDAO.findAll().stream()
//    			.filter(p -> p.getIdsRoles() != null && p.getIdsRoles().containsKey(role))
//    			.collect(Collectors.toList());
    }
}
