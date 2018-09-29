/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.osapp1.dao.impl;

import com.mycompany.osapp1.dao.impl.exceptions.IllegalOrphanException;
import com.mycompany.osapp1.dao.impl.exceptions.NonexistentEntityException;
import com.mycompany.osapp1.dao.impl.exceptions.PreexistingEntityException;
import com.mycompany.osapp1.entity.Productlines;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.mycompany.osapp1.entity.Products;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author nleit_000
 */
public class ProductlinesDAOImpl implements Serializable {

    public ProductlinesDAOImpl(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Productlines productlines) throws PreexistingEntityException, Exception {
        if (productlines.getProductsList() == null) {
            productlines.setProductsList(new ArrayList<Products>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Products> attachedProductsList = new ArrayList<Products>();
            for (Products productsListProductsToAttach : productlines.getProductsList()) {
                productsListProductsToAttach = em.getReference(productsListProductsToAttach.getClass(), productsListProductsToAttach.getProductCode());
                attachedProductsList.add(productsListProductsToAttach);
            }
            productlines.setProductsList(attachedProductsList);
            em.persist(productlines);
            for (Products productsListProducts : productlines.getProductsList()) {
                Productlines oldProductLineOfProductsListProducts = productsListProducts.getProductLine();
                productsListProducts.setProductLine(productlines);
                productsListProducts = em.merge(productsListProducts);
                if (oldProductLineOfProductsListProducts != null) {
                    oldProductLineOfProductsListProducts.getProductsList().remove(productsListProducts);
                    oldProductLineOfProductsListProducts = em.merge(oldProductLineOfProductsListProducts);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findProductlines(productlines.getProductLine()) != null) {
                throw new PreexistingEntityException("Productlines " + productlines + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Productlines productlines) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Productlines persistentProductlines = em.find(Productlines.class, productlines.getProductLine());
            List<Products> productsListOld = persistentProductlines.getProductsList();
            List<Products> productsListNew = productlines.getProductsList();
            List<String> illegalOrphanMessages = null;
            for (Products productsListOldProducts : productsListOld) {
                if (!productsListNew.contains(productsListOldProducts)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Products " + productsListOldProducts + " since its productLine field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Products> attachedProductsListNew = new ArrayList<Products>();
            for (Products productsListNewProductsToAttach : productsListNew) {
                productsListNewProductsToAttach = em.getReference(productsListNewProductsToAttach.getClass(), productsListNewProductsToAttach.getProductCode());
                attachedProductsListNew.add(productsListNewProductsToAttach);
            }
            productsListNew = attachedProductsListNew;
            productlines.setProductsList(productsListNew);
            productlines = em.merge(productlines);
            for (Products productsListNewProducts : productsListNew) {
                if (!productsListOld.contains(productsListNewProducts)) {
                    Productlines oldProductLineOfProductsListNewProducts = productsListNewProducts.getProductLine();
                    productsListNewProducts.setProductLine(productlines);
                    productsListNewProducts = em.merge(productsListNewProducts);
                    if (oldProductLineOfProductsListNewProducts != null && !oldProductLineOfProductsListNewProducts.equals(productlines)) {
                        oldProductLineOfProductsListNewProducts.getProductsList().remove(productsListNewProducts);
                        oldProductLineOfProductsListNewProducts = em.merge(oldProductLineOfProductsListNewProducts);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = productlines.getProductLine();
                if (findProductlines(id) == null) {
                    throw new NonexistentEntityException("The productlines with id " + id + " no longer exists.");
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
            Productlines productlines;
            try {
                productlines = em.getReference(Productlines.class, id);
                productlines.getProductLine();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The productlines with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Products> productsListOrphanCheck = productlines.getProductsList();
            for (Products productsListOrphanCheckProducts : productsListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Productlines (" + productlines + ") cannot be destroyed since the Products " + productsListOrphanCheckProducts + " in its productsList field has a non-nullable productLine field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(productlines);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Productlines> findProductlinesEntities() {
        return findProductlinesEntities(true, -1, -1);
    }

    public List<Productlines> findProductlinesEntities(int maxResults, int firstResult) {
        return findProductlinesEntities(false, maxResults, firstResult);
    }

    private List<Productlines> findProductlinesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Productlines.class));
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

    public Productlines findProductlines(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Productlines.class, id);
        } finally {
            em.close();
        }
    }

    public int getProductlinesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Productlines> rt = cq.from(Productlines.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
