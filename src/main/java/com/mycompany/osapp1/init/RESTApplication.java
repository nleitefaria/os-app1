package com.mycompany.osapp1.init;

import java.util.HashSet;
import java.util.Set;

import com.mycompany.osapp1.rws.DepartmentsRWS;
import com.mycompany.osapp1.rws.EmployeesRWS;
import com.mycompany.osapp1.rws.HelloRWS;
import com.mycompany.osapp1.rws.SalariesRWS;
import com.mycompany.osapp1.rws.TitlesRWS;

public class RESTApplication extends javax.ws.rs.core.Application 
{

	private Set<Object> singletons = new HashSet<Object>();

	public RESTApplication() 
	{
		singletons.add(new HelloRWS());
		singletons.add(new TitlesRWS());
		singletons.add(new SalariesRWS());		
		singletons.add(new DepartmentsRWS());
		singletons.add(new EmployeesRWS());
		
		singletons.add(io.swagger.jaxrs.listing.ApiListingResource.class);
		singletons.add(io.swagger.jaxrs.listing.SwaggerSerializers.class);
	}

	@Override
	public Set<Object> getSingletons() {
		return singletons;
	}
}