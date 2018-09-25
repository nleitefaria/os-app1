package com.mycompany.osapp1.rws;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import com.mycompany.osapp1.service.TitleService;
import com.mycompany.osapp1.service.impl.TitleServiceImpl;

@Path("/rest")
public class TitlesRWS 
{
	private TitleService service;
	
	@GET
	@Path("/titles")
	@Produces({MediaType.APPLICATION_JSON})
	public Response findAll()
	{
		service = new TitleServiceImpl(); 	
		return Response.status(200).entity(service.findAll()).build();	
	}

}
