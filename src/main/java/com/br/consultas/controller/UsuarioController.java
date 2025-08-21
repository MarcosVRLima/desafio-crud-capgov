package com.br.consultas.controller;

import com.br.consultas.dao.UsuarioDAO;
import com.br.consultas.model.Usuario;
import java.util.List;

public class UsuarioController {
	
	private final UsuarioDAO usuarioDAO;

    public UsuarioController() {
        this.usuarioDAO = new UsuarioDAO();
    }

    public void salvarUsuario(Usuario usuario) {
    	usuarioDAO.save(usuario);
    }

    public void atualizarUsuario(Usuario usuario) {
    	usuarioDAO.update(usuario);
    }

    public void excluirUsuario(Usuario usuario) {
    	usuarioDAO.delete(usuario);
    }

    public Usuario buscarUsuarioPorId(Long id) {
        return usuarioDAO.findById(id);
    }

    public List<Usuario> listarUsuario() {
        return usuarioDAO.findAll();
    }
}
