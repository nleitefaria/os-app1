package com.mycompany.osapp1.rws;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.mycompany.osapp1.service.PaymentsService;
import com.mycompany.osapp1.service.impl.PaymentsServiceImpl;

@Path("/rest")
public class PaymentsRWS 
{
	private PaymentsService service;
	
	@GET
	@Path("/payments")
	@Produces({MediaType.APPLICATION_JSON})
	public Response findAll()
	{
		service = new PaymentsServiceImpl();
		return Response.status(200).entity(service.findAll()).build();
	}

}
