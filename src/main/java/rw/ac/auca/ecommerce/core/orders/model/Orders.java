package rw.ac.auca.ecommerce.core.orders.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import rw.ac.auca.ecommerce.core.base.AbstractBaseEntity;
import rw.ac.auca.ecommerce.core.product.model.Product;
import rw.ac.auca.ecommerce.core.users.model.Users;
import rw.ac.auca.ecommerce.core.util.order.EOrderState;
import rw.ac.auca.ecommerce.core.util.order.EPaymentState;

import java.util.List;

@Getter
@Setter
@Entity
public class Orders extends AbstractBaseEntity {

    @Column(name = "order_number", nullable = true, unique = true, insertable = true, updatable = true)
    private Integer orderNumber;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Users users;
    
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
