package rw.ac.auca.ecommmerce.core.users.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import rw.ac.auca.ecommmerce.core.users.model.Users;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Repository
public interface IUsersRepository extends JpaRepository<Users, UUID> {
    @Query("SELECT usr FROM Users usr WHERE usr.id =: id AND usr.active =: active")
    Optional<Users> findUserByIdWithQuery(@Param("id") UUID id, @Param("active") Boolean active);
    Optional<Users> findUsersByEmailAndActive(String email, Boolean active);
    Optional<Users> findUsersByIdAndActive(UUID id, Boolean active);
    Optional<Users> findUsersById(UUID id);

    Optional<Users> findUsersByUsernameOrEmailAndPassword(String username, String email, String password);
    List<Users> findUsersByActive(Boolean active);
}
