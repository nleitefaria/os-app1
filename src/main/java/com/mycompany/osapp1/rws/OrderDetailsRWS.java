package com.mycompany.osapp1.rws;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.mycompany.osapp1.service.OrderDetailService;
import com.mycompany.osapp1.service.impl.OrderDetailServiceImpl;

@Path("/rest")
public class OrderDetailsRWS 
{	
	private OrderDetailService service;
	
	@GET
	@Path("/orderdetails")
	@Produces({MediaType.APPLICATION_JSON})
	public Response findAll()
	{
		service = new OrderDetailServiceImpl();
		return Response.status(200).entity(service.findAll()).build();
	}
}
