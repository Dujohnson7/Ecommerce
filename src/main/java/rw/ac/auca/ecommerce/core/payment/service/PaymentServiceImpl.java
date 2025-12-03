package rw.ac.auca.ecommerce.core.payment.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.hibernate.ObjectNotFoundException;
import org.springframework.stereotype.Service;
import rw.ac.auca.ecommerce.core.customer.model.Customer;
import rw.ac.auca.ecommerce.core.customer.repository.ICustomerRepository;
import rw.ac.auca.ecommerce.core.orders.model.Orders;
import rw.ac.auca.ecommerce.core.orders.repository.IOrdersRepository;
import rw.ac.auca.ecommerce.core.payment.model.Payment;
import rw.ac.auca.ecommerce.core.payment.repository.IPaymentRepository;
import rw.ac.auca.ecommerce.core.users.model.Users;
import rw.ac.auca.ecommerce.core.users.repository.IUserRepository;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements IPaymentService {

    private final IPaymentRepository paymentRepository;
    private final IUserRepository userRepository;
    private final IOrdersRepository  ordersRepository;

    @Override
    public Payment registerPayment(Payment payment) {
        return paymentRepository.save(payment);
    }

    @Override
    public Payment updatePayment(Payment payment) {
        Payment foundPayment = findPaymentByIdAndState(payment.getId(), Boolean.TRUE);
        if (Objects.nonNull(foundPayment)) {
            foundPayment.setUsers(payment.getUsers());
            foundPayment.setOrders(payment.getOrders());
            foundPayment.setAmount(payment.getAmount());
            foundPayment.setPaymentMethod(payment.getPaymentMethod());
            foundPayment.setPaymentStatus(payment.getPaymentStatus());
            return paymentRepository.save(foundPayment);
        }
        throw new ObjectNotFoundException(Payment.class, "Payment not found");
    }

    @Override
    public Payment deletePayment(Payment payment) {
            Payment foundPayment = findPaymentByIdAndState(payment.getId(), Boolean.TRUE);
            if (Objects.nonNull(foundPayment)) {
                foundPayment.setActive(Boolean.FALSE);
                return paymentRepository.save(foundPayment);
            }
            throw new ObjectNotFoundException(Payment.class, "Payment not found");
    }

    @Override
    public Payment findPaymentByIdAndState(UUID id, Boolean active) {
        Payment thePayment = paymentRepository.findPaymentByIdAndActive(id, Boolean.TRUE)
                .orElseThrow(() -> new EntityNotFoundException("Payment not found"));
        return thePayment;
    }

    @Override
    public List<Payment> findAllPaymentsByState(Boolean state) {
        return paymentRepository.findPaymentByActive(state);
    }

    @Override
    public Payment updatePaymentStatus(Payment payment) {
        Payment foundPayment = findPaymentByIdAndState(payment.getId(), Boolean.TRUE);
        if (Objects.nonNull(foundPayment)) {
            foundPayment.setPaymentStatus(payment.getPaymentStatus());
            foundPayment.setPaymentMethod(payment.getPaymentMethod());
            return paymentRepository.save(foundPayment);
        }
        throw new ObjectNotFoundException(Payment.class, "Payment not found");
    }

    @Override
    public List<Users> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public List<Orders> findAllOrders() {
        return ordersRepository.findAll();
    }
}
