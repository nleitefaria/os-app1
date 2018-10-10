package com.mycompany.osapp1.rws;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import com.mycompany.osapp1.domain.OrderDTO;
import com.mycompany.osapp1.service.OrderService;
import com.mycompany.osapp1.service.impl.OrderServiceImpl;

@Path("/rest")
public class OrdersRWS 
{
	private OrderService service;
	
	@GET
	@Path("/orders/count")
	@Produces({MediaType.APPLICATION_JSON})
	public Response count()
	{
		service = new OrderServiceImpl();
		return Response.status(200).entity(service.count()).build();
	}
	
	@GET
	@Path("/orders")
	@Produces({MediaType.APPLICATION_JSON})
	public Response findAll()
	{
		service = new OrderServiceImpl();
		return Response.status(200).entity(service.findAll()).build();
	}
	
	@GET
	@Path("/order/{id}")
	@Produces({MediaType.APPLICATION_JSON})
	public Response findOffices(@PathParam("id") String id)
	{
		service = new OrderServiceImpl();
		OrderDTO orderDTO = service.findOrders(id);
		if(orderDTO != null)
		{
			return Response.status(200).entity(orderDTO).build();			
		}
		else
		{
			return Response.status(204).entity("No entity for id: " + id).build();
		}	
	}
	

}
