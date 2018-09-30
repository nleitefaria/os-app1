package com.mycompany.osapp1.dao;

import java.util.List;

import com.mycompany.osapp1.entity.Offices;

public interface OfficesDAO
{
	int getOfficesCount();
	List<Offices> findOfficesEntities();
	Offices findOffices(String id);

}
