package com.mycompany.osapp1.service;

import java.util.List;

import com.mycompany.osapp1.domain.OfficeDTO;

public interface OfficeService 
{
	Integer count();
	List<OfficeDTO> findAll();
	OfficeDTO findOffices(String id);

}
