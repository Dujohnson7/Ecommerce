package rw.ac.auca.ecommerce.core.role.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import rw.ac.auca.ecommerce.core.base.AbstractBaseEntity;

@Getter
@Setter
@Entity
public class Role extends AbstractBaseEntity {
    @Column(name = "role_name", unique = true, nullable = false)
    private String roleName;
    @Column(name = "role_description", nullable = true)
    private String roleDescription;
}
