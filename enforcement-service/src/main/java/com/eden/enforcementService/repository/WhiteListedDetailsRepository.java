package com.eden.enforcementService.repository;

import com.eden.enforcementService.common.model.entity.WhitelistDetails;
import com.eden.enforcementService.common.request.WhitelistDetailsProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface WhiteListedDetailsRepository extends JpaRepository<WhitelistDetails, Long>, JpaSpecificationExecutor<WhitelistDetails> {

    WhitelistDetails findByFromDateAndToDateAndTimeFromAndTimeTo(LocalDate fromDat, LocalDate toDate, LocalTime timeFrom, LocalTime timeTo);


    @Query(value = "select wd.id from " +
            " public.whitelist_details wd " +
            " INNER JOIN public.whitelist w on w.id = wd.whitelist_id " +
            " where " +
            "  ( wd.from_date >= :fromDate and wd.to_date<= :toDate ) " +
            "    and ( wd.time_from >= :timeFrom and wd.time_to <= :timeTo ) " +
            "    and w.operation_id = :operationId and w.plate_number_en = :plateNumberEn "

            , nativeQuery = true)
    List<WhitelistDetailsProjection> findByDateAndTime(@Param("fromDate") LocalDate fromDate, @Param("toDate") LocalDate toDate,
                                                       @Param("timeFrom") LocalTime timeFrom, @Param("timeTo") LocalTime timeTo,
                                                       @Param("operationId") Long operationId, @Param("plateNumberEn") String plateNumberEn);

}
