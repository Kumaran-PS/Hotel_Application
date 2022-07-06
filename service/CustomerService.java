package service;
import model.Customer;

import java.util.*;

public class CustomerService {


    final Map<String,Customer> customerList = new HashMap<>();

    // static reference
    private static CustomerService customerService = null;
    private CustomerService() {}
    public static CustomerService getInstance() {
        if (null != customerService) return customerService;
        customerService = new CustomerService();
        return customerService;
    }

    // Defining all methods as per the course rubric

    public void addCustomer(String email, String firstName, String lastName) {
        Customer customer = new Customer(firstName, lastName, email);
        customerList.putIfAbsent(email, customer);
    }

    public Customer getCustomer(String email) {
        return customerList.get(email);
    }

    public Collection<Customer> getAllCustomers(){
        return customerList.values();
    }

}