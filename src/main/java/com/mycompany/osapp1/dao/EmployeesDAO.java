package com.mycompany.osapp1.dao;

import java.util.List;

import com.mycompany.osapp1.dao.impl.exceptions.NonexistentEntityException;
import com.mycompany.osapp1.dao.impl.exceptions.PreexistingEntityException;
import com.mycompany.osapp1.entity.Employees;

public interface EmployeesDAO 
{
	int getEmployeesCount();
	List<Employees> findEmployeesEntities();
	Employees findEmployees(Integer id);
	void create(Employees employees) throws PreexistingEntityException, Exception;
	void destroy(Integer id) throws NonexistentEntityException;
}
