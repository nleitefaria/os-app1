package com.mycompany.osapp1.service;

import java.util.List;

import com.mycompany.osapp1.domain.OrderDTO;

public interface OrderService {
	
	List<OrderDTO> findAll();

}
