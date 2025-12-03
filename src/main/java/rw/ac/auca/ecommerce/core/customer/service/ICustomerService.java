package rw.ac.auca.ecommerce.core.customer.service;

import rw.ac.auca.ecommerce.core.customer.model.Customer;

import java.util.List;
import java.util.UUID;


public interface ICustomerService {
    Customer registerCustomer(Customer theCustomer);
    Customer updateCustomer(Customer theCustomer);
    Customer deleteCustomer(Customer theCustomer);
    Customer findCustomerByIdAndState(UUID id , Boolean state);
    Customer findCustomerByEmailAndState(String email , Boolean state);
    List<Customer> findCustomersByState(Boolean state);
}
