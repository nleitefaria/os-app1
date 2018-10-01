package com.mycompany.osapp1.dao;

import java.util.List;

import com.mycompany.osapp1.entity.Customers;

public interface CustomersDAO 
{
	int getCustomersCount();
	List<Customers> findCustomersEntities();
	Customers findCustomers(Integer id);

}
