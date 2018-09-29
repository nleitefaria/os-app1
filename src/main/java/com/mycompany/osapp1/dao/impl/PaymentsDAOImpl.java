/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.osapp1.dao.impl;

import com.mycompany.osapp1.dao.impl.exceptions.NonexistentEntityException;
import com.mycompany.osapp1.dao.impl.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.mycompany.osapp1.entity.Customers;
import com.mycompany.osapp1.entity.Payments;
import com.mycompany.osapp1.entity.PaymentsPK;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author nleit_000
 */
public class PaymentsDAOImpl implements Serializable {

    public PaymentsDAOImpl(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Payments payments) throws PreexistingEntityException, Exception {
        if (payments.getPaymentsPK() == null) {
            payments.setPaymentsPK(new PaymentsPK());
        }
        payments.getPaymentsPK().setCustomerNumber(payments.getCustomers().getCustomerNumber());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Customers customers = payments.getCustomers();
            if (customers != null) {
                customers = em.getReference(customers.getClass(), customers.getCustomerNumber());
                payments.setCustomers(customers);
            }
            em.persist(payments);
            if (customers != null) {
                customers.getPaymentsList().add(payments);
                customers = em.merge(customers);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findPayments(payments.getPaymentsPK()) != null) {
                throw new PreexistingEntityException("Payments " + payments + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Payments payments) throws NonexistentEntityException, Exception {
        payments.getPaymentsPK().setCustomerNumber(payments.getCustomers().getCustomerNumber());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Payments persistentPayments = em.find(Payments.class, payments.getPaymentsPK());
            Customers customersOld = persistentPayments.getCustomers();
            Customers customersNew = payments.getCustomers();
            if (customersNew != null) {
                customersNew = em.getReference(customersNew.getClass(), customersNew.getCustomerNumber());
                payments.setCustomers(customersNew);
            }
            payments = em.merge(payments);
            if (customersOld != null && !customersOld.equals(customersNew)) {
                customersOld.getPaymentsList().remove(payments);
                customersOld = em.merge(customersOld);
            }
            if (customersNew != null && !customersNew.equals(customersOld)) {
                customersNew.getPaymentsList().add(payments);
                customersNew = em.merge(customersNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                PaymentsPK id = payments.getPaymentsPK();
                if (findPayments(id) == null) {
                    throw new NonexistentEntityException("The payments with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(PaymentsPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Payments payments;
            try {
                payments = em.getReference(Payments.class, id);
                payments.getPaymentsPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The payments with id " + id + " no longer exists.", enfe);
            }
            Customers customers = payments.getCustomers();
            if (customers != null) {
                customers.getPaymentsList().remove(payments);
                customers = em.merge(customers);
            }
            em.remove(payments);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Payments> findPaymentsEntities() {
        return findPaymentsEntities(true, -1, -1);
    }

    public List<Payments> findPaymentsEntities(int maxResults, int firstResult) {
        return findPaymentsEntities(false, maxResults, firstResult);
    }

    private List<Payments> findPaymentsEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Payments.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Payments findPayments(PaymentsPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Payments.class, id);
        } finally {
            em.close();
        }
    }

    public int getPaymentsCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Payments> rt = cq.from(Payments.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
