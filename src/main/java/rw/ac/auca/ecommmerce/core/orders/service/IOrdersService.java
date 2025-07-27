package rw.ac.auca.ecommmerce.core.orders.service;

import rw.ac.auca.ecommmerce.core.customer.model.Customer;
import rw.ac.auca.ecommmerce.core.orders.model.Orders;
import rw.ac.auca.ecommmerce.core.product.model.Product;
import rw.ac.auca.ecommmerce.core.util.order.EOrderState;
import rw.ac.auca.ecommmerce.core.util.order.EPaymentState;

import java.util.List;
import java.util.UUID;

public interface IOrdersService {
    Orders registerOrder(Orders theOrders);
    Orders updateOrder(Orders theOrders);
    Orders deleteOrder(Orders theOrders);

    Orders findOrderByIdAndState(UUID id, Boolean active);
    List<Orders> findAllOrdersByState(Boolean active);
    List<Orders> findAllOrdersByCustomer(Customer theCustomer);
    List<Orders> findAllOrdersByOrderStateAndState(EOrderState orderState, Boolean active);
    List<Orders> findAllOrdersByPaymentStateAndState(EPaymentState paymentState, Boolean active);


    List<Customer> findAllCustomers();
    List<Product> findAllProducts();

}
