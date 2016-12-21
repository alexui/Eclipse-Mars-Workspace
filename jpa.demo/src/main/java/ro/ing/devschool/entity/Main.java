package ro.ing.devschool.entity;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class Main {

	public static void main(String[] args) {
		
		EntityManagerFactory factory =  Persistence.createEntityManagerFactory("persistenceUnit");
		EntityManager entity = factory.createEntityManager();
		
		persistCustomer(entity);	
		findCustomer(entity);
	}

	private static void persistCustomer(EntityManager entity) {
		Customer customer = new Customer();
		//customer.setCustomerId(0);
		customer.setFirstName("Alex");
		customer.setLastName("Budau");
		
		Customer customer2 = new Customer();
		//customer2.setCustomerId(1);
		customer2.setFirstName("Mircea");
		customer2.setLastName("CelBatran");
		
		EntityTransaction transaction = entity.getTransaction();
		transaction.begin();
		entity.persist(customer);
		entity.persist(customer2);
		transaction.commit();
	}
	
	private static void findCustomer(EntityManager entity) {
		Customer customer;
		
		EntityTransaction transaction = entity.getTransaction();
		transaction.begin();
		Integer id = new Integer(1);
		customer = entity.find(Customer.class, id);
		transaction.commit();
		System.out.println(customer);
	}
}
