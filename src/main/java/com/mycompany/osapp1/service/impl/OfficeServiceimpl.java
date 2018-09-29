package com.mycompany.osapp1.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.mycompany.osapp1.dao.OfficesDAO;
import com.mycompany.osapp1.dao.impl.OfficesDAOImpl;
import com.mycompany.osapp1.domain.OfficeDTO;
import com.mycompany.osapp1.entity.Offices;
import com.mycompany.osapp1.service.OfficeService;

public class OfficeServiceimpl implements OfficeService
{
	private EntityManagerFactory emf;
	private OfficesDAO dao;
	
	public OfficeServiceimpl()
	{
		emf = Persistence.createEntityManagerFactory("JavaApplicationClassicModelsPU"); 
		dao = new OfficesDAOImpl(emf);		
	}
		
	public Integer count()
	{		
		return dao.getOfficesCount();
	}
	
	public List<OfficeDTO> findAll()
	{
		List<OfficeDTO> ret = new ArrayList<OfficeDTO>();
		
		OfficeDTO officeDTO;
		for(Offices o : dao.findOfficesEntities())
		{
			officeDTO = new OfficeDTO(o.getOfficeCode(), o.getCity(), o.getPhone(), o.getAddressLine1(), o.getAddressLine2(), o.getState(), o.getCountry(), o.getPostalCode(), o.getTerritory());
			ret.add(officeDTO);
		}
		
		return ret;
	}

}
