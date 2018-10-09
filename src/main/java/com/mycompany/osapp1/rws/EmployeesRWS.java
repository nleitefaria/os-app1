package com.mycompany.osapp1.rws;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.mycompany.osapp1.dao.impl.exceptions.PreexistingEntityException;
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
	@Path("/employee/{id}")
	@Produces({MediaType.APPLICATION_JSON})
	public Response findOne(@PathParam("id") String id)
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
	
	@POST
	@Path("/employee")
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	public Response create(EmployeeDTO employeeDTO) 
	{
		service = new EmployeeServiceImpl();
		try 
		{
			service.create(employeeDTO);
			return Response.status(201).entity(employeeDTO).build();	
		} 
		catch (PreexistingEntityException e) 
		{
			return Response.status(500).entity("An error occured").build();	
		} 
		catch (Exception e) 
		{
			return Response.status(500).entity("An error occured").build();	
		}
	}
	
	@DELETE
	@Path("/employee/{id}")
    @Produces("application/json")
    public Response delete(@PathParam("id") String id)
	{
		service = new EmployeeServiceImpl();
        Integer ret = service.destroy(Integer.parseInt(id));

        if (ret > 0) 
        {
            return Response.ok().status(Response.Status.NO_CONTENT).build();
        } 
        else 
        {
            return Response.notModified().build();
        }
    }

}
