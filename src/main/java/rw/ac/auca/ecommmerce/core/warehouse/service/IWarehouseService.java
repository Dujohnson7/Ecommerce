package rw.ac.auca.ecommmerce.core.warehouse.service;

import rw.ac.auca.ecommmerce.core.warehouse.model.Warehouse;

import java.util.List;

public interface IWarehouseService {
    Warehouse register(Warehouse theWarehouse);
    Warehouse update(Warehouse theWarehouse);
    Warehouse delete(Warehouse theWarehouse);
    List<Warehouse> findAllWarehousesByState(Boolean state);
}
