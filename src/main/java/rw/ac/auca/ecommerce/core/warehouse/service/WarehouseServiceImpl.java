package rw.ac.auca.ecommerce.core.warehouse.service;

import lombok.RequiredArgsConstructor;
import org.hibernate.ObjectNotFoundException;
import org.springframework.stereotype.Service;
import rw.ac.auca.ecommerce.core.warehouse.model.Warehouse;
import rw.ac.auca.ecommerce.core.warehouse.repository.IWarehouseRepository;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class WarehouseServiceImpl implements IWarehouseService {
    private final IWarehouseRepository warehouseRepository;

    @Override
    public Warehouse register(Warehouse theWarehouse) {
        return warehouseRepository.save(theWarehouse);
    }

    @Override
    public Warehouse update(Warehouse theWarehouse) {
        Warehouse foundWarehouse = findByIdAndActive(theWarehouse.getId(), Boolean.TRUE);
        if (Objects.nonNull(foundWarehouse)) {
            foundWarehouse.setWarehouseName(theWarehouse.getWarehouseName());
            foundWarehouse.setWarehouseLocation(theWarehouse.getWarehouseLocation());
            foundWarehouse.setActive(theWarehouse.isActive());
            return warehouseRepository.save(foundWarehouse);
        }
        throw new ObjectNotFoundException(Warehouse.class, "Warehouse not found");
    }

    @Override
    public Warehouse delete(Warehouse theWarehouse) {
        Warehouse foundWarehouse = findByIdAndActive(theWarehouse.getId(), Boolean.TRUE);
        if (Objects.nonNull(foundWarehouse)) {
            foundWarehouse.setActive(Boolean.FALSE);
            return warehouseRepository.save(foundWarehouse);
        }
        throw new ObjectNotFoundException(Warehouse.class, "Warehouse not found");
    }

    @Override
    public Warehouse findByIdAndActive(UUID id, Boolean active) {
       Warehouse theWarehouse = warehouseRepository.findWarehouseByIdAndActive(id,Boolean.TRUE)
               .orElseThrow( () -> new ObjectNotFoundException(Warehouse.class, "WAREHOUSE NOT FOUND"));
       return theWarehouse;
    }

    @Override
    public List<Warehouse> findAllWarehousesByState(Boolean state) {
        return warehouseRepository.findAllWarehousesByActive(state);
    }
}
