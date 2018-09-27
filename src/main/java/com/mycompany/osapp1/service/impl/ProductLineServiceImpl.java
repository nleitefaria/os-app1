package com.mycompany.osapp1.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.mycompany.osapp1.domain.ProductLineDTO;
import com.mycompany.osapp1.service.ProductLineService;

public class ProductLineServiceImpl implements ProductLineService 
{
	public List<ProductLineDTO> findAll()
	{
		List<ProductLineDTO> ret = new ArrayList<ProductLineDTO>();
		
		ProductLineDTO p1 = new ProductLineDTO();
		p1.setProductLine("cd1");
		
		return ret;
	}
	
	

}
