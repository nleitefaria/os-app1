package com.mycompany.osapp1.rws;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.mycompany.osapp1.domain.SalaryDTO;

@Path("/rest")
public class SalariesRWS 
{
	@GET
	@Path("/salaries")
	@Produces({MediaType.APPLICATION_JSON})
	public Response findAll()
	{
		List<SalaryDTO> ret = new ArrayList<SalaryDTO>();
		
		SalaryDTO s1 = new SalaryDTO(1, 1500);
		SalaryDTO s2 = new SalaryDTO(2, 2000);
		
		ret.add(s1);
		ret.add(s2);
		
		return Response.status(200).entity(ret).build();	
	}

}
