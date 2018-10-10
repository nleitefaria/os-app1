package com.mycompany.osapp1.dao;

import java.util.List;

import com.mycompany.osapp1.dao.impl.exceptions.NonexistentEntityException;
import com.mycompany.osapp1.dao.impl.exceptions.PreexistingEntityException;
import com.mycompany.osapp1.entity.Orderdetails;
import com.mycompany.osapp1.entity.OrderdetailsPK;

public interface OrderdetailsDAO
{
	int getOrderdetailsCount();
	List<Orderdetails> findOrderdetailsEntities();
	Orderdetails findOrderdetails(OrderdetailsPK id);
	void create(Orderdetails orderdetails) throws PreexistingEntityException, Exception;
	void destroy(OrderdetailsPK id) throws NonexistentEntityException;
}
