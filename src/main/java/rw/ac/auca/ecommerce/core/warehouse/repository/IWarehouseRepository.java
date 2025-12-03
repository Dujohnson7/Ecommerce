package rw.ac.auca.ecommerce.core.warehouse.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rw.ac.auca.ecommerce.core.warehouse.model.Warehouse;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Repository
public interface IWarehouseRepository extends JpaRepository<Warehouse, UUID> {
    Optional<Warehouse> findWarehouseByIdAndActive(UUID id, Boolean active);
    Optional<Warehouse> findWarehouseById(UUID id);
    List<Warehouse> findAllWarehousesByActive(Boolean state);
}
