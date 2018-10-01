package com.mycompany.osapp1.service;

import java.util.List;

import com.mycompany.osapp1.domain.OrderDTO;

public interface OrderService 
{
	Integer count();
	List<OrderDTO> findAll();
	OrderDTO findOrders(String id);

}
