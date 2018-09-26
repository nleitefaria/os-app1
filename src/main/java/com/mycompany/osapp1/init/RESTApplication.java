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
		BeanConfig beanConfig = new BeanConfig();
        beanConfig.setVersion("1.0.2");
        beanConfig.setSchemes(new String[]{"http"});
        beanConfig.setHost("http://os-app1-os-app1.a3c1.starter-us-west-1.openshiftapps.com");
        beanConfig.setBasePath("/api");
        beanConfig.setResourcePackage("io.swagger.resources");
        beanConfig.setScan(true);
		
		
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