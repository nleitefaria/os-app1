package com.mycompany.osapp1.domain;

public class SalaryDTO 
{
	private Integer id;
	private Integer salary;
	
	public SalaryDTO() 
	{
	}
	
	public SalaryDTO(Integer id, Integer salary) 
	{
		this.id = id;
		this.salary = salary;
	}
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getSalary() {
		return salary;
	}
	
	public void setSalary(Integer salary) {
		this.salary = salary;
	}
	
	
	
	

}
