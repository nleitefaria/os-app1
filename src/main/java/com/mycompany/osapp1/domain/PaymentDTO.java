package com.mycompany.osapp1.domain;

import java.math.BigDecimal;
import java.util.Date;

public class PaymentDTO 
{
    private Date paymentDate;
    private BigDecimal amount;
    
	public PaymentDTO() {
	}

	public PaymentDTO(Date paymentDate, BigDecimal amount) {
		
		this.paymentDate = paymentDate;
		this.amount = amount;
	}

	public Date getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

}
