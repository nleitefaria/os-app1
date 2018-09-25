package com.mycompany.osapp1.rws;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.mycompany.osapp1.service.DepartmentService;
import com.mycompany.osapp1.service.impl.DepartmentServiceImpl;

@Path("/rest")
public class DepartmentsRWS 
{
	private DepartmentService service;
	
	@GET
	@Path("/employees")
	@Produces({MediaType.APPLICATION_JSON})
	public Response findAll()
	{
		service = new DepartmentServiceImpl();
		return Response.status(200).entity(service.findAll()).build();
	}
	

}
