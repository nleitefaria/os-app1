package com.mycompany.osapp1.rws;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import com.mycompany.osapp1.domain.OfficeDTO;
import com.mycompany.osapp1.service.OfficeService;
import com.mycompany.osapp1.service.impl.OfficeServiceimpl;

@Path("/rest")
public class OfficesRWS 
{
	private OfficeService service;
	
	@GET
	@Path("/offices/count")
	@Produces({MediaType.APPLICATION_JSON})
	public Response count()
	{
		service = new OfficeServiceimpl();
		return Response.status(200).entity(service.count()).build();
	}
	
	@GET
	@Path("/offices")
	@Produces({MediaType.APPLICATION_JSON})
	public Response findAll()
	{
		service = new OfficeServiceimpl();
		return Response.status(200).entity(service.findAll()).build();
	}
	
	@GET
	@Path("/offices/{id}")
	@Produces({MediaType.APPLICATION_JSON})
	public Response findOffices(@PathParam("id") String id)
	{
		service = new OfficeServiceimpl();
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

}
