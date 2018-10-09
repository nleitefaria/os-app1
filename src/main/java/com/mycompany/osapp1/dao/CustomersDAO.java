package com.mycompany.osapp1.dao;

import java.util.List;

import com.mycompany.osapp1.dao.impl.exceptions.PreexistingEntityException;
import com.mycompany.osapp1.entity.Customers;

public interface CustomersDAO 
{
	int getCustomersCount();
	List<Customers> findCustomersEntities();
	Customers findCustomers(Integer id);
	void create(Customers customers) throws PreexistingEntityException, Exception;

}
