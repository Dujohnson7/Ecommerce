package rw.ac.auca.ecommmerce.core.role.service;

import rw.ac.auca.ecommmerce.core.role.model.Role;

import java.util.List;
import java.util.UUID;

public interface IRoleService {
    Role registerRole(Role theRole);
    Role updateRole(Role theRole);
    Role deleteRole(Role theRole);
    Role findRoleById(UUID id);
    Role findRoleByIdAndState(UUID id, Boolean state);
    List<Role> findRoleByState(Boolean state);
    List<Role> findAllRoles();
}
