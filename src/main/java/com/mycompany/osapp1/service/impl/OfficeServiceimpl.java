package com.mycompany.osapp1.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.mycompany.osapp1.domain.OfficeDTO;
import com.mycompany.osapp1.service.OfficeService;

public class OfficeServiceimpl implements OfficeService
{
	public List<OfficeDTO> findAll()
	{
		List<OfficeDTO> ret = new ArrayList<OfficeDTO>();
		
		OfficeDTO o1 = new OfficeDTO();
		o1.setCountry("Portugal");
		
		OfficeDTO o2 = new OfficeDTO();
		o2.setCountry("Spain");
		
		ret.add(o1);
		ret.add(o2);
		
		return ret;
	}

}
