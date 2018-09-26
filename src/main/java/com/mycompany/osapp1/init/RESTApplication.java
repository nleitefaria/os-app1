package com.mycompany.osapp1.init;

import java.util.HashSet;
import java.util.Set;

import com.mycompany.osapp1.rws.DepartmentsRWS;
import com.mycompany.osapp1.rws.EmployeesRWS;
import com.mycompany.osapp1.rws.HelloRWS;
import com.mycompany.osapp1.rws.SalariesRWS;
import com.mycompany.osapp1.rws.TitlesRWS;

import io.swagger.jaxrs.config.BeanConfig;

public class RESTApplication extends javax.ws.rs.core.Application 
{

	private Set<Object> singletons = new HashSet<Object>();

	public RESTApplication() 
	{	
		configureSwagger();
		
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
	
	
	private void configureSwagger() 
	{
		
		System.out.println("*****************");
		System.out.println("@configureSwagger");
		System.out.println("*****************");
		
	    BeanConfig beanConfig = new BeanConfig();
	    beanConfig.setVersion("1.0.0");
	    beanConfig.setSchemes(new String[] { "http" });
	    beanConfig.setHost("http://localhost:8080/osapp1-1.0");
	    beanConfig.setBasePath("/api");
	    beanConfig.setResourcePackage("com.mycompany.osapp1.rws");
	    beanConfig.setTitle("RESTEasy, ...");
	    beanConfig.setDescription("Sample application to demonstrate ...");
	    beanConfig.setScan(true);
	  }
	
	
	
}