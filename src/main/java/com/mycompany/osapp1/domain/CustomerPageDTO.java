package com.mycompany.osapp1.domain;

import java.util.List;

public class CustomerPageDTO 
{	
	private List<CustomerDTO> customerPage;
	private int total;
	
	public CustomerPageDTO(List<CustomerDTO> customerPage, int total) {
		
		this.customerPage = customerPage;
		this.total = total;
	}
	
	public List<CustomerDTO> getCustomerPage() {
		return customerPage;
	}
	
	public void setCustomerPage(List<CustomerDTO> customerPage) {
		this.customerPage = customerPage;
	}
	
	public int getTotal() {
		return total;
	}
	
	public void setTotal(int total) {
		this.total = total;
	}

}