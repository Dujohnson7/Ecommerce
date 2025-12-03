package rw.ac.auca.ecommerce.core.customer.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import rw.ac.auca.ecommerce.core.base.AbstractBaseEntity;

@Getter
@Setter
@Entity
public class Customer extends AbstractBaseEntity {

    @Column(name = "fist_name" ,nullable = false)
    private String firstName;

    @Column(name = "last_name" ,nullable = false)
    private String lastName;

    @Column(name = "email" , nullable = false , unique = true)
    private String email;

    @Column(name = "phone_number" , nullable = false , unique = true)
    private String phoneNumber;

}
