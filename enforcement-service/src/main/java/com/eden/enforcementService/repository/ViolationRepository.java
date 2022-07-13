package com.eden.enforcementService.repository;
import com.eden.enforcementService.common.model.entity.Violation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ViolationRepository extends JpaRepository<Violation,Long> {

    @Query("select count(v.id) from Violation v where v.id in (:ids) ")
    Long countViolationByIds(List<Long> ids);

    @Query(value = "select v from Violation v " +
            "where lower(v.enDescription) like concat('%', :keyword,'%') " +
            " or lower(v.arDescription) like concat('%', :keyword,'%') "+
            " or lower(v.enName) like concat('%', :keyword,'%') "+
            " or lower(v.arName) like concat('%', :keyword,'%') "+
            " or concat(v.id,'') like concat('%', :keyword,'%')")
    Page<Violation> searchViolations(@Param("keyword") String keyword, Pageable pageable);

    @Query(value = "select v from Violation v ")
    Page<Violation> listViolationsPagination(Pageable pageable);

    boolean existsByEnNameOrArName(String enName,String arName);

    Boolean existsByEnName(String name);

    Boolean existsByArName(String name);
}
