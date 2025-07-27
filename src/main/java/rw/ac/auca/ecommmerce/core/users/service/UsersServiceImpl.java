package rw.ac.auca.ecommmerce.core.users.service;

import lombok.RequiredArgsConstructor;
import org.hibernate.ObjectNotFoundException;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import rw.ac.auca.ecommmerce.core.role.model.Role;
import rw.ac.auca.ecommmerce.core.role.repository.IRoleRepository;
import rw.ac.auca.ecommmerce.core.users.model.Users;
import rw.ac.auca.ecommmerce.core.users.repository.IUsersRepository;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UsersServiceImpl implements IUsersService {

    private final IUsersRepository usersRepository;
    private final IRoleRepository  roleRepository;

    @Override
    public Users registerUsers(Users users) {
        return usersRepository.save(users);
    }

    @Override
    public Users loginUsers(Users users) {
        Users foundUser = usersRepository.findUsersByUsernameOrEmailAndPassword(users.getUsername(), users.getEmail(),  users.getPassword())
                .orElseThrow(() -> new ObjectNotFoundException(Users.class, "User not found"));

        if (!foundUser.isActive()) {
            throw new RuntimeException("User account is inactive");
        }

        if (!foundUser.getPassword().equals(users.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        return foundUser;
    }

    @Override
    public Users updateUsers(Users users) {
        Users found = findUserById(users.getId());
        if (Objects.nonNull(found)) {
            found.setFirstName(users.getFirstName());
            found.setLastName(users.getLastName());
            found.setEmail(users.getEmail());
            found.setPassword(users.getPassword());
            found.setUsername(users.getUsername());
            return usersRepository.save(found);
        }
        throw new ObjectNotFoundException(Users.class,"USERS NOT FOUND");
    }

    @Override
    public Users deleteUsers(Users users) {
        Users found = findUserById(users.getId());
        if (Objects.nonNull(found)) {
            found.setActive(Boolean.FALSE);
            return usersRepository.save(found);
        }
        throw new ObjectNotFoundException(Users.class,"USERS NOT FOUND");
    }

    @Override
    public Users findUserById(UUID id) {
        Users theUsers = usersRepository.findUsersByIdAndActive(id, Boolean.TRUE)
                .orElseThrow(() -> new ObjectNotFoundException(Users.class, "Users not found!"));
        return theUsers;
    }

    @Override
    public List<Users> findAllUsersByState(Boolean state) {
        return usersRepository.findUsersByActive(state);
    }

    @Override
    public Users findUserByIdAndState(UUID id, Boolean state) {
        Users theUsers = usersRepository.findUsersByIdAndActive(id, state)
                .orElseThrow(() -> new ObjectNotFoundException(Users.class, "Users not found!"));
        return theUsers;
    }

    @Override
    public List<Role> findAllRoles() {
        return roleRepository.findAll();
    }
}
