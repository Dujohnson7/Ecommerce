package rw.ac.auca.ecommerce.core.users.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
import rw.ac.auca.ecommerce.core.address.model.Address;
import rw.ac.auca.ecommerce.core.base.AbstractBaseEntity;
import rw.ac.auca.ecommerce.core.role.model.Role;

@Getter
@Setter
@Entity
public class Users extends AbstractBaseEntity {

    @Column(name = "fist_name" ,nullable = false)
    private String firstName;

    @Column(name = "last_name" ,nullable = false)
    private String lastName;

    @Column(name = "email" , nullable = false , unique = true)
    private String email;

    @Column(name = "phone_number" , nullable = false , unique = true)
    private String phoneNumber;

    @Column(name = "username" , nullable = false)
    private String username;

    @Column(name = "password" , nullable = false)
    String password;

    @ManyToOne
    @JoinColumn(name = "user_role")
    Role role;

    @ManyToOne
    @JoinColumn(name = "user_address",  nullable = true)
    Address  address;

    @Column(name = "sector" ,nullable = true)
    private String sector;

    @Column(name = "street" ,nullable = true)
    private String street;

}
