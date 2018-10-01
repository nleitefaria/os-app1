package com.mycompany.osapp1.rws;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.mycompany.osapp1.service.EmployeeService;
import com.mycompany.osapp1.service.impl.EmployeeServiceImpl;

@Path("/rest")
public class EmployeesRWS
{
	private EmployeeService service;
	
	@GET
	@Path("/employees")
	@Produces({MediaType.APPLICATION_JSON})
	public Response findAll()
	{
		service = new EmployeeServiceImpl();
		return Response.status(200).entity(service.findAll()).build();
	}

}
