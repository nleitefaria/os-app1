package com.mycompany.osapp1.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.mycompany.osapp1.domain.ProductDTO;
import com.mycompany.osapp1.service.ProductService;

public class ProductServiceImpl implements ProductService
{
	public List<ProductDTO> findAll()
	{
		List<ProductDTO> ret = new ArrayList<ProductDTO>();
		
		ProductDTO p1 = new ProductDTO();
		p1.setProductCode("pc1");
		
		return ret;
	}

}
