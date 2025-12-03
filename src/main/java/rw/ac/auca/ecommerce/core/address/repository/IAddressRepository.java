 package rw.ac.auca.ecommerce.core.address.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import rw.ac.auca.ecommerce.core.address.model.Address;
import rw.ac.auca.ecommerce.core.util.address.EDistrictState;
import rw.ac.auca.ecommerce.core.util.address.EProvinceState;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface IAddressRepository extends JpaRepository<Address, UUID> {
    @Query("SELECT ad FROM Address ad WHERE ad.id = :id AND ad.active = :active")
    Optional<Address> findAddressByIdWithNamedQuery(@Param("id") UUID id, @Param("active") Boolean active);

    Optional<Address> findAddressByIdAndActive(UUID id, Boolean active);

    Optional<Address> findAddressesByProvinceStateAndActive(EProvinceState provinceState, Boolean active);

    Optional<Address> findAddressesByDistrictStateAndActive(EDistrictState districtState, Boolean active);

    List<Address> findAddressesByActive(Boolean active);

    @Query("SELECT DISTINCT ad.provinceState FROM Address ad WHERE ad.active = true")
    List<EProvinceState> findDistinctProvinceState();

    @Query("SELECT DISTINCT ad.districtState FROM Address ad WHERE ad.active = true")
    List<EDistrictState> findDistinctDistrictState();

    List<Address> findByProvinceStateAndDistrictStateAndActive(EProvinceState provinceState, EDistrictState districtState, Boolean active);

    @Query("SELECT DISTINCT ad.districtState FROM Address ad WHERE ad.provinceState = :provinceState AND ad.active = true")
    List<EDistrictState> findDistinctDistrictStateByProvinceState(@Param("provinceState") EProvinceState provinceState);
}