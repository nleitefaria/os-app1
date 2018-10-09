package com.mycompany.osapp1.dao;

import java.util.List;

import com.mycompany.osapp1.dao.impl.exceptions.IllegalOrphanException;
import com.mycompany.osapp1.dao.impl.exceptions.NonexistentEntityException;
import com.mycompany.osapp1.dao.impl.exceptions.PreexistingEntityException;
import com.mycompany.osapp1.entity.Offices;

public interface OfficesDAO
{
	int getOfficesCount();
	List<Offices> findOfficesEntities();
	Offices findOffices(String id);
	void create(Offices offices) throws PreexistingEntityException, Exception;
	void destroy(String id) throws IllegalOrphanException, NonexistentEntityException;

}
