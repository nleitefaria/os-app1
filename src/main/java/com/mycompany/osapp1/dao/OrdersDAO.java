package com.mycompany.osapp1.dao;

import java.util.List;

import com.mycompany.osapp1.entity.Orders;

public interface OrdersDAO {
	
	int getOrdersCount();
	List<Orders> findOrdersEntities();
	Orders findOrders(Integer id);

}
