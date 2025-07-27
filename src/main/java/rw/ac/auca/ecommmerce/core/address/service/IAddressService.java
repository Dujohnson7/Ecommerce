package rw.ac.auca.ecommmerce.core.address.service;

import rw.ac.auca.ecommmerce.core.address.model.Address;
import rw.ac.auca.ecommmerce.core.util.address.EDistrictState;
import rw.ac.auca.ecommmerce.core.util.address.EProvinceState;

import java.util.List;
import java.util.UUID;

public interface IAddressService {
    Address registerAddress(Address address);
    Address updateAddress(Address address);
    Address deleteAddress(Address address);
    Address findAddressByIdAndState(UUID id , Boolean state);
    Address findAddressByDistrictStateAndState(EDistrictState districtState, Boolean state);
    Address findAddressByProvinveStateAndState(EProvinceState provinceState, Boolean state);
    Address findAddressBySectorAndState(String sector, Boolean state);
    Address findAddressByStreetAndState(String street, Boolean state);
    List<Address> findAddessByState(Boolean state);

}
