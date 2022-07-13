package com.eden.enforcementService.common.model.entity;

import com.eden.enforcementService.common.model.enums.PenaltyMethod;
import com.eden.enforcementService.common.model.enums.PenaltyType;
import com.eden.enforcementService.util.Constants;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.*;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@ToString
@Table(name = "penalty")
@Where(clause = "deleted <> true")
@SQLDelete(sql = "UPDATE {h-schema} penalty SET deleted = true WHERE id = ?", check = ResultCheckStyle.COUNT)
public class Penalty extends BaseEntity {

    @Id
    @SequenceGenerator(name = "penalty_seq",
            sequenceName = "penalty_seq",
            allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "penalty_seq")
    private Long id;

    @NotNull(message = Constants.ErrorKeys.PENALTY_NAME_REQUIRED)
    @Column(name = "en_name", unique = true)
    private String enName;

    @NotNull(message = Constants.ErrorKeys.PENALTY_NAME_REQUIRED)
    @Column(name = "ar_name", unique = true)
    private String arName;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private PenaltyType type;

    @NotNull(message = Constants.ErrorKeys.PENALTY_VALUE_REQUIRED)
    @Column(name = "fees")
    private double fees;

    @Email(message = Constants.ErrorKeys.PENALTY_TO_EMAIL_NOT_VALID, regexp = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])")
    @Column(name = "email")
    private String email;

    @Column(name = "subject")
    private String subject;

    @Lob
    @Column(name = "body")
    @Type(type = "org.hibernate.type.TextType")
    private String body;

    @Column(name = "method")
    @NotNull(message = Constants.ErrorKeys.PENALTY_METHOD_REQUIRED)
    @Enumerated(EnumType.STRING)
    private PenaltyMethod method;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "penalty")
    @Fetch(FetchMode.SUBSELECT)
    List<OperationViolationPenalties> operationViolationPenalties;

    @Column(name = "en_description", nullable = true)
    private String enDescription;

    @Column(name = "ar_description", nullable = true)
    private String arDescription;

    public Penalty(Long id) {
        this.id = id;
    }

}
