package com.mycompany.osapp1.rws;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.mycompany.osapp1.domain.TitleDTO;

@Path("/rest")
public class TitlesRWS 
{
	@GET
	@Path("/titles")
	@Produces({MediaType.APPLICATION_JSON})
	public Response findAll()
	{
		List<TitleDTO> ret = new ArrayList<TitleDTO>();
		
		TitleDTO t1 = new TitleDTO(1, "t1");
		TitleDTO t2 = new TitleDTO(2, "t2");
		
		ret.add(t1);
		ret.add(t2);
		
		return Response.status(200).entity(ret).build();	
	}

}
