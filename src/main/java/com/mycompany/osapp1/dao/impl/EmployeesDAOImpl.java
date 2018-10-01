/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.osapp1.dao.impl;

import com.mycompany.osapp1.dao.EmployeesDAO;
import com.mycompany.osapp1.dao.impl.exceptions.NonexistentEntityException;
import com.mycompany.osapp1.dao.impl.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.mycompany.osapp1.entity.Employees;
import com.mycompany.osapp1.entity.Offices;
import com.mycompany.osapp1.entity.Customers;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author nleit_000
 */
public class EmployeesDAOImpl implements EmployeesDAO, Serializable {

    public EmployeesDAOImpl(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Employees employees) throws PreexistingEntityException, Exception {
        if (employees.getCustomersList() == null) {
            employees.setCustomersList(new ArrayList<Customers>());
        }
        if (employees.getEmployeesList() == null) {
            employees.setEmployeesList(new ArrayList<Employees>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Employees reportsTo = employees.getReportsTo();
            if (reportsTo != null) {
                reportsTo = em.getReference(reportsTo.getClass(), reportsTo.getEmployeeNumber());
                employees.setReportsTo(reportsTo);
            }
            Offices officeCode = employees.getOfficeCode();
            if (officeCode != null) {
                officeCode = em.getReference(officeCode.getClass(), officeCode.getOfficeCode());
                employees.setOfficeCode(officeCode);
            }
            List<Customers> attachedCustomersList = new ArrayList<Customers>();
            for (Customers customersListCustomersToAttach : employees.getCustomersList()) {
                customersListCustomersToAttach = em.getReference(customersListCustomersToAttach.getClass(), customersListCustomersToAttach.getCustomerNumber());
                attachedCustomersList.add(customersListCustomersToAttach);
            }
            employees.setCustomersList(attachedCustomersList);
            List<Employees> attachedEmployeesList = new ArrayList<Employees>();
            for (Employees employeesListEmployeesToAttach : employees.getEmployeesList()) {
                employeesListEmployeesToAttach = em.getReference(employeesListEmployeesToAttach.getClass(), employeesListEmployeesToAttach.getEmployeeNumber());
                attachedEmployeesList.add(employeesListEmployeesToAttach);
            }
            employees.setEmployeesList(attachedEmployeesList);
            em.persist(employees);
            if (reportsTo != null) {
                reportsTo.getEmployeesList().add(employees);
                reportsTo = em.merge(reportsTo);
            }
            if (officeCode != null) {
                officeCode.getEmployeesList().add(employees);
                officeCode = em.merge(officeCode);
            }
            for (Customers customersListCustomers : employees.getCustomersList()) {
                Employees oldSalesRepEmployeeNumberOfCustomersListCustomers = customersListCustomers.getSalesRepEmployeeNumber();
                customersListCustomers.setSalesRepEmployeeNumber(employees);
                customersListCustomers = em.merge(customersListCustomers);
                if (oldSalesRepEmployeeNumberOfCustomersListCustomers != null) {
                    oldSalesRepEmployeeNumberOfCustomersListCustomers.getCustomersList().remove(customersListCustomers);
                    oldSalesRepEmployeeNumberOfCustomersListCustomers = em.merge(oldSalesRepEmployeeNumberOfCustomersListCustomers);
                }
            }
            for (Employees employeesListEmployees : employees.getEmployeesList()) {
                Employees oldReportsToOfEmployeesListEmployees = employeesListEmployees.getReportsTo();
                employeesListEmployees.setReportsTo(employees);
                employeesListEmployees = em.merge(employeesListEmployees);
                if (oldReportsToOfEmployeesListEmployees != null) {
                    oldReportsToOfEmployeesListEmployees.getEmployeesList().remove(employeesListEmployees);
                    oldReportsToOfEmployeesListEmployees = em.merge(oldReportsToOfEmployeesListEmployees);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findEmployees(employees.getEmployeeNumber()) != null) {
                throw new PreexistingEntityException("Employees " + employees + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Employees employees) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Employees persistentEmployees = em.find(Employees.class, employees.getEmployeeNumber());
            Employees reportsToOld = persistentEmployees.getReportsTo();
            Employees reportsToNew = employees.getReportsTo();
            Offices officeCodeOld = persistentEmployees.getOfficeCode();
            Offices officeCodeNew = employees.getOfficeCode();
            List<Customers> customersListOld = persistentEmployees.getCustomersList();
            List<Customers> customersListNew = employees.getCustomersList();
            List<Employees> employeesListOld = persistentEmployees.getEmployeesList();
            List<Employees> employeesListNew = employees.getEmployeesList();
            if (reportsToNew != null) {
                reportsToNew = em.getReference(reportsToNew.getClass(), reportsToNew.getEmployeeNumber());
                employees.setReportsTo(reportsToNew);
            }
            if (officeCodeNew != null) {
                officeCodeNew = em.getReference(officeCodeNew.getClass(), officeCodeNew.getOfficeCode());
                employees.setOfficeCode(officeCodeNew);
            }
            List<Customers> attachedCustomersListNew = new ArrayList<Customers>();
            for (Customers customersListNewCustomersToAttach : customersListNew) {
                customersListNewCustomersToAttach = em.getReference(customersListNewCustomersToAttach.getClass(), customersListNewCustomersToAttach.getCustomerNumber());
                attachedCustomersListNew.add(customersListNewCustomersToAttach);
            }
            customersListNew = attachedCustomersListNew;
            employees.setCustomersList(customersListNew);
            List<Employees> attachedEmployeesListNew = new ArrayList<Employees>();
            for (Employees employeesListNewEmployeesToAttach : employeesListNew) {
                employeesListNewEmployeesToAttach = em.getReference(employeesListNewEmployeesToAttach.getClass(), employeesListNewEmployeesToAttach.getEmployeeNumber());
                attachedEmployeesListNew.add(employeesListNewEmployeesToAttach);
            }
            employeesListNew = attachedEmployeesListNew;
            employees.setEmployeesList(employeesListNew);
            employees = em.merge(employees);
            if (reportsToOld != null && !reportsToOld.equals(reportsToNew)) {
                reportsToOld.getEmployeesList().remove(employees);
                reportsToOld = em.merge(reportsToOld);
            }
            if (reportsToNew != null && !reportsToNew.equals(reportsToOld)) {
                reportsToNew.getEmployeesList().add(employees);
                reportsToNew = em.merge(reportsToNew);
            }
            if (officeCodeOld != null && !officeCodeOld.equals(officeCodeNew)) {
                officeCodeOld.getEmployeesList().remove(employees);
                officeCodeOld = em.merge(officeCodeOld);
            }
            if (officeCodeNew != null && !officeCodeNew.equals(officeCodeOld)) {
                officeCodeNew.getEmployeesList().add(employees);
                officeCodeNew = em.merge(officeCodeNew);
            }
            for (Customers customersListOldCustomers : customersListOld) {
                if (!customersListNew.contains(customersListOldCustomers)) {
                    customersListOldCustomers.setSalesRepEmployeeNumber(null);
                    customersListOldCustomers = em.merge(customersListOldCustomers);
                }
            }
            for (Customers customersListNewCustomers : customersListNew) {
                if (!customersListOld.contains(customersListNewCustomers)) {
                    Employees oldSalesRepEmployeeNumberOfCustomersListNewCustomers = customersListNewCustomers.getSalesRepEmployeeNumber();
                    customersListNewCustomers.setSalesRepEmployeeNumber(employees);
                    customersListNewCustomers = em.merge(customersListNewCustomers);
                    if (oldSalesRepEmployeeNumberOfCustomersListNewCustomers != null && !oldSalesRepEmployeeNumberOfCustomersListNewCustomers.equals(employees)) {
                        oldSalesRepEmployeeNumberOfCustomersListNewCustomers.getCustomersList().remove(customersListNewCustomers);
                        oldSalesRepEmployeeNumberOfCustomersListNewCustomers = em.merge(oldSalesRepEmployeeNumberOfCustomersListNewCustomers);
                    }
                }
            }
            for (Employees employeesListOldEmployees : employeesListOld) {
                if (!employeesListNew.contains(employeesListOldEmployees)) {
                    employeesListOldEmployees.setReportsTo(null);
                    employeesListOldEmployees = em.merge(employeesListOldEmployees);
                }
            }
            for (Employees employeesListNewEmployees : employeesListNew) {
                if (!employeesListOld.contains(employeesListNewEmployees)) {
                    Employees oldReportsToOfEmployeesListNewEmployees = employeesListNewEmployees.getReportsTo();
                    employeesListNewEmployees.setReportsTo(employees);
                    employeesListNewEmployees = em.merge(employeesListNewEmployees);
                    if (oldReportsToOfEmployeesListNewEmployees != null && !oldReportsToOfEmployeesListNewEmployees.equals(employees)) {
                        oldReportsToOfEmployeesListNewEmployees.getEmployeesList().remove(employeesListNewEmployees);
                        oldReportsToOfEmployeesListNewEmployees = em.merge(oldReportsToOfEmployeesListNewEmployees);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = employees.getEmployeeNumber();
                if (findEmployees(id) == null) {
                    throw new NonexistentEntityException("The employees with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Employees employees;
            try {
                employees = em.getReference(Employees.class, id);
                employees.getEmployeeNumber();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The employees with id " + id + " no longer exists.", enfe);
            }
            Employees reportsTo = employees.getReportsTo();
            if (reportsTo != null) {
                reportsTo.getEmployeesList().remove(employees);
                reportsTo = em.merge(reportsTo);
            }
            Offices officeCode = employees.getOfficeCode();
            if (officeCode != null) {
                officeCode.getEmployeesList().remove(employees);
                officeCode = em.merge(officeCode);
            }
            List<Customers> customersList = employees.getCustomersList();
            for (Customers customersListCustomers : customersList) {
                customersListCustomers.setSalesRepEmployeeNumber(null);
                customersListCustomers = em.merge(customersListCustomers);
            }
            List<Employees> employeesList = employees.getEmployeesList();
            for (Employees employeesListEmployees : employeesList) {
                employeesListEmployees.setReportsTo(null);
                employeesListEmployees = em.merge(employeesListEmployees);
            }
            em.remove(employees);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Employees> findEmployeesEntities() {
        return findEmployeesEntities(true, -1, -1);
    }

    public List<Employees> findEmployeesEntities(int maxResults, int firstResult) {
        return findEmployeesEntities(false, maxResults, firstResult);
    }

    private List<Employees> findEmployeesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Employees.class));
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

    public Employees findEmployees(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Employees.class, id);
        } finally {
            em.close();
        }
    }

    public int getEmployeesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Employees> rt = cq.from(Employees.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
