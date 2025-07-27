package rw.ac.auca.ecommmerce.core.orders.model;

import jakarta.annotation.Generated;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import rw.ac.auca.ecommmerce.core.base.AbstractBaseEntity;
import rw.ac.auca.ecommmerce.core.customer.model.Customer;
import rw.ac.auca.ecommmerce.core.product.model.Product;
import rw.ac.auca.ecommmerce.core.util.order.EOrderState;
import rw.ac.auca.ecommmerce.core.util.order.EPaymentState;

import java.util.List;

@Getter
@Setter
@Entity
public class Orders extends AbstractBaseEntity {
    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;
    @ManyToMany
    @JoinTable(
            name = "order_products",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private List<Product> products;
    @Column(name = "order_quanity", nullable = false)
    private double orderQuantity;
    @Column(name = "order_amount", nullable = false)
    private double orderAmount;
    @Column(name = "order_state", nullable = false)
    @Enumerated(EnumType.STRING)
    private EOrderState orderState;
    @Column(name = "payment_state", nullable = false)
    @Enumerated(EnumType.STRING)
    private EPaymentState paymentState;
}
