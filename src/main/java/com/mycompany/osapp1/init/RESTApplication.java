package com.mycompany.osapp1.init;

import java.util.HashSet;
import java.util.Set;

import com.mycompany.osapp1.rws.CustomersRWS;
import com.mycompany.osapp1.rws.EmployeesRWS;
import com.mycompany.osapp1.rws.HelloRWS;
import com.mycompany.osapp1.rws.OfficesRWS;
import com.mycompany.osapp1.rws.OrderDetailsRWS;
import com.mycompany.osapp1.rws.OrdersRWS;
import com.mycompany.osapp1.rws.PaymentsRWS;

public class RESTApplication extends javax.ws.rs.core.Application 
{
	private Set<Object> singletons = new HashSet<Object>();

	public RESTApplication() 
	{		
		singletons.add(new HelloRWS());
		singletons.add(new CustomersRWS());
		singletons.add(new EmployeesRWS());
		singletons.add(new OfficesRWS());
		singletons.add(new OrderDetailsRWS());
		singletons.add(new OrdersRWS());
		singletons.add(new PaymentsRWS());
		
		
	}

	@Override
	public Set<Object> getSingletons() {
		return singletons;
	}
}