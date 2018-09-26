package com.mycompany.osapp1.rws;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.mycompany.osapp1.service.CustomerService;
import com.mycompany.osapp1.service.impl.CustomerServiceimpl;

@Path("/rest")
public class CustomersRWS 
{
	private CustomerService service;
	
	@GET
	@Path("/customers")
	@Produces({MediaType.APPLICATION_JSON})
	public Response findAll()
	{
		service = new CustomerServiceimpl();
		return Response.status(200).entity(service.findAll()).build();
	}
	
	
	

}
