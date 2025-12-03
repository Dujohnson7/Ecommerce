package rw.ac.auca.ecommerce.core.payment.service;

import rw.ac.auca.ecommerce.core.customer.model.Customer;
import rw.ac.auca.ecommerce.core.orders.model.Orders;
import rw.ac.auca.ecommerce.core.payment.model.Payment;
import rw.ac.auca.ecommerce.core.users.model.Users;

import java.util.List;
import java.util.UUID;

public interface IPaymentService {
    Payment registerPayment(Payment payment);
    Payment updatePayment(Payment payment);
    Payment deletePayment(Payment payment);
    Payment findPaymentByIdAndState(UUID id, Boolean active);
    List<Payment> findAllPaymentsByState(Boolean state);
    Payment updatePaymentStatus(Payment payment);
    List<Users> findAllUsers();
    List<Orders> findAllOrders();
}
