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
import com.mycompany.osapp1.domain.OfficeDTO;
import com.mycompany.osapp1.service.OfficeService;
import com.mycompany.osapp1.service.impl.OfficeServiceImpl;

@Path("/rest")
public class OfficesRWS 
{
	private OfficeService service;
	
	@GET
	@Path("/offices/count")
	@Produces({MediaType.APPLICATION_JSON})
	public Response count()
	{
		service = new OfficeServiceImpl();
		return Response.status(200).entity(service.count()).build();
	}
	
	@GET
	@Path("/offices")
	@Produces({MediaType.APPLICATION_JSON})
	public Response findAll()
	{
		service = new OfficeServiceImpl();
		return Response.status(200).entity(service.findAll()).build();
	}
	
	@GET
	@Path("/office/{id}")
	@Produces({MediaType.APPLICATION_JSON})
	public Response findOffices(@PathParam("id") String id)
	{
		service = new OfficeServiceImpl();
		OfficeDTO officeDTO = service.findOffices(id);
		if(officeDTO != null)
		{
			return Response.status(200).entity(officeDTO).build();			
		}
		else
		{
			return Response.status(204).entity("No entity for id: " + id).build();
		}	
	}
	
	@POST
	@Path("/office")
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	public Response create(OfficeDTO officeDTO) 
	{
		service = new OfficeServiceImpl();
		try 
		{
			service.create(officeDTO);
			return Response.status(201).entity(officeDTO).build();	
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
	@Path("/office/{id}")
    @Produces("application/json")
    public Response delete(@PathParam("id") String id)
	{
		service = new OfficeServiceImpl();
        Integer ret = service.destroy(id);

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