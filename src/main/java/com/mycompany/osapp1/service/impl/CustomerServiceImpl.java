package com.mycompany.osapp1.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.mycompany.osapp1.dao.CustomersDAO;
import com.mycompany.osapp1.dao.impl.CustomersDAOImpl;
import com.mycompany.osapp1.dao.impl.exceptions.IllegalOrphanException;
import com.mycompany.osapp1.dao.impl.exceptions.NonexistentEntityException;
import com.mycompany.osapp1.dao.impl.exceptions.PreexistingEntityException;
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
	
	public CustomerDTO create(CustomerDTO customerDTO) throws PreexistingEntityException, Exception
	{
		Customers c = new Customers(customerDTO.getCustomerNumber(), customerDTO.getCustomerName(), customerDTO.getContactLastName(), customerDTO.getContactFirstName(), customerDTO.getPhone(), customerDTO.getAddressLine1(), customerDTO.getCity(), customerDTO.getCountry());
		dao.create(c);
		return customerDTO;
	}
	
	public Integer destroy(Integer id)
	{
		try {
			dao.destroy(id);
			return 1;
		} catch (IllegalOrphanException e) {
			return 0;
		} catch (NonexistentEntityException e) {
			return -1;
		}
	}
}
