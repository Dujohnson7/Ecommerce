package rw.ac.auca.ecommerce.core.orders.service;

import lombok.RequiredArgsConstructor;
import org.hibernate.ObjectNotFoundException;
import org.springframework.stereotype.Service;
import rw.ac.auca.ecommerce.core.orders.model.Orders;
import rw.ac.auca.ecommerce.core.orders.repository.IOrdersRepository;
import rw.ac.auca.ecommerce.core.product.model.Product;
import rw.ac.auca.ecommerce.core.product.repository.IProductRepository;
import rw.ac.auca.ecommerce.core.users.model.Users;
import rw.ac.auca.ecommerce.core.users.repository.IUserRepository;
import rw.ac.auca.ecommerce.core.util.order.EOrderState;
import rw.ac.auca.ecommerce.core.util.order.EPaymentState;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrdersServiceImpl implements IOrdersService {

    private final IOrdersRepository orderRepository;
    private final IUserRepository userRepository;
    private final IProductRepository productRepository;

    @Override
    public Orders registerOrder(Orders theOrders) {
        theOrders.setOrderNumber(generateOrderNumber());
        return orderRepository.save(theOrders);
    }
    public Integer generateOrderNumber() {
        return (int)(System.currentTimeMillis() % 1000000000);
    }
    @Override
    public Orders updateOrder(Orders theOrders) {
        Orders found = findOrderByIdAndState(theOrders.getId() , Boolean.TRUE);
        if(Objects.nonNull(found)){
            found.setUsers(theOrders.getUsers());
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
    public Orders findOrderByIdAndUserId(UUID id, UUID userId) {
        Orders theOrders = orderRepository .findOrdersByIdAndUserId(id,userId)
                .orElseThrow( () -> new ObjectNotFoundException(Orders.class, "Order Not Found"));
        return theOrders;
    }


    @Override
    public List<Orders> findAllOrdersByState(Boolean active) {
        return orderRepository.findAllByActive(active);
    }

    @Override
    public List<Orders> findAllOrdersByUsers(Users theUsers) {
        return orderRepository.findOrdersByUsers(theUsers);
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
    public List<Users> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public List<Product> findAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public List<Orders> findOrdersByUserIdAndState(UUID userId, Boolean state) {
       // return orderRepository.findByUserIdAndState(userId, Boolean.TRUE);
        return orderRepository.findByUserIdAndActive(userId, state);
    }
}
