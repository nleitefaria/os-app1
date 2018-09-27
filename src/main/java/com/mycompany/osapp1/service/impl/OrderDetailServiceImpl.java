package com.mycompany.osapp1.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.mycompany.osapp1.domain.OrderDetailDTO;
import com.mycompany.osapp1.service.OrderDetailService;

public class OrderDetailServiceImpl implements OrderDetailService
{
	public List<OrderDetailDTO> findAll()
	{
		List<OrderDetailDTO> ret = new ArrayList<OrderDetailDTO>();
		
		Integer oln = new Integer(100);
		OrderDetailDTO od1 = new OrderDetailDTO();
		od1.setOrderLineNumber(oln.shortValue());
		
		oln = new Integer(200);
		OrderDetailDTO od2 = new OrderDetailDTO();
		od2.setOrderLineNumber(oln.shortValue());
		
		ret.add(od1);
		ret.add(od2);
		
		return ret;
	}

}
