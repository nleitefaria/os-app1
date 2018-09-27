package com.mycompany.osapp1.rws;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.mycompany.osapp1.service.OrdersService;
import com.mycompany.osapp1.service.impl.OrdersServiceImpl;

@Path("/rest")
public class OrdersRWS 
{
	private OrdersService service;
	
	@GET
	@Path("/orders")
	@Produces({MediaType.APPLICATION_JSON})
	public Response findAll()
	{
		service = new OrdersServiceImpl();
		return Response.status(200).entity(service.findAll()).build();
	}
	

}
