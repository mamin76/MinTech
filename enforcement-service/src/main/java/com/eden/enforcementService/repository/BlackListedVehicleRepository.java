package com.eden.enforcementService.repository;

import com.eden.enforcementService.common.model.entity.BlackListedVehicle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface BlackListedVehicleRepository extends JpaRepository<BlackListedVehicle, Long> {

    BlackListedVehicle findByPlateNumberArAndPlateNumberEnAndCountryId(String plateNumberAr, String plateNumberEn, Long countryId);

    BlackListedVehicle findByPlateNumberEnAndCountryName(String plateNumberEn, String countryName);

    @Query(value = "select bl from BlackListedVehicle bl " +
            "where (lower(bl.plateNumberAr) like concat('%', :keyword,'%') or " +
            " lower(bl.plateNumberEn) like concat('%', :keyword,'%') )" +
            "and bl.deleted = false")
    Page<BlackListedVehicle> searchBlackListedVehicle(@Param("keyword") String keyword, Pageable pageable);

    @Query(value = "select bl from BlackListedVehicle bl where bl.deleted = false")
    Page<BlackListedVehicle> searchBlackListedVehicle(Pageable pageable);


}
