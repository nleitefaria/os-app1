package com.mycompany.osapp1.rws;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.mycompany.osapp1.service.ProductService;
import com.mycompany.osapp1.service.impl.ProductServiceImpl;

@Path("/rest")
public class ProductRWS 
{
	private ProductService service;
	
	@GET
	@Path("/products")
	@Produces({MediaType.APPLICATION_JSON})
	public Response findAll()
	{
		service = new ProductServiceImpl();
		return Response.status(200).entity(service.findAll()).build();
	}
	

}
