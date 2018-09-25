package com.mycompany.osapp1.rws;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/rest")
public class HelloRWS 
{
	@GET
	@Path("/")
	@Produces({MediaType.APPLICATION_JSON})
	public Response sayHello()
	{		
		return Response.status(200).entity("Hello").build();	
	}
}
