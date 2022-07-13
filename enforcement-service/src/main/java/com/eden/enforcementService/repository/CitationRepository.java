package com.eden.enforcementService.repository;

import com.eden.enforcementService.common.dto.CitationsProjection;
import com.eden.enforcementService.common.dto.CitationsSearchProjection;
import com.eden.enforcementService.common.model.entity.Citation;
import com.eden.enforcementService.common.model.enums.CitationStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface CitationRepository extends JpaRepository<Citation, Long>, JpaSpecificationExecutor<Citation> {

    List<Citation> findByIdIn(List<Long> citationsIds);

    List<Citation> findByPlateNumberEnAndStatus(String plateNumberEn, CitationStatus status);

    @Query(value = "select c1.created_date\tcitationDate,\n" +
            "sub.citationId\tcitationId,\n" +
            "(case when c1.status = 'Settled'  then c1.modified_date  ELSE null end )\tpaymentDateTime,\n" +
            "c1.plate_number_ar\tplateNumberAr,\n" +
            "c1.plate_number_en\tplateNumberEn,\n" +
            "c1.comment\tremarks,\n" +
            "c1.status\tstatus,\n" +
            "c1.street_name\tstreetName,\n" +
            "sub.fees\ttotalFees,\n" +
            "sub.violationId\tviolationId,\n" +
            "v1.ar_name\tviolationNameAr,\n" +
            "v1.en_name\tviolationNameEn\n" +
            "from \n" +
            "(SELECT c.id citationId,v.id violationId,sum(case when p.fees is null or cp.status='Voided' then 0  ELSE p.fees end ) fees\n" +
            "FROM citation c join citation_penalties cp on c.id=cp.citation_id \n" +
            "     join operation_violation_penalties ovp  on ovp.id= cp.operation_violation_penalty_id \n" +
            "     join violation v  on ovp.violation_id = v.id \n" +
            "     left join penalty p on p.id =ovp.penalty_id and p.method ='MONETARY'\n" +
            "     where c.plate_number_ar  = :plateNumber or c.plate_number_en = :plateNumber\n" +
            "    group by c.id,v.id) sub\n" +
            "  join  citation c1  \n" +
            "    on c1.id=sub.citationId\n" +
            "    join  violation v1  \n" +
            "    on v1.id=sub.violationId ",
            countQuery = "SELECT count(*) FROM citation c where c.plate_number_ar  = :plateNumber or c.plate_number_en = :plateNumber",
            nativeQuery = true)
    Page<CitationsProjection> getCitationByPlateNumber(@Param("plateNumber") String lastname, Pageable pageable);

    @Query(value = "select c1.created_date\tcitationDate,\n" +
            "sub.citationId\tcitationId,\n" +
            "(case when c1.status = 'Settled'  then c1.modified_date  ELSE null end )\tpaymentDateTime,\n" +
            "c1.plate_number_ar\tplateNumberAr,\n" +
            "c1.plate_number_en\tplateNumberEn,\n" +
            "c1.comment\tremarks,\n" +
            "c1.shift_work_force_id\tshiftWorkForceId,\n" +
            "c1.status\tstatus,\n" +
            "c1.street_name\tstreetName,\n" +
            "sub.fees\ttotalFees,\n" +
            "sub.violationId\tviolationId,\n" +
            "v1.ar_name\tviolationNameAr,\n" +
            "v1.en_name\tviolationNameEn\n" +
            "from \n" +
            "(SELECT c.id citationId,v.id violationId,sum(case when p.fees is null then 0  ELSE p.fees end ) fees\n" +
            "FROM citation c join citation_penalties cp on c.id=cp.citation_id \n" +
            "     join operation_violation_penalties ovp  on ovp.id= cp.operation_violation_penalty_id \n" +
            "     join violation v  on ovp.violation_id = v.id \n" +
            "     left join penalty p on p.id =ovp.penalty_id and p.method ='MONETARY'\n" +
            "     where (c.operation_id = :operationId or -1 = :operationId) and c.status in (:citationStatuses)  and c.created_date >=:fromDate  and c.created_date <=:toDate\n" +
            "    group by c.id,v.id) sub\n" +
            "  join  citation c1  \n" +
            "    on c1.id=sub.citationId\n" +
            "    join  violation v1  \n" +
            "    on v1.id=sub.violationId ",
            countQuery = "SELECT count(*) FROM citation c where (c.operation_id = :operationId or -1 = :operationId) and c.status in (:citationStatuses) and c.created_date >=:fromDate and c.created_date <=:toDate",
            nativeQuery = true)
    Page<CitationsSearchProjection> searchCitation(@Param("operationId") Long operationId,
                                                   @Param("citationStatuses") List<String> citationStatuses,
                                                   @Param("fromDate") LocalDate fromDate,
                                                   @Param("toDate") LocalDate toDate,
                                                   Pageable pageable);
}
