package rw.ac.auca.ecommerce.core.users.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rw.ac.auca.ecommerce.core.address.model.Address;
import rw.ac.auca.ecommerce.core.orders.model.Orders;
import rw.ac.auca.ecommerce.core.role.model.Role;
import rw.ac.auca.ecommerce.core.users.model.Users;
import rw.ac.auca.ecommerce.core.util.address.EDistrictState;
import rw.ac.auca.ecommerce.core.util.address.EProvinceState;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IUserRepository extends JpaRepository<Users, UUID> {
    Optional<Users> findByEmail(String email);
    Optional<Users> findByUsername(String username);
    Optional<Users> findUsersByIdAndActive(UUID id, Boolean active);
    List<Users> findUsersByActive(Boolean active);
    Optional<Users> findByUsernameAndActive(String username, Boolean active);
    List<Orders>  findOrdersById(UUID id);
    List<Users> findByAddressDistrictState(EDistrictState districtState);

    List<Users> findUsersByRoleAndActive(Role role, Boolean active);

}
