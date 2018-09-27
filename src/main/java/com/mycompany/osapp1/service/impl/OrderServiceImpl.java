package com.mycompany.osapp1.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.mycompany.osapp1.domain.OrderDTO;
import com.mycompany.osapp1.service.OrderService;

public class OrderServiceImpl implements OrderService
{
	public List<OrderDTO> findAll()
	{
		List<OrderDTO> ret = new ArrayList<OrderDTO>();
		
		OrderDTO o1 = new OrderDTO();
		o1.setStatus("active");
		
		OrderDTO o2 = new OrderDTO();
		o2.setStatus("pending");
		
		return ret;
	}
	
	
	
	

}
