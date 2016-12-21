package ro.ing.devschool.repository;

import java.util.List;

import ro.ing.devschool.entity.Customer;

public interface CustomerRepository {
	public List<Customer> findAll();
}
