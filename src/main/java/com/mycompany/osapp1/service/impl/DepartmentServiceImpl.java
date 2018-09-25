package com.mycompany.osapp1.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.mycompany.osapp1.domain.DepartmentDTO;
import com.mycompany.osapp1.service.DepartmentService;

public class DepartmentServiceImpl implements DepartmentService
{
	public DepartmentServiceImpl()
	{
	}
	
	public List<DepartmentDTO> findAll()
	{
		List<DepartmentDTO> ret = new ArrayList<DepartmentDTO>();
		
		DepartmentDTO d1 = new DepartmentDTO(1, "d1");
		DepartmentDTO d2 = new DepartmentDTO(2, "d2");
		DepartmentDTO d3 = new DepartmentDTO(1, "d3");
		
		ret.add(d1);
		ret.add(d2);
		ret.add(d3);
		
		return ret;
	}
	

}
