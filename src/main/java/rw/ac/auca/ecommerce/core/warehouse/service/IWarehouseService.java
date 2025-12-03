package rw.ac.auca.ecommerce.core.warehouse.service;

import rw.ac.auca.ecommerce.core.warehouse.model.Warehouse;

import java.util.List;
import java.util.UUID;

public interface IWarehouseService {
    Warehouse register(Warehouse theWarehouse);
    Warehouse update(Warehouse theWarehouse);
    Warehouse delete(Warehouse theWarehouse);
    Warehouse findByIdAndActive(UUID id, Boolean active);
    List<Warehouse> findAllWarehousesByState(Boolean state);
}
