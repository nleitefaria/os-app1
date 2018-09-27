package com.mycompany.osapp1.rws;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.mycompany.osapp1.service.ProductLineService;
import com.mycompany.osapp1.service.impl.ProductLineServiceImpl;

@Path("/rest")
public class ProductLineRWS 
{	
	private ProductLineService service;
	
	@GET
	@Path("/productline")
	@Produces({MediaType.APPLICATION_JSON})
	public Response findAll()
	{
		service = new ProductLineServiceImpl();
		return Response.status(200).entity(service.findAll()).build();
	}
	

}
