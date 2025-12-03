 package rw.ac.auca.ecommerce.core.address.service;

import org.springframework.stereotype.Service;
import rw.ac.auca.ecommerce.core.address.model.Address;
import rw.ac.auca.ecommerce.core.address.repository.IAddressRepository;
import rw.ac.auca.ecommerce.core.util.address.EDistrictState;
import rw.ac.auca.ecommerce.core.util.address.EProvinceState;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AddressServiceImpl implements IAddressService {

    private final IAddressRepository addressRepository;

    public AddressServiceImpl(IAddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    @Override
    public Address registerAddress(Address address) {
        return addressRepository.save(address);
    }

    @Override
    public Address updateAddress(Address address) {
        return addressRepository.save(address);
    }

    @Override
    public Address deleteAddress(Address address) {
        address.setActive(false);
        return addressRepository.save(address);
    }

    @Override
    public Address findAddressByIdAndState(UUID id, Boolean state) {
        return addressRepository.findAddressByIdAndActive(id, state).orElse(null);
    }

    @Override
    public Address findAddressByDistrictStateAndState(EDistrictState districtState, Boolean state) {
        return addressRepository.findAddressesByDistrictStateAndActive(districtState, state).orElse(null);
    }

    @Override
    public Address findAddressByProvinveStateAndState(EProvinceState provinceState, Boolean state) {
        return addressRepository.findAddressesByProvinceStateAndActive(provinceState, state).orElse(null);
    }


    @Override
    public List<Address> findAddessByState(Boolean state) {
        return addressRepository.findAddressesByActive(state);
    }

    @Override
    public List<String> findDistinctProvinces() {
        return addressRepository.findDistinctProvinceState()
                .stream()
                .map(EProvinceState::name)
                .collect(Collectors.toList());
    }

    @Override
    public List<String> findDistinctDistricts() {
        return addressRepository.findDistinctDistrictState()
                .stream()
                .map(EDistrictState::name)
                .collect(Collectors.toList());
    }

    @Override
    public Address findAddressByProvinceAndDistrict(EProvinceState provinceState, EDistrictState districtState) {
        return addressRepository.findByProvinceStateAndDistrictStateAndActive(provinceState, districtState, true)
                .stream()
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<EDistrictState> findDistrictsByProvince(EProvinceState provinceState) {
        return addressRepository.findDistinctDistrictStateByProvinceState(provinceState);
    }
}