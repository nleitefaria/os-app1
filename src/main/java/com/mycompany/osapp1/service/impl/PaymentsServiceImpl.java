package com.mycompany.osapp1.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.mycompany.osapp1.domain.PaymentDTO;
import com.mycompany.osapp1.service.PaymentsService;

public class PaymentsServiceImpl implements PaymentsService
{
	public List<PaymentDTO> findAll()
	{
		List<PaymentDTO> ret = new ArrayList<PaymentDTO>();
		
		PaymentDTO p1 = new PaymentDTO();
		p1.setAmount(new BigDecimal(1000));
		
		PaymentDTO p2 = new PaymentDTO();
		p2.setAmount(new BigDecimal(2000));
		
		return ret;
	}

}
