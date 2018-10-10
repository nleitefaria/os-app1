package com.mycompany.osapp1.service;

import java.util.List;

import com.mycompany.osapp1.domain.OrderDetailDTO;

public interface OrderDetailService 
{
	Integer count();
	List<OrderDetailDTO> findAll();
}
