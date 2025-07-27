package rw.ac.auca.ecommmerce.core.payment.service;

import rw.ac.auca.ecommmerce.core.customer.model.Customer;
import rw.ac.auca.ecommmerce.core.orders.model.Orders;
import rw.ac.auca.ecommmerce.core.payment.model.Payment;

import java.util.List;
import java.util.UUID;

public interface IPaymentService {
    Payment registerPayment(Payment payment);
    Payment updatePayment(Payment payment);
    Payment deletePayment(Payment payment);
    Payment findPaymentByIdAndState(UUID id, Boolean active);
    List<Payment> findAllPaymentsByState(Boolean state);
    Payment updatePaymentStatus(Payment payment);
    List<Customer> findAllCustomers();
    List<Orders> findAllOrders();
}
