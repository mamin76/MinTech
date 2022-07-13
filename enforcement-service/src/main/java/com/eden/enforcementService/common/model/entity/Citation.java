package com.eden.enforcementService.common.model.entity;

import com.eden.enforcementService.common.model.enums.CitationStatus;
import com.eden.enforcementService.util.Constants;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.*;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@ToString
@Table(name = "citation")
@Where(clause = "deleted <> true")
@SQLDelete(sql = "UPDATE {h-schema} citation SET deleted = true WHERE id = ?", check = ResultCheckStyle.COUNT)
public class Citation extends BaseEntity {

    @Id
    @SequenceGenerator(name = "citation_seq",
            sequenceName = "citation_seq",
            allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "citation_seq")
    private Long id;

    @NotNull(message = Constants.ErrorKeys.EMPTY_PLATE_NUMBER_AR)
    @Column(name = "plate_number_ar")
    private String plateNumberAr;

    @NotNull(message = Constants.ErrorKeys.EMPTY_PLATE_NUMBER_EN)
    @Column(name = "plate_number_en")
    private String plateNumberEn;

    @NotNull(message = Constants.ErrorKeys.EMPTY_CITATION_COUNTRY_ID)
    @Column(name = "country_id")
    private Long countryId;

    @NotNull(message = Constants.ErrorKeys.EMPTY_SHIFT_WORK_FORCE_ID)
    @Column(name = "shift_work_force_id")
    private Long shiftWorkforceId;

    @NotNull(message = Constants.ErrorKeys.EMPTY_CITATION_STATUS)
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private CitationStatus status;

    @NotNull(message = Constants.ErrorKeys.EMPTY_CITATION_LONGITUDE)
    @Column(name = "longitude")
    private Double longitude;

    @NotNull(message = Constants.ErrorKeys.EMPTY_CITATION_LATITUDE)
    @Column(name = "latitude")
    private Double latitude;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "citation")
    @Fetch(FetchMode.SUBSELECT)
    @JsonManagedReference
    private List<CitationImage> citationImages = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "citation")
    @Fetch(FetchMode.SUBSELECT)
    @JsonManagedReference
    private List<CitationPenalties> citationPenalties = new ArrayList<>();

    private Long invoiceNumber;

    private String streetName;

    private String comment;

    private Long operationId;


}
