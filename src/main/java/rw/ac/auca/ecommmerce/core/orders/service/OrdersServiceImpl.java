package rw.ac.auca.ecommmerce.core.orders.service;

import lombok.RequiredArgsConstructor;
import org.hibernate.ObjectNotFoundException;
import org.springframework.stereotype.Service;
import rw.ac.auca.ecommmerce.core.customer.model.Customer;
import rw.ac.auca.ecommmerce.core.customer.repository.ICustomerRepository;
import rw.ac.auca.ecommmerce.core.orders.model.Orders;
import rw.ac.auca.ecommmerce.core.orders.repository.IOrdersRepository;
import rw.ac.auca.ecommmerce.core.product.model.Product;
import rw.ac.auca.ecommmerce.core.product.repository.IProductRepository;
import rw.ac.auca.ecommmerce.core.util.order.EOrderState;
import rw.ac.auca.ecommmerce.core.util.order.EPaymentState;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrdersServiceImpl implements IOrdersService {

    private final IOrdersRepository orderRepository;
    private final ICustomerRepository customerRepository;
    private final IProductRepository productRepository;

    @Override
    public Orders registerOrder(Orders theOrders) {
        return orderRepository.save(theOrders);
    }

    @Override
    public Orders updateOrder(Orders theOrders) {
        Orders found = findOrderByIdAndState(theOrders.getId() , Boolean.TRUE);
        if(Objects.nonNull(found)){
            found.setCustomer(theOrders.getCustomer());
            found.setProducts(theOrders.getProducts());
            found.setOrderQuantity(theOrders.getOrderQuantity());
            found.setOrderAmount(theOrders.getOrderAmount());
            found.setOrderState(theOrders.getOrderState());
            found.setPaymentState(theOrders.getPaymentState());
            return orderRepository.save(found);
        }
        throw new ObjectNotFoundException(Orders.class, "Order Not Found");
    }

    @Override
    public Orders deleteOrder(Orders theOrders) {
        Orders found = findOrderByIdAndState(theOrders.getId(), Boolean.TRUE);
        if(Objects.nonNull(found)){
            found.setActive(Boolean.FALSE);
            return orderRepository.save(found);
        }
        throw new ObjectNotFoundException(Orders.class , "Order Object not found");
    }

    @Override
    public Orders findOrderByIdAndState(UUID id, Boolean active) {
        Orders theOrders = orderRepository.findOrderByIdAndActive(id, active)
                .orElseThrow( () -> new ObjectNotFoundException(Orders.class, "Order Not Found"));
        return theOrders;
    }


    @Override
    public List<Orders> findAllOrdersByState(Boolean active) {
        return orderRepository.findAllByActive(active);
    }

    @Override
    public List<Orders> findAllOrdersByCustomer(Customer theCustomer) {
        return orderRepository.findOrdersByCustomer(theCustomer);
    }

    @Override
    public List<Orders> findAllOrdersByOrderStateAndState(EOrderState orderState, Boolean active) {
        return orderRepository.findAllByOrderStateAndActive(orderState,active);
    }

    @Override
    public List<Orders> findAllOrdersByPaymentStateAndState(EPaymentState paymentState, Boolean active) {
        return orderRepository.findAllByPaymentStateAndActive(paymentState, active);
    }

    @Override
    public List<Customer> findAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public List<Product> findAllProducts() {
        return productRepository.findAll();
    }
}
