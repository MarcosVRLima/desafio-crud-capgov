package com.br.consultas.testes;

import com.br.consultas.controller.PessoaController;
import com.br.consultas.model.Pessoa;

public class TestMain {

	public static void main(String[] args) {
		
		Pessoa pessoa = new Pessoa();
		pessoa.setNome("Marcos");
		pessoa.setCpf("080.772.263-45");
		
		PessoaController pc = new PessoaController();
		pc.salvarPessoa(pessoa);

	}

}
