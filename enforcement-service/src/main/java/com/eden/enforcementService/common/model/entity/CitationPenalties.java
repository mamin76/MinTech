package com.eden.enforcementService.common.model.entity;

import com.eden.enforcementService.common.model.enums.PenalityStatus;
import com.eden.enforcementService.common.model.enums.SettlementChannel;
import com.eden.enforcementService.util.Constants;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.*;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@ToString
@Table(name = "Citation_penalties")
@Where(clause = "deleted <> true")
@SQLDelete(sql = "UPDATE {h-schema} CitationPenalties SET deleted = true WHERE id = ?", check = ResultCheckStyle.COUNT)
public class CitationPenalties extends BaseEntity {

    @Id
    @SequenceGenerator(name = "citation_penalties_seq",
            sequenceName = "citation_penalties_seq",
            allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "citation_penalties_seq")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "citation_id", nullable = false)
    private Citation citation;

    @ManyToOne(fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "operation_violation_penalty_id", nullable = false)
    private OperationViolationPenalties operationViolationPenalty;

    @NotNull(message = Constants.ErrorKeys.EMPTY_CITATION_STATUS)
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private PenalityStatus status;

    @Column(name = "action_count")
    private Long actionCount;

    @NotNull(message = Constants.ErrorKeys.EMPTY_SHIFT_WORK_FORCE_ID)
    @Column(name = "shift_work_force_id")
    private Long shiftWorkforceId;

    @Column(name = "initiation_date")
    @CreationTimestamp
    private LocalDateTime initiationDate = LocalDateTime.now();

    @Column(name = "acknowledgment_date")
    private LocalDateTime acknowledgmentDate;

    @Column(name = "settlement_voiding")
    private LocalDateTime settlementVoiding;

    @Column(name = "settlement_channel")
    @Enumerated(EnumType.STRING)
    private SettlementChannel settlementChannel;

    @Column(name = "comment")
    private String comment;

    @OneToOne
    @JoinColumn(name = "reason_id")
    private Reason reason;


}
