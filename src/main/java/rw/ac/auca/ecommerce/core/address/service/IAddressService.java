package rw.ac.auca.ecommerce.core.address.service;

import rw.ac.auca.ecommerce.core.address.model.Address;
import rw.ac.auca.ecommerce.core.util.address.EDistrictState;
import rw.ac.auca.ecommerce.core.util.address.EProvinceState;

import java.util.List;
import java.util.UUID;

public interface IAddressService {
    Address registerAddress(Address address);
    Address updateAddress(Address address);
    Address deleteAddress(Address address);
    Address findAddressByIdAndState(UUID id , Boolean state);
    Address findAddressByDistrictStateAndState(EDistrictState districtState, Boolean state);
    Address findAddressByProvinveStateAndState(EProvinceState provinceState, Boolean state);
    //Address findAddressByStreetAndState(String street, Boolean state);
    List<Address> findAddessByState(Boolean state);
   // Address findAddressByProvinceDistrictSector(EProvinceState provinceState, EDistrictState districtState, String sector);
    List<String> findDistinctProvinces();
    List<String> findDistinctDistricts();
    Address findAddressByProvinceAndDistrict(EProvinceState provinceState, EDistrictState districtState);
    List<EDistrictState> findDistrictsByProvince(EProvinceState provinceState);

}
