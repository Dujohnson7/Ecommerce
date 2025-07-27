package rw.ac.auca.ecommmerce.core.orders.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import rw.ac.auca.ecommmerce.core.customer.model.Customer;
import rw.ac.auca.ecommmerce.core.orders.model.Orders;
import rw.ac.auca.ecommmerce.core.util.order.EOrderState;
import rw.ac.auca.ecommmerce.core.util.order.EPaymentState;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Repository
public interface IOrdersRepository extends JpaRepository<Orders, UUID> {
    @Query("SELECT ord FROM Orders ord WHERE ord.id =: id and ord.active =: active")
    Optional<Orders> findOrdersByIdWithNamedQuery(@Param("id") UUID id , @Param("active") Boolean active);

    @Query("SELECT o FROM Orders o LEFT JOIN FETCH o.products WHERE o.active = :active")
    List<Orders> findAllWithProductsByActive(@Param("active") Boolean active);
   // @Query("SELECT o FROM Order o WHERE o.orderState = :orderState AND o.active = :active")
    //List<Order> findOrdersByStateAndActive(@Param("orderState") EOrderState orderState,  @Param("active") Boolean active);
    Optional<Orders> findById(UUID uuid);
    Optional<Orders> findOrderByIdAndActive(UUID orderId, Boolean active);
    Optional<Orders> findOrdersByCustomerIdAndActive(UUID customerId, Boolean active);
    List<Orders> findOrdersByCustomer(Customer theCustomer);
    List<Orders> findAllByActive(Boolean active);

    List<Orders> findAllByPaymentStateAndActive(EPaymentState paymentState, Boolean active);

    List<Orders> findAllByOrderStateAndActive(EOrderState orderState, Boolean active);

}
