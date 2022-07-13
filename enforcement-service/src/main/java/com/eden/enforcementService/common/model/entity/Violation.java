package com.eden.enforcementService.common.model.entity;

import com.eden.enforcementService.util.Constants;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@ToString
@Table(name = "violation")
@Where(clause = "deleted <> true")
@SQLDelete(sql = "UPDATE {h-schema} violation SET deleted = true WHERE id = ?", check = ResultCheckStyle.COUNT)
public class Violation extends BaseEntity {

    @Id
    @SequenceGenerator(name = "violation_seq",
            sequenceName = "violation_seq",
            allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "violation_seq")
    private Long id;

    @NotNull(message = Constants.ErrorKeys.VIOLATION_NAME_REQUIRED)
    @Column(name = "en_name", unique = true)
    private String enName;

    @NotNull(message = Constants.ErrorKeys.VIOLATION_NAME_REQUIRED)
    @Column(name = "ar_name", unique = true)
    private String arName;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "violation")
    @Fetch(FetchMode.SUBSELECT)
    @JsonManagedReference
    List<OperationViolationPenalties> operationViolationPenalties;

    @Column(name = "en_description",nullable = true)
    private String enDescription;

    @Column(name = "ar_description",nullable = true)
    private String arDescription;

}
