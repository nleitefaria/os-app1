package com.mycompany.osapp1.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.mycompany.osapp1.domain.EmployeeDTO;
import com.mycompany.osapp1.service.EmployeeService;

public class EmployeeServiceimpl implements EmployeeService
{
	public EmployeeServiceimpl()
	{
	}
	
	public List<EmployeeDTO> findAll()
	{
		List<EmployeeDTO> ret = new ArrayList<EmployeeDTO>();
		
		EmployeeDTO e1 = new EmployeeDTO(1, "fn1", "ln1");
		EmployeeDTO e2 = new EmployeeDTO(2, "fn2", "ln2");
		EmployeeDTO e3 = new EmployeeDTO(3, "fn3", "ln3");
		
		ret.add(e1);
		ret.add(e2);
		ret.add(e3);
		
		return ret;
	}
	
	

}
