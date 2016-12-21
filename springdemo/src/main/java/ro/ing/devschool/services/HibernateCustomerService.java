package ro.ing.devschool.services;

import java.util.ArrayList;
import java.util.List;

import ro.ing.devschool.entity.Customer;
import ro.ing.devschool.repository.CustomerRepository;

public class HibernateCustomerService implements CustomerServices{

	CustomerRepository cr; // setez din XML relatia has-a
	
	public List<Customer> findMitica() {
		List<Customer> customerList, customerRep;
		//CustomerRepository cr = new HibernateCustomerRepository(); // as vrea ca sa nu mai depinda direct 
																	//de implementarea de Hibernate
		customerRep = cr.findAll();
		customerList = new ArrayList<Customer>();
		for (Customer c : customerRep)
			if (c.firstName.equals("Mitica"))
				customerList.add(c);
		return customerList;
	}

	public CustomerRepository getCr() {
		return cr;
	}

	public void setCr(CustomerRepository cr) {
		this.cr = cr;
	}
	
	

}
