package com.mycompany.osapp1.domain;

import java.math.BigDecimal;

public class OrderDetailDTO 
{
    private int quantityOrdered;
    private BigDecimal priceEach;
    private short orderLineNumber;
    
	public OrderDetailDTO() 
	{	
	}

	public OrderDetailDTO(int quantityOrdered, BigDecimal priceEach, short orderLineNumber) 
	{		
		this.quantityOrdered = quantityOrdered;
		this.priceEach = priceEach;
		this.orderLineNumber = orderLineNumber;
	}

	public int getQuantityOrdered() {
		return quantityOrdered;
	}

	public void setQuantityOrdered(int quantityOrdered) {
		this.quantityOrdered = quantityOrdered;
	}

	public BigDecimal getPriceEach() {
		return priceEach;
	}

	public void setPriceEach(BigDecimal priceEach) {
		this.priceEach = priceEach;
	}

	public short getOrderLineNumber() {
		return orderLineNumber;
	}

	public void setOrderLineNumber(short orderLineNumber) {
		this.orderLineNumber = orderLineNumber;
	}

}
