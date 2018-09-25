package com.mycompany.osapp1.rws;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import com.mycompany.osapp1.service.SalaryService;
import com.mycompany.osapp1.service.impl.SalaryServiceImpl;

@Path("/rest")
public class SalariesRWS 
{
	private SalaryService service;
	
	@GET
	@Path("/salaries")
	@Produces({MediaType.APPLICATION_JSON})
	public Response findAll()
	{
		service = new SalaryServiceImpl();
		return Response.status(200).entity(service.findAll()).build();
	}

}
