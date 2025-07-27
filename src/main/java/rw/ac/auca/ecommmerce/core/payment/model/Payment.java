package rw.ac.auca.ecommmerce.core.payment.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import rw.ac.auca.ecommmerce.core.base.AbstractBaseEntity;
import rw.ac.auca.ecommmerce.core.customer.model.Customer;
import rw.ac.auca.ecommmerce.core.orders.model.Orders;
import rw.ac.auca.ecommmerce.core.util.order.EPaymentMethod;
import rw.ac.auca.ecommmerce.core.util.order.EPaymentState;

@Getter
@Setter
@Entity
public class Payment extends AbstractBaseEntity {
    @ManyToOne
    @JoinColumn(name = "customer_id")
    Customer customer;
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
