package rw.ac.auca.ecommerce.core.role.service;

import lombok.RequiredArgsConstructor;
import org.hibernate.ObjectNotFoundException;
import org.springframework.stereotype.Service;
import rw.ac.auca.ecommerce.core.role.model.Role;
import rw.ac.auca.ecommerce.core.role.repository.IRoleRepository;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements IRoleService {

    private final IRoleRepository roleRepository;

    @Override
    public Role registerRole(Role theRole) {
        return roleRepository.save(theRole);
    }

    @Override
    public Role updateRole(Role theRole) {
        Role found = findRoleByIdAndState(theRole.getId(), Boolean.TRUE);
        if (Objects.nonNull(found)) {
            found.setRoleName(theRole.getRoleName());
            found.setRoleDescription(theRole.getRoleDescription());
            found.setModifiedBy(theRole.getModifiedBy());
            found.setUpdatedAt(theRole.getUpdatedAt());
            return roleRepository.save(found);
        }
        throw new ObjectNotFoundException( Role.class ,"Role not found");
    }

    @Override
    public Role deleteRole(Role theRole) {
        Role found = findRoleByIdAndState(theRole.getId(), Boolean.TRUE);
        if(Objects.nonNull(found)){
            found.setActive(Boolean.FALSE);
            found.setModifiedBy(theRole.getModifiedBy());
            found.setUpdatedAt(theRole.getUpdatedAt());
            return roleRepository.save(found);
        }
        throw new ObjectNotFoundException(Role.class, "Role Object not found");
    }

    @Override
    public Role findRoleById(UUID id) {
        Role theRole = roleRepository.findById(id)
                .orElseThrow( () -> new ObjectNotFoundException(Role.class, "Id Not Found") );
        return theRole;
    }

    @Override
    public Role findRoleByIdAndState(UUID id, Boolean state) {
        Role theRole = roleRepository.findRoleByIdAndActive(id, state)
                .orElseThrow(() -> new ObjectNotFoundException(Role.class , "Role Not Found") );
        return theRole;
    }

    @Override
    public List<Role> findRoleByState(Boolean state) {
        return roleRepository.findAllByActive(state);
    }

    @Override
    public List<Role> findAllRoles() {
        return roleRepository.findAll();
    }

    @Override
    public Role findRoleByName(String roleName) {
        return roleRepository.findByRoleNameAndActive(roleName, Boolean.TRUE)
                .orElseThrow( () -> new ObjectNotFoundException(Role.class, "Role Not Found") );
    }
}
