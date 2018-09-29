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
import com.mycompany.osapp1.entity.Customers;
import com.mycompany.osapp1.entity.Orderdetails;
import com.mycompany.osapp1.entity.Orders;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author nleit_000
 */
public class OrdersDAOImpl implements Serializable {

    public OrdersDAOImpl(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Orders orders) throws PreexistingEntityException, Exception {
        if (orders.getOrderdetailsList() == null) {
            orders.setOrderdetailsList(new ArrayList<Orderdetails>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Customers customerNumber = orders.getCustomerNumber();
            if (customerNumber != null) {
                customerNumber = em.getReference(customerNumber.getClass(), customerNumber.getCustomerNumber());
                orders.setCustomerNumber(customerNumber);
            }
            List<Orderdetails> attachedOrderdetailsList = new ArrayList<Orderdetails>();
            for (Orderdetails orderdetailsListOrderdetailsToAttach : orders.getOrderdetailsList()) {
                orderdetailsListOrderdetailsToAttach = em.getReference(orderdetailsListOrderdetailsToAttach.getClass(), orderdetailsListOrderdetailsToAttach.getOrderdetailsPK());
                attachedOrderdetailsList.add(orderdetailsListOrderdetailsToAttach);
            }
            orders.setOrderdetailsList(attachedOrderdetailsList);
            em.persist(orders);
            if (customerNumber != null) {
                customerNumber.getOrdersList().add(orders);
                customerNumber = em.merge(customerNumber);
            }
            for (Orderdetails orderdetailsListOrderdetails : orders.getOrderdetailsList()) {
                Orders oldOrdersOfOrderdetailsListOrderdetails = orderdetailsListOrderdetails.getOrders();
                orderdetailsListOrderdetails.setOrders(orders);
                orderdetailsListOrderdetails = em.merge(orderdetailsListOrderdetails);
                if (oldOrdersOfOrderdetailsListOrderdetails != null) {
                    oldOrdersOfOrderdetailsListOrderdetails.getOrderdetailsList().remove(orderdetailsListOrderdetails);
                    oldOrdersOfOrderdetailsListOrderdetails = em.merge(oldOrdersOfOrderdetailsListOrderdetails);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findOrders(orders.getOrderNumber()) != null) {
                throw new PreexistingEntityException("Orders " + orders + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Orders orders) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Orders persistentOrders = em.find(Orders.class, orders.getOrderNumber());
            Customers customerNumberOld = persistentOrders.getCustomerNumber();
            Customers customerNumberNew = orders.getCustomerNumber();
            List<Orderdetails> orderdetailsListOld = persistentOrders.getOrderdetailsList();
            List<Orderdetails> orderdetailsListNew = orders.getOrderdetailsList();
            List<String> illegalOrphanMessages = null;
            for (Orderdetails orderdetailsListOldOrderdetails : orderdetailsListOld) {
                if (!orderdetailsListNew.contains(orderdetailsListOldOrderdetails)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Orderdetails " + orderdetailsListOldOrderdetails + " since its orders field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (customerNumberNew != null) {
                customerNumberNew = em.getReference(customerNumberNew.getClass(), customerNumberNew.getCustomerNumber());
                orders.setCustomerNumber(customerNumberNew);
            }
            List<Orderdetails> attachedOrderdetailsListNew = new ArrayList<Orderdetails>();
            for (Orderdetails orderdetailsListNewOrderdetailsToAttach : orderdetailsListNew) {
                orderdetailsListNewOrderdetailsToAttach = em.getReference(orderdetailsListNewOrderdetailsToAttach.getClass(), orderdetailsListNewOrderdetailsToAttach.getOrderdetailsPK());
                attachedOrderdetailsListNew.add(orderdetailsListNewOrderdetailsToAttach);
            }
            orderdetailsListNew = attachedOrderdetailsListNew;
            orders.setOrderdetailsList(orderdetailsListNew);
            orders = em.merge(orders);
            if (customerNumberOld != null && !customerNumberOld.equals(customerNumberNew)) {
                customerNumberOld.getOrdersList().remove(orders);
                customerNumberOld = em.merge(customerNumberOld);
            }
            if (customerNumberNew != null && !customerNumberNew.equals(customerNumberOld)) {
                customerNumberNew.getOrdersList().add(orders);
                customerNumberNew = em.merge(customerNumberNew);
            }
            for (Orderdetails orderdetailsListNewOrderdetails : orderdetailsListNew) {
                if (!orderdetailsListOld.contains(orderdetailsListNewOrderdetails)) {
                    Orders oldOrdersOfOrderdetailsListNewOrderdetails = orderdetailsListNewOrderdetails.getOrders();
                    orderdetailsListNewOrderdetails.setOrders(orders);
                    orderdetailsListNewOrderdetails = em.merge(orderdetailsListNewOrderdetails);
                    if (oldOrdersOfOrderdetailsListNewOrderdetails != null && !oldOrdersOfOrderdetailsListNewOrderdetails.equals(orders)) {
                        oldOrdersOfOrderdetailsListNewOrderdetails.getOrderdetailsList().remove(orderdetailsListNewOrderdetails);
                        oldOrdersOfOrderdetailsListNewOrderdetails = em.merge(oldOrdersOfOrderdetailsListNewOrderdetails);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = orders.getOrderNumber();
                if (findOrders(id) == null) {
                    throw new NonexistentEntityException("The orders with id " + id + " no longer exists.");
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
            Orders orders;
            try {
                orders = em.getReference(Orders.class, id);
                orders.getOrderNumber();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The orders with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Orderdetails> orderdetailsListOrphanCheck = orders.getOrderdetailsList();
            for (Orderdetails orderdetailsListOrphanCheckOrderdetails : orderdetailsListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Orders (" + orders + ") cannot be destroyed since the Orderdetails " + orderdetailsListOrphanCheckOrderdetails + " in its orderdetailsList field has a non-nullable orders field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Customers customerNumber = orders.getCustomerNumber();
            if (customerNumber != null) {
                customerNumber.getOrdersList().remove(orders);
                customerNumber = em.merge(customerNumber);
            }
            em.remove(orders);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Orders> findOrdersEntities() {
        return findOrdersEntities(true, -1, -1);
    }

    public List<Orders> findOrdersEntities(int maxResults, int firstResult) {
        return findOrdersEntities(false, maxResults, firstResult);
    }

    private List<Orders> findOrdersEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Orders.class));
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

    public Orders findOrders(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Orders.class, id);
        } finally {
            em.close();
        }
    }

    public int getOrdersCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Orders> rt = cq.from(Orders.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
