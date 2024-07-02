package com.enoch;

import com.enoch.dto.CompanyDTO;
import com.enoch.dto.ShipDTO;
import com.enoch.dto.UserDTO;
import com.enoch.master.dto.PortDTO;
import com.enoch.master.model.Port;
import com.enoch.model.AuditTrail;

public class ApplicationContext {

	private static ThreadLocal<ApplicationContext> context = new ThreadLocal<ApplicationContext>();
	
	private String loggedInUser;
	private UserDTO user;
	private ShipDTO ship;
	private CompanyDTO company;
	
	private ApplicationContext(String loggedInUser) {
		this.loggedInUser = loggedInUser;
	}
	
	public static ApplicationContext getContext() {
		assert context.get() != null : "Context not initilized";
		return context.get();
	}
	
	public static void initContext(String loggedInUser) {
	//	assert context.get() == null : "Context already initilized";
		context.set(new ApplicationContext(loggedInUser));
	}
	
	public String getLoggedInUser() {
		return loggedInUser;
	}
	public UserDTO getUser() {
		return user;
	}
	public void setUser(UserDTO user) {
		this.user = user;
	}
	public void setShip(ShipDTO ship) {
		this.ship = ship;
	}
	public void setCompany(CompanyDTO company) {
		this.company = company;
	}

	public AuditTrail getAuditTrail() {
		return  new AuditTrail(getLoggedInUser());
		
	}

	public CompanyDTO getCompany() {
		return this.company;
		
	}
	
	//navanee added
	public ShipDTO getShip() {
		return this.ship;
	}
	
}
