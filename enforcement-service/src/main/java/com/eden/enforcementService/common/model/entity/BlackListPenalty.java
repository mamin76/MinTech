package com.eden.enforcementService.common.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.*;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Table(name = "BlackListPenalty")
@Where(clause = "deleted <> true")
@SQLDelete(sql = "UPDATE {h-schema} BlackListPenalty SET deleted = true WHERE id = ?", check = ResultCheckStyle.COUNT)
public class BlackListPenalty extends BaseEntity {

    @Id
    @SequenceGenerator(name = "blacklist_penalty_seq",
            sequenceName = "blacklist_penalty_seq",
            allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "blacklist_penalty_seq")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "penalty_id", nullable = false)
    private Penalty penalty;

    @ManyToOne(fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "blacklisted_id", nullable = false)
    private BlackListedVehicle blackListedVehicle;

}
