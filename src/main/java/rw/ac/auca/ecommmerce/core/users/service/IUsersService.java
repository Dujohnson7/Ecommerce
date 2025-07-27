package rw.ac.auca.ecommmerce.core.users.service;

import rw.ac.auca.ecommmerce.core.role.model.Role;
import rw.ac.auca.ecommmerce.core.users.model.Users;

import java.util.List;
import java.util.UUID;

public interface IUsersService  {
    Users registerUsers(Users users);
    Users loginUsers(Users users);
    Users updateUsers(Users users);
    Users deleteUsers(Users users);
    Users findUserById(UUID id);
    List<Users> findAllUsersByState(Boolean state);
    Users findUserByIdAndState(UUID id, Boolean state);
    List<Role> findAllRoles();
}
