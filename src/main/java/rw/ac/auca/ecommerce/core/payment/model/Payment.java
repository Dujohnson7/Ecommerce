package rw.ac.auca.ecommerce.core.payment.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import rw.ac.auca.ecommerce.core.base.AbstractBaseEntity;
import rw.ac.auca.ecommerce.core.customer.model.Customer;
import rw.ac.auca.ecommerce.core.orders.model.Orders;
import rw.ac.auca.ecommerce.core.users.model.Users;
import rw.ac.auca.ecommerce.core.util.order.EPaymentMethod;
import rw.ac.auca.ecommerce.core.util.order.EPaymentState;

@Getter
@Setter
@Entity
public class Payment extends AbstractBaseEntity {
    @ManyToOne
    @JoinColumn(name = "user_id")
    Users users;
    @OneToOne
    @JoinColumn(name = "orders_id")
    Orders orders;
    @Column(name = "amount", nullable = false)
    Double amount;
    @Column(name = "paiment_method", nullable = false)
    @Enumerated(EnumType.STRING)
    EPaymentMethod paymentMethod;
    @Column(name = "payment_status", nullable = false)
    @Enumerated(EnumType.STRING)
    EPaymentState paymentStatus;

}
