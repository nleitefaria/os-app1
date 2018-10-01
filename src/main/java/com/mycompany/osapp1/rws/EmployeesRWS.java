package com.mycompany.osapp1.rws;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.mycompany.osapp1.domain.EmployeeDTO;
import com.mycompany.osapp1.service.EmployeeService;
import com.mycompany.osapp1.service.impl.EmployeeServiceImpl;

@Path("/rest")
public class EmployeesRWS
{
	private EmployeeService service;
	
	@GET
	@Path("/employees/count")
	@Produces({MediaType.APPLICATION_JSON})
	public Response count()
	{
		service = new EmployeeServiceImpl();
		return Response.status(200).entity(service.count()).build();
	}
	
	@GET
	@Path("/employees")
	@Produces({MediaType.APPLICATION_JSON})
	public Response findAll()
	{
		service = new EmployeeServiceImpl();
		return Response.status(200).entity(service.findAll()).build();
	}
	
	@GET
	@Path("/employees/{id}")
	@Produces({MediaType.APPLICATION_JSON})
	public Response findOffices(@PathParam("id") String id)
	{
		service = new EmployeeServiceImpl();
		EmployeeDTO employeeDTO = service.findEmployees(id);
		if(employeeDTO != null)
		{
			return Response.status(200).entity(employeeDTO).build();			
		}
		else
		{
			return Response.status(204).entity("No entity for id: " + id).build();
		}	
	}

}
