package com.eden.enforcementService.repository;

import com.eden.enforcementService.common.model.entity.OperationViolationPenalties;
import com.eden.enforcementService.common.model.entity.Penalty;
import com.eden.enforcementService.common.model.entity.Violation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OperationViolationPenaltiesRepository extends JpaRepository<OperationViolationPenalties, Long> {

    Boolean existsByOperationIdAndViolation(Long operationId, Violation violation);

    Boolean existsByOperationIdAndViolationAndDeletedFalse(Long operationId,
                                                           Violation violation);

    Boolean existsByOperationIdAndViolationAndPenalty(Long operationId, Violation violation,
                                                      Penalty penalty);

    Boolean existsByOperationIdAndViolationAndPenaltyAndDeletedFalse(Long operationId,
                                                                     Violation violation,
                                                                     Penalty penalty);

    OperationViolationPenalties getByOperationIdAndViolationAndPenalty(Long operationId, Violation violation,
                                                                       Penalty penalty);

    OperationViolationPenalties getByOperationIdAndViolationAndPenaltyAndDeletedFalse(Long operationId, Violation violation,
                                                                       Penalty penalty);

//    Page<OperationViolationPenalties> findByOperationIdAndViolation_EnNameContainingIgnoreCase(
//            Long operationId, String violationName, Pageable pageable);
//
//    Page<OperationViolationPenalties> findByOperationIdAndViolation_ArNameContainingIgnoreCase(
//            Long operationId, String violationName, Pageable pageable);


    @Query("SELECT ovp FROM OperationViolationPenalties ovp join ovp.violation v where v.id = :violationId and ovp.operationId = :operationId ")
    @EntityGraph(attributePaths = {"penalty"})
    List<OperationViolationPenalties> getOperationViolationPenaltiesByOperationAndViolation(@Param("operationId") Long operationId, @Param("violationId") Long violationId);

    @Query("SELECT ovp FROM OperationViolationPenalties ovp join ovp.violation v where v.id = :violationId and ovp.operationId = :operationId and ovp.deleted = :deleted")
    @EntityGraph(attributePaths = {"penalty"})
    List<OperationViolationPenalties> getOperationViolationPenaltiesByOperationAndViolationAndDeleted(@Param("operationId") Long operationId, @Param("violationId") Long violationId, @Param("deleted") Boolean deleted);


    @Query("SELECT DISTINCT ovp.violation FROM OperationViolationPenalties ovp " +
            "left join ovp.violation v where (v.enName like concat('%', :violationName,'%') or v.arName like concat('%', :violationName,'%') or :violationName = '') "+
            "and ovp.operationId = :operationId")
    Page<Violation> getViolationOperation(@Param("operationId") Long operationId,
                                                            @Param("violationName") String violationName,
                                                            Pageable pageable);

    @Query("SELECT DISTINCT ovp.violation FROM OperationViolationPenalties ovp " +
            "left join ovp.violation v where (v.enName like concat('%', :violationName,'%') or v.arName like concat('%', :violationName,'%') or :violationName = '') "+
            "and ovp.operationId = :operationId and ovp.deleted = false")
    Page<Violation> getViolationOperationNotDeleted(@Param("operationId") Long operationId,
                                          @Param("violationName") String violationName,
                                          Pageable pageable);


}
