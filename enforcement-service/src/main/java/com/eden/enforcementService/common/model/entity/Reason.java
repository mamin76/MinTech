package com.eden.enforcementService.common.model.entity;

import com.eden.enforcementService.common.model.enums.ReasonType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Table(name = "reason")
@Where(clause = "deleted <> true")
@SQLDelete(sql = "UPDATE {h-schema} reason SET deleted = true WHERE id = ?", check = ResultCheckStyle.COUNT)
public class Reason extends BaseEntity {
    @Id
    @SequenceGenerator(name = "reason_seq",
            sequenceName = "reason_seq",
            allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "reason_seq")
    private Long id;

    private String reasonEN;

    private String reasonAR;

    @Enumerated(EnumType.STRING)
    private ReasonType type;
}
