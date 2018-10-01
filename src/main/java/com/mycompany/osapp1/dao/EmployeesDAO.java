package com.mycompany.osapp1.dao;

import java.util.List;

import com.mycompany.osapp1.entity.Employees;

public interface EmployeesDAO {
	
	int getEmployeesCount();
	List<Employees> findEmployeesEntities();
	Employees findEmployees(Integer id);

}
