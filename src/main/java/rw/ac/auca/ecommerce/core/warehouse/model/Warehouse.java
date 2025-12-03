package rw.ac.auca.ecommerce.core.warehouse.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import rw.ac.auca.ecommerce.core.base.AbstractBaseEntity;

@Getter
@Setter
@Entity
public class Warehouse  extends AbstractBaseEntity {
    @Column(name = "warehouse_name", nullable = false, unique = true)
    private String warehouseName;
    @Column(name = "warehouse_location",  nullable = false)
    private String warehouseLocation;

}
