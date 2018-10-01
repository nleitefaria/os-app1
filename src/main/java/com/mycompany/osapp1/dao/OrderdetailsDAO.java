package com.mycompany.osapp1.dao;

import java.util.List;

import com.mycompany.osapp1.entity.Orderdetails;
import com.mycompany.osapp1.entity.OrderdetailsPK;

public interface OrderdetailsDAO {
	
	int getOrderdetailsCount();
	List<Orderdetails> findOrderdetailsEntities();
	Orderdetails findOrderdetails(OrderdetailsPK id);

}
