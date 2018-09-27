package com.mycompany.osapp1.service;

import java.util.List;

import com.mycompany.osapp1.domain.PaymentDTO;

public interface PaymentsService
{
	List<PaymentDTO> findAll();

}
