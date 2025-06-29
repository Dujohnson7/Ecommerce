package rw.ac.auca.ecommmerce.core.customer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import rw.ac.auca.ecommmerce.core.customer.model.Customer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Repository
public interface ICustomerRepository extends JpaRepository<Customer, UUID> {
    @Query("SELECT c FROM Customer c WHERE c.id =: id and c.active =: active")
    Optional<Customer> findCustomerByIdWithNamedQuery(@Param("id") UUID id , @Param("active") Boolean active);
    Optional<Customer> findByIdAndActive(UUID id , Boolean active);
    Optional<Customer> findByEmailAndActive(String email , Boolean active);
    List<Customer> findAllByActive(Boolean active);
}
