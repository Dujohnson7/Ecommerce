package rw.ac.auca.ecommerce.core.orders.service;

import rw.ac.auca.ecommerce.core.customer.model.Customer;
import rw.ac.auca.ecommerce.core.orders.model.Orders;
import rw.ac.auca.ecommerce.core.product.model.Product;
import rw.ac.auca.ecommerce.core.users.model.Users;
import rw.ac.auca.ecommerce.core.util.order.EOrderState;
import rw.ac.auca.ecommerce.core.util.order.EPaymentState;

import java.util.List;
import java.util.UUID;

public interface IOrdersService {
    Orders registerOrder(Orders theOrders);
    Orders updateOrder(Orders theOrders);
    Orders deleteOrder(Orders theOrders);
    Orders findOrderByIdAndState(UUID id, Boolean active);
    Orders findOrderByIdAndUserId(UUID id, UUID userId);

    List<Orders> findAllOrdersByState(Boolean active);
    List<Orders> findAllOrdersByUsers(Users theUsers);
    List<Orders> findAllOrdersByOrderStateAndState(EOrderState orderState, Boolean active);
    List<Orders> findAllOrdersByPaymentStateAndState(EPaymentState paymentState, Boolean active);

    List<Users> findAllUsers();
    List<Product> findAllProducts();
    List<Orders> findOrdersByUserIdAndState(UUID userId, Boolean state);

}
