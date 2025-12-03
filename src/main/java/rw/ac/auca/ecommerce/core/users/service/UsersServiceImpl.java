package rw.ac.auca.ecommerce.core.users.service;

import lombok.RequiredArgsConstructor;
import org.hibernate.ObjectNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import rw.ac.auca.ecommerce.core.orders.model.Orders;
import rw.ac.auca.ecommerce.core.orders.repository.IOrdersRepository;
import rw.ac.auca.ecommerce.core.role.model.Role;
import rw.ac.auca.ecommerce.core.role.repository.IRoleRepository;
import rw.ac.auca.ecommerce.core.users.model.Users;
import rw.ac.auca.ecommerce.core.users.repository.IUserRepository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UsersServiceImpl implements IUsersService{

    private final IUserRepository usersRepository;
    private final IRoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final IOrdersRepository ordersRepository;


    @Override
    public Users registerUsers(Users users) {
        if (users.getRole() == null) {
            Role defaultRole = roleRepository.findByRoleName("CUSTOMER")
                    .orElseThrow(() -> new RuntimeException("Default role not found"));
            users.setRole(defaultRole);
        }
        users.setPassword(passwordEncoder.encode(users.getPassword()));
        return usersRepository.save(users);
    }

    @Override
    public Users updateUsers(Users users) {
        Users found = findUserByIdAndState(users.getId(), Boolean.TRUE);
        if (Objects.nonNull(found)) {
            found.setFirstName(users.getFirstName());
            found.setLastName(users.getLastName());
            found.setEmail(users.getEmail());
            found.setUsername(users.getUsername());
            found.setRole(users.getRole());
            found.setPhoneNumber(users.getPhoneNumber());
            found.setAddress(users.getAddress());
            found.setSector(users.getSector());
            found.setStreet(users.getStreet());
            found.setActive(users.isActive());
            if (users.getPassword() != null && !users.getPassword().isEmpty()) {
                found.setPassword(passwordEncoder.encode(users.getPassword()));
            }
            return usersRepository.save(found);
        }
        throw new ObjectNotFoundException(Users.class, "USERS NOT FOUND");
    }

    @Override
    public Users deleteUsers(Users users) {
        Users found = findUserByIdAndState(users.getId(), Boolean.TRUE);
        if (Objects.nonNull(found)) {
            found.setActive(Boolean.FALSE);
            return usersRepository.save(found);
        }
        throw new ObjectNotFoundException(Users.class, "USERS NOT FOUND");
    }

    @Override
    public Users findUserByIdAndState(UUID id, Boolean state) {
        Users theUsers = usersRepository.findUsersByIdAndActive(id, Boolean.TRUE)
                .orElseThrow( () -> new ObjectNotFoundException(Users.class,"User not found"));
        return theUsers;
    }

    @Override
    public List<Users> findAllUsersByState(Boolean state) {
        return usersRepository.findUsersByActive(state);
    }

    @Override
    public List<Role> findAllRoles() {
        return roleRepository.findAll();
    }

    @Override
    public List<Orders> findOrdersById(UUID id) {
        return ordersRepository.findAllOrdersByUsersId(id);
    }

    @Override
    public List<Users> findUsersByRoleAndActive(Role role, Boolean active) {
        return usersRepository.findUsersByRoleAndActive(role, Boolean.TRUE);
    }
}
