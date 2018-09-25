package com.mycompany.osapp1.rws;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.mycompany.osapp1.domain.Product;

@Path("/hello")
public class HelloRWS 
{
	@GET
	@Path("/hi")
	@Produces({MediaType.APPLICATION_JSON})
	public Response count()
	{	
		Product product = new Product();
		product.setName("iPad 3");
		product.setQty(999);
		return Response.status(200).entity(product).build();	
	}
}
