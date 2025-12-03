package rw.ac.auca.ecommerce.core.orders.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import rw.ac.auca.ecommerce.core.orders.model.Orders;
import rw.ac.auca.ecommerce.core.users.model.Users;
import rw.ac.auca.ecommerce.core.util.order.EOrderState;
import rw.ac.auca.ecommerce.core.util.order.EPaymentState;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface IOrdersRepository extends JpaRepository<Orders, UUID> {

    @Query("SELECT ord FROM Orders ord WHERE ord.id = :id AND ord.active = :active")
    Optional<Orders> findOrdersByIdWithNamedQuery(@Param("id") UUID id, @Param("active") Boolean active);

    @Query("SELECT o FROM Orders o LEFT JOIN FETCH o.products WHERE o.active = :active")
    List<Orders> findAllWithProductsByActive(@Param("active") Boolean active);

    @Query("SELECT o FROM Orders o WHERE o.users.id = :userId AND o.active = :active")
    List<Orders> findByUserIdAndActive(@Param("userId") UUID userId, @Param("active") Boolean active);

    @Query("SELECT o FROM Orders o WHERE o.users.id = :userId AND o.active = :state")
    List<Orders> findByUserIdAndState(@Param("userId") UUID userId, @Param("state") Boolean state);

    @Query("SELECT o FROM Orders o WHERE o.id = :id AND o.users.id = :userId")
    Optional<Orders> findOrdersByIdAndUserId(@Param("id") UUID id, @Param("userId") UUID userId);

    Optional<Orders> findById(UUID uuid);

    Optional<Orders> findOrderByIdAndActive(UUID orderId, Boolean active);

    List<Orders> findOrdersByUsers(Users theUsers);

    List<Orders> findAllOrdersByUsersId(UUID id);

    List<Orders> findAllByActive(Boolean active);

    List<Orders> findAllByPaymentStateAndActive(EPaymentState paymentState, Boolean active);

    List<Orders> findAllByOrderStateAndActive(EOrderState orderState, Boolean active);
}