package ro.ing.devschool.entity;

import java.applet.AppletContext;

import ro.ing.devschool.services.CustomerServices;
import ro.ing.devschool.services.HibernateCustomerService;

public class Main {

	public static void main(String[] args) {
		
		CustomerServices services = new HibernateCustomerService();
		for (Customer c : services.findMitica())
			System.out.println(c.firstName);
	}
}
