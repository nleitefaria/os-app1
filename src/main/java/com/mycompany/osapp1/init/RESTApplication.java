package com.mycompany.osapp1.init;

import java.util.HashSet;
import java.util.Set;

import com.mycompany.osapp1.rws.CustomersRWS;
import com.mycompany.osapp1.rws.EmployeesRWS;
import com.mycompany.osapp1.rws.HelloRWS;

public class RESTApplication extends javax.ws.rs.core.Application 
{

	private Set<Object> singletons = new HashSet<Object>();

	public RESTApplication() 
	{		
		singletons.add(new HelloRWS());
		singletons.add(new CustomersRWS());
		singletons.add(new EmployeesRWS());
	}

	@Override
	public Set<Object> getSingletons() {
		return singletons;
	}
	
	
	
	
	
	
}