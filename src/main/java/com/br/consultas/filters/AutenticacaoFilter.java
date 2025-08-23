package com.br.consultas.filters;

import java.io.IOException;

import com.br.consultas.model.Usuario;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebFilter("/views/*") // filtra todas as páginas dentro de /views
public class AutenticacaoFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        // Cast para HTTP
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        HttpSession session = req.getSession(false); // não cria sessão se não existir
        Usuario usuario = null;

        if (session != null) {
            usuario = (Usuario) session.getAttribute("usuarioLogado");
        }

        String pagina = req.getRequestURI();

        // Se não estiver logado e tentar acessar qualquer página diferente do login
        if (usuario == null && !pagina.endsWith("index.xhtml") && !pagina.endsWith("cadastrar-pessoa.xhtml")) {
            res.sendRedirect(req.getContextPath() + "/views/index.xhtml");
         // Usuário logado tenta acessar a página de login
        } else if (usuario != null && pagina.endsWith("index.xhtml")) {
            res.sendRedirect(req.getContextPath() + "/views/home.xhtml");
        } else {
            chain.doFilter(request, response); // usuário logado ou acessando login, segue normalmente
        }
    }

    @Override
    public void destroy() {
        
    }
}
