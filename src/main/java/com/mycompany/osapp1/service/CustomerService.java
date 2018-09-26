package com.mycompany.osapp1.service;

import java.util.List;

import com.mycompany.osapp1.domain.CustomerDTO;

public interface CustomerService {
	
	List<CustomerDTO> findAll();

}
