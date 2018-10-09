package com.mycompany.osapp1.rws;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.mycompany.osapp1.dao.impl.exceptions.PreexistingEntityException;
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
	@Path("/customer/{id}")
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
		
	@POST
	@Path("/customer")
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	public Response create(CustomerDTO customerDTO) 
	{
		service = new CustomerServiceImpl();
		try 
		{
			service.create(customerDTO);
			return Response.status(201).entity(customerDTO).build();	
		} 
		catch (PreexistingEntityException e) 
		{
			return Response.status(500).entity("An error occured").build();	
		} 
		catch (Exception e) 
		{
			return Response.status(500).entity("An error occured").build();	
		}
	}
	

}
