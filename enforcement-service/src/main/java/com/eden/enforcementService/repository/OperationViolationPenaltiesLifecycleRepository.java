package com.eden.enforcementService.repository;

import com.eden.enforcementService.common.model.entity.OperationViolationPenalties;
import com.eden.enforcementService.common.model.entity.OperationViolationPenaltiesLifecycle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OperationViolationPenaltiesLifecycleRepository extends JpaRepository<
        OperationViolationPenaltiesLifecycle,Long> {

    OperationViolationPenaltiesLifecycle getByOperationViolationPenalty(
            OperationViolationPenalties operationViolationPenalties);
}
