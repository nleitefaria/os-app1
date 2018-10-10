package com.mycompany.osapp1.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.mycompany.osapp1.dao.OfficesDAO;
import com.mycompany.osapp1.dao.impl.OfficesDAOImpl;
import com.mycompany.osapp1.dao.impl.exceptions.IllegalOrphanException;
import com.mycompany.osapp1.dao.impl.exceptions.NonexistentEntityException;
import com.mycompany.osapp1.dao.impl.exceptions.PreexistingEntityException;
import com.mycompany.osapp1.domain.OfficeDTO;
import com.mycompany.osapp1.entity.Offices;
import com.mycompany.osapp1.service.OfficeService;

public class OfficeServiceImpl implements OfficeService 
{
	private EntityManagerFactory emf;
	private OfficesDAO dao;

	public OfficeServiceImpl() {
		emf = Persistence.createEntityManagerFactory("JavaApplicationClassicModelsPU");
		dao = new OfficesDAOImpl(emf);
	}

	public Integer count() {
		return dao.getOfficesCount();
	}

	public List<OfficeDTO> findAll() {
		List<OfficeDTO> ret = new ArrayList<OfficeDTO>();

		OfficeDTO officeDTO;
		for (Offices o : dao.findOfficesEntities()) {
			officeDTO = new OfficeDTO(o.getOfficeCode(), o.getCity(), o.getPhone(), o.getAddressLine1(),
					o.getAddressLine2(), o.getState(), o.getCountry(), o.getPostalCode(), o.getTerritory());
			ret.add(officeDTO);
		}

		return ret;
	}

	public OfficeDTO findOffices(String id) {
		Offices o = dao.findOffices(id);
		return new OfficeDTO(o.getOfficeCode(), o.getCity(), o.getPhone(), o.getAddressLine1(), o.getAddressLine2(),
				o.getState(), o.getCountry(), o.getPostalCode(), o.getTerritory());
	}

	public OfficeDTO create(OfficeDTO officeDTO) throws PreexistingEntityException, Exception {
		Offices o = new Offices(officeDTO.getOfficeCode(), officeDTO.getCity(), officeDTO.getPhone(),
				officeDTO.getAddressLine1(), officeDTO.getCountry(), officeDTO.getPostalCode(),
				officeDTO.getTerritory());
		dao.create(o);
		return officeDTO;
	}

	public Integer destroy(String id)
	{
		try 
		{
			dao.destroy(id);
			return 1;
		} 
		catch (IllegalOrphanException e) 
		{
			return -1;
		} 
		catch (NonexistentEntityException e)
		{
			return -1;
		}
	}

}
