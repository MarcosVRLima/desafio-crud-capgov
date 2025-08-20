package com.br.consultas.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.List;

public class GenericDAO<T> {
	
	private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("consultasPU");
    private final Class<T> entityClass;
    
    protected GenericDAO(Class<T> entityClass) {
        this.entityClass = entityClass;
    }
    
    public void save(T entity) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(entity);
        em.getTransaction().commit();
        em.close();
    }
    
    public void update(T entity) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.merge(entity);
        em.getTransaction().commit();
        em.close();
    }

    public void delete(T entity) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.remove(em.contains(entity) ? entity : em.merge(entity));
        em.getTransaction().commit();
        em.close();
    }
    
    public T findById(Long id) {
        EntityManager em = emf.createEntityManager();
        T entity = em.find(entityClass, id);
        em.close();
        return entity;
    }
    
    public List<T> findAll() {
        EntityManager em = emf.createEntityManager();
        List<T> list = em.createQuery("FROM " + entityClass.getSimpleName(), entityClass).getResultList();
        em.close();
        return list;
    }
}
