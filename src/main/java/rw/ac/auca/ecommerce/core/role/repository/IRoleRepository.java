package rw.ac.auca.ecommerce.core.role.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import rw.ac.auca.ecommerce.core.role.model.Role;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IRoleRepository extends JpaRepository<Role, UUID> {
    @Query("SELECT r FROM Role r WHERE r.id =: id AND r.active =: active")
    Optional<Role> findRoleByIdWithNamedQuery(@Param("id") UUID id,  @Param("active") Boolean active);
    Optional<Role> findById(UUID id);
    //Optional<Role> findByIdActive(UUID id, Boolean active);
    Optional<Role> findRoleByIdAndActive(UUID id, Boolean active);
    List<Role> findAllByActive(Boolean active);
    List<Role> findAll();
    Optional<Role> findByRoleNameAndActive(String roleName, Boolean active);
    Optional<Role> findByRoleName(String roleName);


}
