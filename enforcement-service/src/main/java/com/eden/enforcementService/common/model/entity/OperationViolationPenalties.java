package com.eden.enforcementService.common.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Table(name = "OperationViolationPenalties")
//@Where(clause = "deleted <> true")
//@SQLDelete(sql = "UPDATE {h-schema} OperationViolationPenalties SET deleted = true WHERE id = ?", check = ResultCheckStyle.COUNT)
public class OperationViolationPenalties extends BaseEntity {

    @Id
    @SequenceGenerator(name = "operation_violation_penalties_seq",
            sequenceName = "operation_violation_penalties_seq",
            allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "operation_violation_penalties_seq")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "penalty_id", nullable = false)
    private Penalty penalty;

    @ManyToOne(fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "violation_id", nullable = false)
    private Violation violation;

    @Column(name = "operation_id")
    private Long operationId;

}
