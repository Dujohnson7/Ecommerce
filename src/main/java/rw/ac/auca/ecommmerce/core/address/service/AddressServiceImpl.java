package rw.ac.auca.ecommmerce.core.address.service;

import lombok.RequiredArgsConstructor;
import org.hibernate.ObjectNotFoundException;
import org.springframework.stereotype.Service;
import rw.ac.auca.ecommmerce.core.address.model.Address;
import rw.ac.auca.ecommmerce.core.address.repository.IAddressRepository;
import rw.ac.auca.ecommmerce.core.util.address.EDistrictState;
import rw.ac.auca.ecommmerce.core.util.address.EProvinceState;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements IAddressService{

    private final IAddressRepository addressRepository;

    @Override
    public Address registerAddress(Address address) {
        return addressRepository.save(address);
    }

    @Override
    public Address updateAddress(Address address) {
        Address found = findAddressByIdAndState(address.getId() , Boolean.TRUE);
        if (Objects.nonNull(found)) {
            found.setCountry("Rwanda");
            found.setProvinceState(address.getProvinceState());
            found.setSector(address.getSector());
            found.setStreet(address.getStreet());
            return addressRepository.save(found);
        }
        throw new ObjectNotFoundException(Address.class, "Address not found");
    }

    @Override
    public Address deleteAddress(Address address) {
        Address found = findAddressByIdAndState(address.getId() , Boolean.TRUE);
        if (Objects.nonNull(found)) {
            found.setActive(Boolean.FALSE);
            return addressRepository.save(found);
        }
        throw new ObjectNotFoundException(Address.class, "Address not found");
    }

    @Override
    public Address findAddressByIdAndState(UUID id, Boolean state) {
        Address theAddress = addressRepository.findAddressByIdAndActive(id, Boolean.TRUE)
                .orElseThrow(() -> new ObjectNotFoundException(Address.class, "Address not found"));
        return theAddress;
    }

    @Override
    public Address findAddressByProvinveStateAndState(EProvinceState provinceState, Boolean state) {
        Address theAddress = addressRepository.findAddressesByProvinceStateAndActive(provinceState, Boolean.TRUE)
                .orElseThrow(() -> new ObjectNotFoundException(Address.class, "Address not found"));
        return theAddress;
    }

    @Override
    public Address findAddressBySectorAndState(String sector, Boolean state) {
        Address theAddress = addressRepository.findAllBySectorAndActive(sector, Boolean.TRUE)
                .orElseThrow(() -> new ObjectNotFoundException(Address.class, "Address not found"));
        return theAddress;
    }

    @Override
    public Address findAddressByDistrictStateAndState(EDistrictState districtState, Boolean active) {
        Address theAddress = addressRepository.findAddressesByDistrictStateAndActive(districtState, Boolean.TRUE)
                .orElseThrow(() -> new ObjectNotFoundException(Address.class, "Address not found"));
        return theAddress;
    }

    @Override
    public Address findAddressByStreetAndState(String street, Boolean state) {
        Address theAddress = addressRepository.findAllByStreetAndActive(street, Boolean.TRUE)
                .orElseThrow(() -> new ObjectNotFoundException(Address.class, "Address not found"));
        return theAddress;
    }

    @Override
    public List<Address> findAddessByState(Boolean state) {
        return addressRepository.findAddressesByActive(state);
    }
}
