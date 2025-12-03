package rw.ac.auca.ecommerce.core.productCategory.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import rw.ac.auca.ecommerce.core.base.AbstractBaseEntity;

@Getter
@Setter
@Entity
public class ProductCategory extends AbstractBaseEntity {
    @Column(name = "category_name",  unique = true, nullable = false)
    private String categoryName;
    @Column(name = "category_description", columnDefinition = "TEXT", nullable = true)
    private String categoryDescription;
    @Column(name = "category_icon", nullable = false)
    private String categoryIcon;
}
