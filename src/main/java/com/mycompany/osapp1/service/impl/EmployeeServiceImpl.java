package com.mycompany.osapp1.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.mycompany.osapp1.dao.EmployeesDAO;
import com.mycompany.osapp1.dao.impl.EmployeesDAOImpl;
import com.mycompany.osapp1.dao.impl.exceptions.NonexistentEntityException;
import com.mycompany.osapp1.dao.impl.exceptions.PreexistingEntityException;
import com.mycompany.osapp1.domain.EmployeeDTO;
import com.mycompany.osapp1.entity.Employees;
import com.mycompany.osapp1.service.EmployeeService;

public class EmployeeServiceImpl implements EmployeeService
{
	private EntityManagerFactory emf;
	private EmployeesDAO dao;
	
	public EmployeeServiceImpl()
	{
		emf = Persistence.createEntityManagerFactory("JavaApplicationClassicModelsPU"); 
		dao = new EmployeesDAOImpl(emf);	
	}
	
	public Integer count()
	{		
		return dao.getEmployeesCount();
	}
	
	public List<EmployeeDTO> findAll()
	{
		List<EmployeeDTO> ret = new ArrayList<EmployeeDTO>();
		
		EmployeeDTO employeeDTO;
		for(Employees e : dao.findEmployeesEntities())
		{
			employeeDTO = new EmployeeDTO(e.getEmployeeNumber(), e.getLastName(), e.getFirstName(), e.getExtension(), e.getEmail(), e.getJobTitle());
			ret.add(employeeDTO);
		}
		
		return ret;
	}
	
	public EmployeeDTO findEmployees(String id)
	{
		Employees e = dao.findEmployees(Integer.parseInt(id));
		return new EmployeeDTO(e.getEmployeeNumber(), e.getLastName(), e.getFirstName(), e.getExtension(), e.getEmail(), e.getJobTitle());	
	}
	
	public EmployeeDTO create(EmployeeDTO employeeDTO) throws PreexistingEntityException, Exception
	{
		Employees e = new Employees(employeeDTO.getEmployeeNumber(), employeeDTO.getLastName(), employeeDTO.getFirstName(), employeeDTO.getExtension(), employeeDTO.getEmail(), employeeDTO.getJobTitle());
		dao.create(e);
		return employeeDTO;
	}
	
	public Integer destroy(Integer id)
	{	
		try 
		{
			dao.destroy(id);
			return 1;
		} 
		catch (NonexistentEntityException e) 
		{
			return -1;
		}		
	}
	
	
	
	
	

}
