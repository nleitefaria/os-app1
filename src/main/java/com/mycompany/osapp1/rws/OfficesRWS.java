package com.mycompany.osapp1.rws;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.mycompany.osapp1.service.OfficeService;
import com.mycompany.osapp1.service.impl.OfficeServiceimpl;

@Path("/rest")
public class OfficesRWS 
{
	private OfficeService service;
	
	@GET
	@Path("/oficces")
	@Produces({MediaType.APPLICATION_JSON})
	public Response findAll()
	{
		service = new OfficeServiceimpl();
		return Response.status(200).entity(service.findAll()).build();
	}
	

}
