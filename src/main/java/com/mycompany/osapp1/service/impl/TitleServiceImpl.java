package com.mycompany.osapp1.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.mycompany.osapp1.domain.TitleDTO;
import com.mycompany.osapp1.service.TitleService;

public class TitleServiceImpl implements TitleService
{
	public TitleServiceImpl()
	{
	}
	
	public List<TitleDTO> findAll()
	{
		List<TitleDTO> ret = new ArrayList<TitleDTO>();
		
		TitleDTO t1 = new TitleDTO(1, "t1");
		TitleDTO t2 = new TitleDTO(2, "t2");
		TitleDTO t3 = new TitleDTO(3, "t3");
		
		ret.add(t1);
		ret.add(t2);
		ret.add(t3);
		
		return ret;
	}
	

}
