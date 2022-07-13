package com.eden.enforcementService.common.model.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.*;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Table(name = "whitelistDetails")
@Where(clause = "deleted <> true")
@SQLDelete(sql = "UPDATE {h-schema} whitelistDetails SET deleted = true WHERE id = ?", check = ResultCheckStyle.COUNT)
public class WhitelistDetails extends BaseEntity {

    @Id
    @SequenceGenerator(name = "whitelistDetails_vehicle_seq",
            sequenceName = "whitelistDetails_vehicle_seq",
            allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "whitelistDetails_vehicle_seq")
    private Long id;

    private LocalDate fromDate;

    private LocalDate toDate;

    private LocalTime timeFrom;

    private LocalTime timeTo;

    private String userName;

    private String description;

    @ManyToOne(fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "whitelist_id")
    private Whitelist whitelist;
}
