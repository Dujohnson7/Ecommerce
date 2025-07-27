package rw.ac.auca.ecommmerce.core.payment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import rw.ac.auca.ecommmerce.core.customer.model.Customer;
import rw.ac.auca.ecommmerce.core.payment.model.Payment;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Repository
public interface IPaymentRepository extends JpaRepository<Payment, UUID> {
    @Query("SELECT pa FROM Payment pa WHERE pa.id =: id AND pa.active")
    Optional<Payment> findPaymentByIdWithNamedQuery(@Param("id") UUID id,  @Param("active") Boolean active);
    List<Payment> findPaymentByActive(Boolean active);
    Optional<Payment> findPaymentByIdAndActive(UUID id , Boolean active);
    Optional<Payment> findPaymentByCustomerAndActive(Customer thecustomer, Boolean active);
}
