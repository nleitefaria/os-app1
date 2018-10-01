package com.mycompany.osapp1.rws;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.mycompany.osapp1.domain.CustomerDTO;
import com.mycompany.osapp1.service.CustomerService;
import com.mycompany.osapp1.service.impl.CustomerServiceImpl;

@Path("/rest")
public class CustomersRWS 
{
	private CustomerService service;
	
	@GET
	@Path("/customers/count")
	@Produces({MediaType.APPLICATION_JSON})
	public Response count()
	{
		service = new CustomerServiceImpl();
		return Response.status(200).entity(service.count()).build();
	}
	
	@GET
	@Path("/customers")
	@Produces({MediaType.APPLICATION_JSON})
	public Response findAll()
	{
		service = new CustomerServiceImpl();
		return Response.status(200).entity(service.findAll()).build();
	}
	
	@GET
	@Path("/customers/{id}")
	@Produces({MediaType.APPLICATION_JSON})
	public Response findOffices(@PathParam("id") String id)
	{
		service = new CustomerServiceImpl();
		CustomerDTO customerDTO = service.findCustomers(id);
		if(customerDTO != null)
		{
			return Response.status(200).entity(customerDTO).build();			
		}
		else
		{
			return Response.status(204).entity("No entity for id: " + id).build();
		}	
	}
	

}
