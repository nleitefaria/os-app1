package com.mycompany.osapp1.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.mycompany.osapp1.domain.CustomerDTO;
import com.mycompany.osapp1.service.CustomerService;

public class CustomerServiceimpl implements CustomerService 
{	
	public List<CustomerDTO> findAll()
	{
		List<CustomerDTO> ret = new ArrayList<CustomerDTO>();
		
		CustomerDTO c1 = new CustomerDTO();
		c1.setContactFirstName("c1");
		
		CustomerDTO c2 = new CustomerDTO();
		c2.setContactFirstName("c1");
		
		ret.add(c1);
		ret.add(c2);
		
		return ret;
	}
	
	

}
