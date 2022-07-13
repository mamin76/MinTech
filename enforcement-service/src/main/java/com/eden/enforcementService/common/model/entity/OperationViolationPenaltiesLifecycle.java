package com.eden.enforcementService.common.model.entity;

import com.eden.enforcementService.util.Constants;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Table(name = "OperationViolationPenaltiesLifecycle")
@Where(clause = "deleted <> true")
@SQLDelete(sql = "UPDATE {h-schema} OperationViolationPenaltiesLifecycle SET deleted = true WHERE id = ?", check = ResultCheckStyle.COUNT)
public class OperationViolationPenaltiesLifecycle extends BaseEntity {

    @Id
    @SequenceGenerator(name = "operation_violation_penalties_lifecycle_seq",
            sequenceName = "operation_violation_penalties_lifecycle_seq",
            allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "operation_violation_penalties_lifecycle_seq")
    private Long id;

    @NotNull
    @OneToOne
    @JoinColumn(name = "operation_violation_penalty_id")
    private OperationViolationPenalties operationViolationPenalty;

    @NotNull(message = Constants.ErrorKeys.EMPTY_START_DATE)
    private LocalDate startDate;

    private LocalDate endDate;

    private String comment;


}
