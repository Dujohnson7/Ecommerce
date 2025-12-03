package rw.ac.auca.ecommerce.core.product.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import rw.ac.auca.ecommerce.core.base.AbstractBaseEntity;
import rw.ac.auca.ecommerce.core.productCategory.model.ProductCategory;
import rw.ac.auca.ecommerce.core.util.product.EStockState;

import java.time.LocalDate;


@Getter @Setter
@Entity
public class Product extends AbstractBaseEntity {
    @Column(name = "product_profile", nullable = true)
    private String productProfile;

    @Column(name = "product_name" , nullable = false)
    private String productName;

    @Column(name = "product_description" , nullable = true, columnDefinition = "TEXT")
    private String productDescription;

    @Column(name = "product_price" , nullable = false)
    private Double productPrice;

    @Column(name = "manufactured_date" , nullable = false)
    private LocalDate manufacturedDate;

    @Column(name = "expiration_date" , nullable = true)
    private LocalDate expirationDate;

    @Column(name = "stock_state" , nullable = false)
    @Enumerated(EnumType.STRING)
    private EStockState stockState;

    @ManyToOne
    @JoinColumn(name = "product_category_id")
    private ProductCategory productCategory;


}
