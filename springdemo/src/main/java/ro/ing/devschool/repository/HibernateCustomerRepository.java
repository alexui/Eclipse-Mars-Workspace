package ro.ing.devschool.repository;

import java.util.ArrayList;
import java.util.List;

import ro.ing.devschool.entity.Customer;

public class HibernateCustomerRepository implements CustomerRepository {

	public List<Customer> findAll() {
		List<Customer> list = new ArrayList<Customer>();
		list.add(new Customer("Alex", "Budau"));
		list.add(new Customer("Mitica", "Mardare"));
		list.add(new Customer("Mitica", "Catalean"));
		return list;
	}

}
