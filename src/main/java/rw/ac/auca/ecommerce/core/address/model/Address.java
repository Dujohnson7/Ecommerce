package rw.ac.auca.ecommerce.core.address.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;
import rw.ac.auca.ecommerce.core.base.AbstractBaseEntity;
import rw.ac.auca.ecommerce.core.util.address.EDistrictState;
import rw.ac.auca.ecommerce.core.util.address.EProvinceState;
@Getter
@Setter
@Entity
public class Address extends AbstractBaseEntity {
    @Column(name = "country", nullable = false)
    private String country = "Rwanda";

    @Column(name = "provinceState", nullable = false)
    @Enumerated(EnumType.STRING)
    EProvinceState provinceState;

    @Column(name = "districtState", nullable = false)
    @Enumerated(EnumType.STRING)
    EDistrictState districtState;


}
