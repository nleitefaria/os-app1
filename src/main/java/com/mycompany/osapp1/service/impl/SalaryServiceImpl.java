package com.mycompany.osapp1.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.mycompany.osapp1.domain.SalaryDTO;
import com.mycompany.osapp1.service.SalaryService;

public class SalaryServiceImpl implements SalaryService 
{
	public SalaryServiceImpl()
	{
	}
	
	public List<SalaryDTO> findAll()
	{
		List<SalaryDTO> ret = new ArrayList<SalaryDTO>();
		
		SalaryDTO s1 = new SalaryDTO(1, 1500);
		SalaryDTO s2 = new SalaryDTO(2, 2000);
		
		ret.add(s1);
		ret.add(s2);
		
		return ret;
	}
	

}
