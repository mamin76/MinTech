package com.eden.enforcementService.repository;

import com.eden.enforcementService.common.model.entity.Whitelist;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.persistence.Entity;
import java.util.Date;

public interface WhiteListedRepository extends JpaRepository<Whitelist, Long>, JpaSpecificationExecutor<Whitelist> {

    Whitelist findByPlateNumberArAndPlateNumberEnAndCountry(String plateNumberAr, String plateNumberEn, String country);

    Whitelist findByPlateNumberEnAndCountry(String plateNumberEn, String countryId);

    @Query(value = "select count(1)>0\n" +
            " FROM \n" +
            "whitelist w join whitelist_details wd on w.id =wd.whitelist_id \n" +
            "where \n" +
            "w.plate_number_en = :plateNumberEn and w.country = :country \n" +
            "and :dateTime between (wd.from_date + wd.time_from) and (wd.to_date + wd.time_to) "
            , nativeQuery = true)
    boolean isExistWhitelistVehicle(@Param("plateNumberEn") String plateNumberEn, @Param("country") String country, @Param("dateTime") Date date);

    @Query("SELECT wl FROM Whitelist wl \n" +
            "join wl.whitelistDetails wld \n" +
            " where \n" +
            "wld.userName like concat('%', :searchValue,'%') \n" +
            "or wl.plateNumberEn like concat('%', :searchValue,'%') \n" +
            "or wl.plateNumberAr like concat('%', :searchValue,'%') \n" +
            "or :searchValue = ''"
    )
    @EntityGraph(attributePaths = {"whitelistDetails"})
    Page<Whitelist> findAllBySearchValue(@Param("searchValue") String searchValue, Pageable pageable);

}
