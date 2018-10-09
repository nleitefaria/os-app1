package com.mycompany.osapp1.service;

import java.util.List;

import com.mycompany.osapp1.dao.impl.exceptions.PreexistingEntityException;
import com.mycompany.osapp1.domain.OfficeDTO;

public interface OfficeService 
{
	Integer count();
	List<OfficeDTO> findAll();
	OfficeDTO findOffices(String id);
	OfficeDTO create(OfficeDTO officeDTO) throws PreexistingEntityException, Exception;
	Integer destroy(String id);
}
