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
@Table(name = "country")
@Where(clause = "deleted <> true")
@SQLDelete(sql = "UPDATE {h-schema} country SET deleted = true WHERE id = ?", check = ResultCheckStyle.COUNT)
public class Country extends BaseEntity{

    @Id
    @SequenceGenerator(name="test_seq",
            sequenceName="test_seq",
            allocationSize=1,initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="test_seq")
    private Long id;

    @NotNull(message = Constants.ErrorKeys.PENALTY_NAME_REQUIRED)
    @Column(name = "en_name")
    private String enName;

    @NotNull(message = Constants.ErrorKeys.PENALTY_NAME_REQUIRED)
    @Column(name = "ar_name")
    private String arName;

}
