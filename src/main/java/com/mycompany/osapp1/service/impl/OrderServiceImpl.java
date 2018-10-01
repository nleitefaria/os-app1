package com.mycompany.osapp1.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.mycompany.osapp1.dao.OrdersDAO;
import com.mycompany.osapp1.dao.impl.OrdersDAOImpl;
import com.mycompany.osapp1.domain.OrderDTO;
import com.mycompany.osapp1.entity.Orders;
import com.mycompany.osapp1.service.OrderService;

public class OrderServiceImpl implements OrderService
{
	private EntityManagerFactory emf;
	private OrdersDAO dao;
	
	public OrderServiceImpl()
	{
		emf = Persistence.createEntityManagerFactory("JavaApplicationClassicModelsPU"); 
		dao = new OrdersDAOImpl(emf);		
	}
		
	public Integer count()
	{		
		return dao.getOrdersCount();
	}
		
	public List<OrderDTO> findAll()
	{
		List<OrderDTO> ret = new ArrayList<OrderDTO>();
		
		OrderDTO orderDTO;
		for(Orders o : dao.findOrdersEntities())
		{
			orderDTO = new OrderDTO(o.getOrderNumber(), o.getOrderDate(), o.getRequiredDate(), o.getShippedDate(), o.getStatus(), o.getComments());
			ret.add(orderDTO);
		}
		
		return ret;
	}
	
	public OrderDTO findOrders(String id)
	{
		Orders o = dao.findOrders(Integer.parseInt(id));
		return new OrderDTO(o.getOrderNumber(), o.getOrderDate(), o.getRequiredDate(), o.getShippedDate(), o.getStatus(), o.getComments());		
	}
	
	
	
	

}
