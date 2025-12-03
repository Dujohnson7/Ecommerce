package rw.ac.auca.ecommerce.core.users.service;

import rw.ac.auca.ecommerce.core.orders.model.Orders;
import rw.ac.auca.ecommerce.core.role.model.Role;
import rw.ac.auca.ecommerce.core.users.model.Users;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IUsersService {
    Users registerUsers(Users users);
    Users updateUsers(Users users);
    Users deleteUsers(Users users);
    Users findUserByIdAndState(UUID id, Boolean state);
    List<Users> findAllUsersByState(Boolean state);
    List<Role> findAllRoles();
    List<Orders> findOrdersById(UUID id);
    List<Users> findUsersByRoleAndActive(Role role, Boolean active);
}
