package com.mycompany.osapp1.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.mycompany.osapp1.dao.OfficesDAO;
import com.mycompany.osapp1.dao.OrderdetailsDAO;
import com.mycompany.osapp1.dao.impl.OfficesDAOImpl;
import com.mycompany.osapp1.dao.impl.OrderdetailsDAOImpl;
import com.mycompany.osapp1.domain.OfficeDTO;
import com.mycompany.osapp1.domain.OrderDetailDTO;
import com.mycompany.osapp1.entity.Offices;
import com.mycompany.osapp1.entity.Orderdetails;
import com.mycompany.osapp1.service.OrderDetailService;

public class OrderDetailServiceImpl implements OrderDetailService
{
	private EntityManagerFactory emf;
	private OrderdetailsDAO dao;

	public OrderDetailServiceImpl() 
	{
		emf = Persistence.createEntityManagerFactory("JavaApplicationClassicModelsPU");
		dao = new OrderdetailsDAOImpl(emf);
	}
	
	public Integer count() 
	{
		return dao.getOrderdetailsCount();
	}

	public List<OrderDetailDTO> findAll() 
	{
		List<OrderDetailDTO> ret = new ArrayList<OrderDetailDTO>();

		OrderDetailDTO orderDetailDTO;
		
		for (Orderdetails od : dao.findOrderdetailsEntities())
		{
			orderDetailDTO = new OrderDetailDTO(od.getQuantityOrdered(), od.getPriceEach(), od.getOrderLineNumber());
			ret.add(orderDetailDTO);
		}

		return ret;
	}

}
