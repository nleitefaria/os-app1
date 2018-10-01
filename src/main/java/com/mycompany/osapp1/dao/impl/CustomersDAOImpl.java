/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.osapp1.dao.impl;

import com.mycompany.osapp1.dao.CustomersDAO;
import com.mycompany.osapp1.dao.impl.exceptions.IllegalOrphanException;
import com.mycompany.osapp1.dao.impl.exceptions.NonexistentEntityException;
import com.mycompany.osapp1.dao.impl.exceptions.PreexistingEntityException;
import com.mycompany.osapp1.entity.Customers;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.mycompany.osapp1.entity.Employees;
import com.mycompany.osapp1.entity.Payments;
import java.util.ArrayList;
import java.util.List;
import com.mycompany.osapp1.entity.Orders;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author nleit_000
 */
public class CustomersDAOImpl implements CustomersDAO, Serializable {

    public CustomersDAOImpl(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Customers customers) throws PreexistingEntityException, Exception {
        if (customers.getPaymentsList() == null) {
            customers.setPaymentsList(new ArrayList<Payments>());
        }
        if (customers.getOrdersList() == null) {
            customers.setOrdersList(new ArrayList<Orders>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Employees salesRepEmployeeNumber = customers.getSalesRepEmployeeNumber();
            if (salesRepEmployeeNumber != null) {
                salesRepEmployeeNumber = em.getReference(salesRepEmployeeNumber.getClass(), salesRepEmployeeNumber.getEmployeeNumber());
                customers.setSalesRepEmployeeNumber(salesRepEmployeeNumber);
            }
            List<Payments> attachedPaymentsList = new ArrayList<Payments>();
            for (Payments paymentsListPaymentsToAttach : customers.getPaymentsList()) {
                paymentsListPaymentsToAttach = em.getReference(paymentsListPaymentsToAttach.getClass(), paymentsListPaymentsToAttach.getPaymentsPK());
                attachedPaymentsList.add(paymentsListPaymentsToAttach);
            }
            customers.setPaymentsList(attachedPaymentsList);
            List<Orders> attachedOrdersList = new ArrayList<Orders>();
            for (Orders ordersListOrdersToAttach : customers.getOrdersList()) {
                ordersListOrdersToAttach = em.getReference(ordersListOrdersToAttach.getClass(), ordersListOrdersToAttach.getOrderNumber());
                attachedOrdersList.add(ordersListOrdersToAttach);
            }
            customers.setOrdersList(attachedOrdersList);
            em.persist(customers);
            if (salesRepEmployeeNumber != null) {
                salesRepEmployeeNumber.getCustomersList().add(customers);
                salesRepEmployeeNumber = em.merge(salesRepEmployeeNumber);
            }
            for (Payments paymentsListPayments : customers.getPaymentsList()) {
                Customers oldCustomersOfPaymentsListPayments = paymentsListPayments.getCustomers();
                paymentsListPayments.setCustomers(customers);
                paymentsListPayments = em.merge(paymentsListPayments);
                if (oldCustomersOfPaymentsListPayments != null) {
                    oldCustomersOfPaymentsListPayments.getPaymentsList().remove(paymentsListPayments);
                    oldCustomersOfPaymentsListPayments = em.merge(oldCustomersOfPaymentsListPayments);
                }
            }
            for (Orders ordersListOrders : customers.getOrdersList()) {
                Customers oldCustomerNumberOfOrdersListOrders = ordersListOrders.getCustomerNumber();
                ordersListOrders.setCustomerNumber(customers);
                ordersListOrders = em.merge(ordersListOrders);
                if (oldCustomerNumberOfOrdersListOrders != null) {
                    oldCustomerNumberOfOrdersListOrders.getOrdersList().remove(ordersListOrders);
                    oldCustomerNumberOfOrdersListOrders = em.merge(oldCustomerNumberOfOrdersListOrders);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findCustomers(customers.getCustomerNumber()) != null) {
                throw new PreexistingEntityException("Customers " + customers + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Customers customers) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Customers persistentCustomers = em.find(Customers.class, customers.getCustomerNumber());
            Employees salesRepEmployeeNumberOld = persistentCustomers.getSalesRepEmployeeNumber();
            Employees salesRepEmployeeNumberNew = customers.getSalesRepEmployeeNumber();
            List<Payments> paymentsListOld = persistentCustomers.getPaymentsList();
            List<Payments> paymentsListNew = customers.getPaymentsList();
            List<Orders> ordersListOld = persistentCustomers.getOrdersList();
            List<Orders> ordersListNew = customers.getOrdersList();
            List<String> illegalOrphanMessages = null;
            for (Payments paymentsListOldPayments : paymentsListOld) {
                if (!paymentsListNew.contains(paymentsListOldPayments)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Payments " + paymentsListOldPayments + " since its customers field is not nullable.");
                }
            }
            for (Orders ordersListOldOrders : ordersListOld) {
                if (!ordersListNew.contains(ordersListOldOrders)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Orders " + ordersListOldOrders + " since its customerNumber field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (salesRepEmployeeNumberNew != null) {
                salesRepEmployeeNumberNew = em.getReference(salesRepEmployeeNumberNew.getClass(), salesRepEmployeeNumberNew.getEmployeeNumber());
                customers.setSalesRepEmployeeNumber(salesRepEmployeeNumberNew);
            }
            List<Payments> attachedPaymentsListNew = new ArrayList<Payments>();
            for (Payments paymentsListNewPaymentsToAttach : paymentsListNew) {
                paymentsListNewPaymentsToAttach = em.getReference(paymentsListNewPaymentsToAttach.getClass(), paymentsListNewPaymentsToAttach.getPaymentsPK());
                attachedPaymentsListNew.add(paymentsListNewPaymentsToAttach);
            }
            paymentsListNew = attachedPaymentsListNew;
            customers.setPaymentsList(paymentsListNew);
            List<Orders> attachedOrdersListNew = new ArrayList<Orders>();
            for (Orders ordersListNewOrdersToAttach : ordersListNew) {
                ordersListNewOrdersToAttach = em.getReference(ordersListNewOrdersToAttach.getClass(), ordersListNewOrdersToAttach.getOrderNumber());
                attachedOrdersListNew.add(ordersListNewOrdersToAttach);
            }
            ordersListNew = attachedOrdersListNew;
            customers.setOrdersList(ordersListNew);
            customers = em.merge(customers);
            if (salesRepEmployeeNumberOld != null && !salesRepEmployeeNumberOld.equals(salesRepEmployeeNumberNew)) {
                salesRepEmployeeNumberOld.getCustomersList().remove(customers);
                salesRepEmployeeNumberOld = em.merge(salesRepEmployeeNumberOld);
            }
            if (salesRepEmployeeNumberNew != null && !salesRepEmployeeNumberNew.equals(salesRepEmployeeNumberOld)) {
                salesRepEmployeeNumberNew.getCustomersList().add(customers);
                salesRepEmployeeNumberNew = em.merge(salesRepEmployeeNumberNew);
            }
            for (Payments paymentsListNewPayments : paymentsListNew) {
                if (!paymentsListOld.contains(paymentsListNewPayments)) {
                    Customers oldCustomersOfPaymentsListNewPayments = paymentsListNewPayments.getCustomers();
                    paymentsListNewPayments.setCustomers(customers);
                    paymentsListNewPayments = em.merge(paymentsListNewPayments);
                    if (oldCustomersOfPaymentsListNewPayments != null && !oldCustomersOfPaymentsListNewPayments.equals(customers)) {
                        oldCustomersOfPaymentsListNewPayments.getPaymentsList().remove(paymentsListNewPayments);
                        oldCustomersOfPaymentsListNewPayments = em.merge(oldCustomersOfPaymentsListNewPayments);
                    }
                }
            }
            for (Orders ordersListNewOrders : ordersListNew) {
                if (!ordersListOld.contains(ordersListNewOrders)) {
                    Customers oldCustomerNumberOfOrdersListNewOrders = ordersListNewOrders.getCustomerNumber();
                    ordersListNewOrders.setCustomerNumber(customers);
                    ordersListNewOrders = em.merge(ordersListNewOrders);
                    if (oldCustomerNumberOfOrdersListNewOrders != null && !oldCustomerNumberOfOrdersListNewOrders.equals(customers)) {
                        oldCustomerNumberOfOrdersListNewOrders.getOrdersList().remove(ordersListNewOrders);
                        oldCustomerNumberOfOrdersListNewOrders = em.merge(oldCustomerNumberOfOrdersListNewOrders);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = customers.getCustomerNumber();
                if (findCustomers(id) == null) {
                    throw new NonexistentEntityException("The customers with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Customers customers;
            try {
                customers = em.getReference(Customers.class, id);
                customers.getCustomerNumber();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The customers with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Payments> paymentsListOrphanCheck = customers.getPaymentsList();
            for (Payments paymentsListOrphanCheckPayments : paymentsListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Customers (" + customers + ") cannot be destroyed since the Payments " + paymentsListOrphanCheckPayments + " in its paymentsList field has a non-nullable customers field.");
            }
            List<Orders> ordersListOrphanCheck = customers.getOrdersList();
            for (Orders ordersListOrphanCheckOrders : ordersListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Customers (" + customers + ") cannot be destroyed since the Orders " + ordersListOrphanCheckOrders + " in its ordersList field has a non-nullable customerNumber field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Employees salesRepEmployeeNumber = customers.getSalesRepEmployeeNumber();
            if (salesRepEmployeeNumber != null) {
                salesRepEmployeeNumber.getCustomersList().remove(customers);
                salesRepEmployeeNumber = em.merge(salesRepEmployeeNumber);
            }
            em.remove(customers);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Customers> findCustomersEntities() {
        return findCustomersEntities(true, -1, -1);
    }

    public List<Customers> findCustomersEntities(int maxResults, int firstResult) {
        return findCustomersEntities(false, maxResults, firstResult);
    }

    private List<Customers> findCustomersEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Customers.class));
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

    public Customers findCustomers(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Customers.class, id);
        } finally {
            em.close();
        }
    }

    public int getCustomersCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Customers> rt = cq.from(Customers.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
