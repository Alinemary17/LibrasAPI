package com.aline.Controller;

import com.aline.Model.Conta;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

public class ContaDAO {
    public static void createConta(Conta conta) {
        EntityManager em = Manager.getEntityManager();
        em.getTransaction().begin();
        em.persist(conta);
        em.getTransaction().commit();
        em.close();

    }

    public static void updateConta(Conta conta) {
        EntityManager em = Manager.getEntityManager();
        em.getTransaction().begin();
        em.merge(conta);
        em.getTransaction().commit();
        em.close();

    }

    public static void deleteConta(Conta conta) {
        EntityManager em = Manager.getEntityManager();
        em.getTransaction().begin();
        em.remove(conta);
        em.getTransaction().commit();
        em.close();

    }

    public static Conta findConta(String login, String senha) {
        EntityManager em = Manager.getEntityManager();
        Query q = em.createQuery("SELECT c FROM Conta c WHERE login LIKE :login AND senha LIKE :senha", Conta.class);


        q.setParameter("login", login);
        q.setParameter("senha", senha);
        try {
            return (Conta) q.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }


    }

}
