package com.eden.enforcementService.common.model.entity;

import com.eden.enforcementService.common.model.enums.VehicleType;
import com.eden.enforcementService.util.Constants;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@ToString
@Table(name = "black_list_vehicle", uniqueConstraints = @UniqueConstraint(columnNames = {"plate_number_ar", "plate_number_en", "country_id"}))
public class BlackListedVehicle extends BaseEntity {

    @Id
    @SequenceGenerator(name = "black_list_vehicle_seq",
            sequenceName = "black_list_vehicle_seq",
            allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "black_list_vehicle_seq")
    private Long id;

    @NotNull(message = Constants.ErrorKeys.EMPTY_PLATE_NUMBER_AR)
    @Column(name = "plate_number_ar")
    private String plateNumberAr;

    @NotNull(message = Constants.ErrorKeys.EMPTY_PLATE_NUMBER_EN)
    @Column(name = "plate_number_en")
    private String plateNumberEn;

    @Column(name = "country_id", nullable = false)
    private Long countryId;

    @Column(name = "country_name", nullable = false)
    private String countryName;

    @Column(name = "color_id")
    private Long colorId;

    @Column(name = "make_id")
    private Long makeId;

    @Column(name = "model")
    private String model;

    @NotNull(message = Constants.ErrorKeys.EMPTY_VEHICLE_TYPE)
    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private VehicleType type;

    private String description;

    private String userName;

    public BlackListedVehicle(Long id) {
        this.id = id;
    }
}
