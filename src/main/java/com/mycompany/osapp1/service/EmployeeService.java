package com.mycompany.osapp1.service;

import java.util.List;

import com.mycompany.osapp1.domain.EmployeeDTO;

public interface EmployeeService 
{
	Integer count();
	List<EmployeeDTO> findAll();
	EmployeeDTO findEmployees(String id);
}
