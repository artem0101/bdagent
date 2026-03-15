package ru.education.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Column;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder;
import ru.education.enums.SubjectType;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
        schema = "realtor_agency",
        name = "subjects"
)
public class SubjectEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "subject_id_seq"
    )
    @SequenceGenerator(
            name = "subject_id_seq",
            sequenceName = "realtor_agency.subject_id_seq",
            allocationSize = 1
    )
    private long id;
    private String country;
    private String city;
    private String postalCode;
    private String street;
    private String number;
    private BigDecimal price;
    @Column(
            columnDefinition = "BOOLEAN DEFAULT true"
    )
    private boolean isActive;
    @Enumerated(EnumType.STRING)
    private SubjectType type;

}
