/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.osapp1.dao.impl;

import com.mycompany.osapp1.dao.impl.exceptions.IllegalOrphanException;
import com.mycompany.osapp1.dao.impl.exceptions.NonexistentEntityException;
import com.mycompany.osapp1.dao.impl.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.mycompany.osapp1.entity.Employees;
import com.mycompany.osapp1.entity.Offices;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author nleit_000
 */
public class OfficesDAOImpl implements Serializable {

    public OfficesDAOImpl(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Offices offices) throws PreexistingEntityException, Exception {
        if (offices.getEmployeesList() == null) {
            offices.setEmployeesList(new ArrayList<Employees>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Employees> attachedEmployeesList = new ArrayList<Employees>();
            for (Employees employeesListEmployeesToAttach : offices.getEmployeesList()) {
                employeesListEmployeesToAttach = em.getReference(employeesListEmployeesToAttach.getClass(), employeesListEmployeesToAttach.getEmployeeNumber());
                attachedEmployeesList.add(employeesListEmployeesToAttach);
            }
            offices.setEmployeesList(attachedEmployeesList);
            em.persist(offices);
            for (Employees employeesListEmployees : offices.getEmployeesList()) {
                Offices oldOfficeCodeOfEmployeesListEmployees = employeesListEmployees.getOfficeCode();
                employeesListEmployees.setOfficeCode(offices);
                employeesListEmployees = em.merge(employeesListEmployees);
                if (oldOfficeCodeOfEmployeesListEmployees != null) {
                    oldOfficeCodeOfEmployeesListEmployees.getEmployeesList().remove(employeesListEmployees);
                    oldOfficeCodeOfEmployeesListEmployees = em.merge(oldOfficeCodeOfEmployeesListEmployees);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findOffices(offices.getOfficeCode()) != null) {
                throw new PreexistingEntityException("Offices " + offices + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Offices offices) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Offices persistentOffices = em.find(Offices.class, offices.getOfficeCode());
            List<Employees> employeesListOld = persistentOffices.getEmployeesList();
            List<Employees> employeesListNew = offices.getEmployeesList();
            List<String> illegalOrphanMessages = null;
            for (Employees employeesListOldEmployees : employeesListOld) {
                if (!employeesListNew.contains(employeesListOldEmployees)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Employees " + employeesListOldEmployees + " since its officeCode field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Employees> attachedEmployeesListNew = new ArrayList<Employees>();
            for (Employees employeesListNewEmployeesToAttach : employeesListNew) {
                employeesListNewEmployeesToAttach = em.getReference(employeesListNewEmployeesToAttach.getClass(), employeesListNewEmployeesToAttach.getEmployeeNumber());
                attachedEmployeesListNew.add(employeesListNewEmployeesToAttach);
            }
            employeesListNew = attachedEmployeesListNew;
            offices.setEmployeesList(employeesListNew);
            offices = em.merge(offices);
            for (Employees employeesListNewEmployees : employeesListNew) {
                if (!employeesListOld.contains(employeesListNewEmployees)) {
                    Offices oldOfficeCodeOfEmployeesListNewEmployees = employeesListNewEmployees.getOfficeCode();
                    employeesListNewEmployees.setOfficeCode(offices);
                    employeesListNewEmployees = em.merge(employeesListNewEmployees);
                    if (oldOfficeCodeOfEmployeesListNewEmployees != null && !oldOfficeCodeOfEmployeesListNewEmployees.equals(offices)) {
                        oldOfficeCodeOfEmployeesListNewEmployees.getEmployeesList().remove(employeesListNewEmployees);
                        oldOfficeCodeOfEmployeesListNewEmployees = em.merge(oldOfficeCodeOfEmployeesListNewEmployees);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = offices.getOfficeCode();
                if (findOffices(id) == null) {
                    throw new NonexistentEntityException("The offices with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Offices offices;
            try {
                offices = em.getReference(Offices.class, id);
                offices.getOfficeCode();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The offices with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Employees> employeesListOrphanCheck = offices.getEmployeesList();
            for (Employees employeesListOrphanCheckEmployees : employeesListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Offices (" + offices + ") cannot be destroyed since the Employees " + employeesListOrphanCheckEmployees + " in its employeesList field has a non-nullable officeCode field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(offices);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Offices> findOfficesEntities() {
        return findOfficesEntities(true, -1, -1);
    }

    public List<Offices> findOfficesEntities(int maxResults, int firstResult) {
        return findOfficesEntities(false, maxResults, firstResult);
    }

    private List<Offices> findOfficesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Offices.class));
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

    public Offices findOffices(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Offices.class, id);
        } finally {
            em.close();
        }
    }

    public int getOfficesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Offices> rt = cq.from(Offices.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
