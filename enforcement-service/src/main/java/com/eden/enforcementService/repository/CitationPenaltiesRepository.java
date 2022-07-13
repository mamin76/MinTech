package com.eden.enforcementService.repository;

import com.eden.enforcementService.common.dto.CitationPenaltiesProjection;
import com.eden.enforcementService.common.dto.CitationRevenueProjection;
import com.eden.enforcementService.common.model.entity.CitationPenalties;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CitationPenaltiesRepository extends JpaRepository<CitationPenalties, Long> {

    List<CitationPenalties> findByCitation_Id(Long citationId);


    @Query(value = "select cp.action_count as penaltyCount, cp.status as status, p.fees as fees " +
            "from Citation_penalties cp " +
            "INNER JOIN operation_violation_penalties ovp on ovp.id = cp.operation_violation_penalty_id " +
            "INNER JOIN penalty p  on	 p.id = ovp.penalty_id " +
            "where " +
            "to_char(cp.created_date, 'yyyy-MM')= :byMonth " +
            "and ovp.operation_id = :operationId " +
            "and cp.status in ('Settled', 'Opened') "
            , nativeQuery = true)
    List<CitationPenaltiesProjection> findByMonthAndOperationId(@Param("byMonth") String byMonth, @Param("operationId") Long operationId);


    @Query(value = "select cp.action_count as penaltyCount, cp.status as status, p.fees as fees " +
            "from Citation_penalties cp " +
            "INNER JOIN operation_violation_penalties ovp on ovp.id = cp.operation_violation_penalty_id " +
            "INNER JOIN penalty p  on	 p.id = ovp.penalty_id " +
            "where " +
            "to_char(cp.created_date, 'yyyy-MM-dd')= :day " +
            "and ovp.operation_id = :operationId " +
            "and cp.status in ('Settled', 'Opened') "
            , nativeQuery = true)
    List<CitationPenaltiesProjection> findByOperationId(@Param("day") String today, @Param("operationId") Long operationId);


    @Query(value = "select cp.action_count as penaltyCount, cp.status as status, p.fees as fees " +
            "from Citation_penalties cp " +
            "INNER JOIN operation_violation_penalties ovp on ovp.id = cp.operation_violation_penalty_id " +
            "INNER JOIN penalty p  on	p.id = ovp.penalty_id " +
            "where " +
            "to_char(cp.created_date, 'yyyy-MM') in (:months) " +
            "and cp.status in ('Settled', 'Opened' ,'Voided')  "
            , nativeQuery = true)
    List<CitationPenaltiesProjection> findByMonths(@Param("months") List<String> months);


    @Query(value = "select  sum((cp.action_count * p.fees ))as totalFees,  EXTRACT(month from cp.modified_date) as month \n" +
            "            from Citation_penalties cp \n" +
            "            INNER JOIN operation_violation_penalties ovp on ovp.id = cp.operation_violation_penalty_id \n" +
            "            INNER JOIN penalty p  on p.id = ovp.penalty_id \n" +
            "            where\n" +
            "             cp.status in ('Settled')\n" +
            "             and to_char(cp.modified_date, 'YYYY') = :year \n" +
            "             group by  EXTRACT(month from cp.modified_date)  "
            , nativeQuery = true)
    List<CitationRevenueProjection> getRevenueStatisticsByYear(@Param("year") String year);
}