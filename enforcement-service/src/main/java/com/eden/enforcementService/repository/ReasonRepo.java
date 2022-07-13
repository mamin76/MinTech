package com.eden.enforcementService.repository;

import com.eden.enforcementService.common.dto.ReasonDto;
import com.eden.enforcementService.common.model.entity.Reason;
import com.eden.enforcementService.common.model.enums.ReasonType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface ReasonRepo extends JpaRepository<Reason, Long>, JpaSpecificationExecutor<Reason> {

    List<Reason> findByType(ReasonType type);

    Reason findByIdAndType(Long reasonId, ReasonType aVoid);
}
