package com.mycompany.osapp1.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.mycompany.osapp1.dao.CustomersDAO;
import com.mycompany.osapp1.dao.impl.CustomersDAOImpl;
import com.mycompany.osapp1.domain.CustomerDTO;
import com.mycompany.osapp1.entity.Customers;
import com.mycompany.osapp1.service.CustomerService;

public class CustomerServiceImpl implements CustomerService 
{
	
	private EntityManagerFactory emf;
	private CustomersDAO dao;
	
	public CustomerServiceImpl()
	{
		emf = Persistence.createEntityManagerFactory("JavaApplicationClassicModelsPU"); 
		dao = new CustomersDAOImpl(emf);		
	}
		
	public Integer count()
	{		
		return dao.getCustomersCount();
	}
	
	public List<CustomerDTO> findAll()
	{
		List<CustomerDTO> ret = new ArrayList<CustomerDTO>();
		
		CustomerDTO customerDTO;
		for(Customers c : dao.findCustomersEntities())
		{
			customerDTO = new CustomerDTO(c.getCustomerNumber(), c.getCustomerName(), c.getContactLastName(), c.getContactFirstName(), c.getPhone(), c.getAddressLine1(), c.getAddressLine2(), c.getCity(), c.getState(), c.getPostalCode(), c.getCountry(), c.getCreditLimit());
			ret.add(customerDTO);
		}
		
		return ret;
	}
	
	public CustomerDTO findCustomers(String id)
	{
		Customers c = dao.findCustomers(Integer.parseInt(id));
		return new CustomerDTO(c.getCustomerNumber(), c.getCustomerName(), c.getContactLastName(), c.getContactFirstName(), c.getPhone(), c.getAddressLine1(), c.getAddressLine2(), c.getCity(), c.getState(), c.getPostalCode(), c.getCountry(), c.getCreditLimit());		
	}
	

}
