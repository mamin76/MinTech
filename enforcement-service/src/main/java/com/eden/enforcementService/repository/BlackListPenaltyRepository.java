package com.eden.enforcementService.repository;

import com.eden.enforcementService.common.dto.CitationRevenueProjection;
import com.eden.enforcementService.common.model.entity.BlackListPenalty;
import com.eden.enforcementService.common.model.entity.BlackListedVehicle;
import com.eden.enforcementService.common.model.entity.Penalty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;


public interface BlackListPenaltyRepository extends JpaRepository<BlackListPenalty, Long> {

    boolean existsByBlackListedVehicleAndPenalty(BlackListedVehicle blackListedVehicle, Penalty penalty);

    @Modifying
    @Query("update BlackListPenalty blp set blp.deleted = true, blp.modifiedBy = ?1 ,blp.modifiedDate = ?2 " +
            "  where  blp.blackListedVehicle.id = ?3")
    void deletedAndSetLogInfo(String userName, LocalDateTime modifiedDate, Long blackListedVehicleId);

    List<BlackListPenalty> getBlackListPenaltyByBlackListedVehicle(BlackListedVehicle blackListedVehicle);

}
