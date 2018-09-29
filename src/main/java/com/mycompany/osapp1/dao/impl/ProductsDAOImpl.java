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
import com.mycompany.osapp1.entity.Productlines;
import com.mycompany.osapp1.entity.Orderdetails;
import com.mycompany.osapp1.entity.Products;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author nleit_000
 */
public class ProductsDAOImpl implements Serializable {

    public ProductsDAOImpl(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Products products) throws PreexistingEntityException, Exception {
        if (products.getOrderdetailsList() == null) {
            products.setOrderdetailsList(new ArrayList<Orderdetails>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Productlines productLine = products.getProductLine();
            if (productLine != null) {
                productLine = em.getReference(productLine.getClass(), productLine.getProductLine());
                products.setProductLine(productLine);
            }
            List<Orderdetails> attachedOrderdetailsList = new ArrayList<Orderdetails>();
            for (Orderdetails orderdetailsListOrderdetailsToAttach : products.getOrderdetailsList()) {
                orderdetailsListOrderdetailsToAttach = em.getReference(orderdetailsListOrderdetailsToAttach.getClass(), orderdetailsListOrderdetailsToAttach.getOrderdetailsPK());
                attachedOrderdetailsList.add(orderdetailsListOrderdetailsToAttach);
            }
            products.setOrderdetailsList(attachedOrderdetailsList);
            em.persist(products);
            if (productLine != null) {
                productLine.getProductsList().add(products);
                productLine = em.merge(productLine);
            }
            for (Orderdetails orderdetailsListOrderdetails : products.getOrderdetailsList()) {
                Products oldProductsOfOrderdetailsListOrderdetails = orderdetailsListOrderdetails.getProducts();
                orderdetailsListOrderdetails.setProducts(products);
                orderdetailsListOrderdetails = em.merge(orderdetailsListOrderdetails);
                if (oldProductsOfOrderdetailsListOrderdetails != null) {
                    oldProductsOfOrderdetailsListOrderdetails.getOrderdetailsList().remove(orderdetailsListOrderdetails);
                    oldProductsOfOrderdetailsListOrderdetails = em.merge(oldProductsOfOrderdetailsListOrderdetails);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findProducts(products.getProductCode()) != null) {
                throw new PreexistingEntityException("Products " + products + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Products products) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Products persistentProducts = em.find(Products.class, products.getProductCode());
            Productlines productLineOld = persistentProducts.getProductLine();
            Productlines productLineNew = products.getProductLine();
            List<Orderdetails> orderdetailsListOld = persistentProducts.getOrderdetailsList();
            List<Orderdetails> orderdetailsListNew = products.getOrderdetailsList();
            List<String> illegalOrphanMessages = null;
            for (Orderdetails orderdetailsListOldOrderdetails : orderdetailsListOld) {
                if (!orderdetailsListNew.contains(orderdetailsListOldOrderdetails)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Orderdetails " + orderdetailsListOldOrderdetails + " since its products field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (productLineNew != null) {
                productLineNew = em.getReference(productLineNew.getClass(), productLineNew.getProductLine());
                products.setProductLine(productLineNew);
            }
            List<Orderdetails> attachedOrderdetailsListNew = new ArrayList<Orderdetails>();
            for (Orderdetails orderdetailsListNewOrderdetailsToAttach : orderdetailsListNew) {
                orderdetailsListNewOrderdetailsToAttach = em.getReference(orderdetailsListNewOrderdetailsToAttach.getClass(), orderdetailsListNewOrderdetailsToAttach.getOrderdetailsPK());
                attachedOrderdetailsListNew.add(orderdetailsListNewOrderdetailsToAttach);
            }
            orderdetailsListNew = attachedOrderdetailsListNew;
            products.setOrderdetailsList(orderdetailsListNew);
            products = em.merge(products);
            if (productLineOld != null && !productLineOld.equals(productLineNew)) {
                productLineOld.getProductsList().remove(products);
                productLineOld = em.merge(productLineOld);
            }
            if (productLineNew != null && !productLineNew.equals(productLineOld)) {
                productLineNew.getProductsList().add(products);
                productLineNew = em.merge(productLineNew);
            }
            for (Orderdetails orderdetailsListNewOrderdetails : orderdetailsListNew) {
                if (!orderdetailsListOld.contains(orderdetailsListNewOrderdetails)) {
                    Products oldProductsOfOrderdetailsListNewOrderdetails = orderdetailsListNewOrderdetails.getProducts();
                    orderdetailsListNewOrderdetails.setProducts(products);
                    orderdetailsListNewOrderdetails = em.merge(orderdetailsListNewOrderdetails);
                    if (oldProductsOfOrderdetailsListNewOrderdetails != null && !oldProductsOfOrderdetailsListNewOrderdetails.equals(products)) {
                        oldProductsOfOrderdetailsListNewOrderdetails.getOrderdetailsList().remove(orderdetailsListNewOrderdetails);
                        oldProductsOfOrderdetailsListNewOrderdetails = em.merge(oldProductsOfOrderdetailsListNewOrderdetails);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = products.getProductCode();
                if (findProducts(id) == null) {
                    throw new NonexistentEntityException("The products with id " + id + " no longer exists.");
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
            Products products;
            try {
                products = em.getReference(Products.class, id);
                products.getProductCode();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The products with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Orderdetails> orderdetailsListOrphanCheck = products.getOrderdetailsList();
            for (Orderdetails orderdetailsListOrphanCheckOrderdetails : orderdetailsListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Products (" + products + ") cannot be destroyed since the Orderdetails " + orderdetailsListOrphanCheckOrderdetails + " in its orderdetailsList field has a non-nullable products field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Productlines productLine = products.getProductLine();
            if (productLine != null) {
                productLine.getProductsList().remove(products);
                productLine = em.merge(productLine);
            }
            em.remove(products);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Products> findProductsEntities() {
        return findProductsEntities(true, -1, -1);
    }

    public List<Products> findProductsEntities(int maxResults, int firstResult) {
        return findProductsEntities(false, maxResults, firstResult);
    }

    private List<Products> findProductsEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Products.class));
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

    public Products findProducts(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Products.class, id);
        } finally {
            em.close();
        }
    }

    public int getProductsCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Products> rt = cq.from(Products.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
