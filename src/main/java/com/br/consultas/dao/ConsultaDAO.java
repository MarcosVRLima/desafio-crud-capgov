package com.br.consultas.dao;

import com.br.consultas.model.Consulta;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.time.LocalDateTime;
import java.util.List;

public class ConsultaDAO extends GenericDAO<Consulta> {
	
	private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("consultasPU");

	public ConsultaDAO() {
		super(Consulta.class);
	}
	
	// Método específico: buscar consultas por data
    public List<Consulta> findByDate(LocalDateTime data) {
        EntityManager em = emf.createEntityManager();
        List<Consulta> list = em.createQuery(
                "FROM Consulta c WHERE DATE(c.dataHora) = :data", Consulta.class)
                .setParameter("data", data.toLocalDate())
                .getResultList();
        em.close();
        return list;
    }
    
    // Método específico: buscar consultas de um paciente
    public List<Consulta> findByPacienteId(Long pacienteId) {
        EntityManager em = emf.createEntityManager();
        List<Consulta> list = em.createQuery(
                "FROM Consulta c WHERE c.paciente.id = :pid", Consulta.class)
                .setParameter("pid", pacienteId)
                .getResultList();
        em.close();
        return list;
    }
}
