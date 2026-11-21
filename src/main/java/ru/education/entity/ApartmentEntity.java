package ru.education.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.OneToOne;
import jakarta.persistence.FetchType;
import jakarta.persistence.CascadeType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
        schema = "realtor_agency",
        name = "apartments"
)
public class ApartmentEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "apartments_id_seq"
    )
    @SequenceGenerator(
            name = "apartments_id_seq",
            sequenceName = "realtor_agency.apartments_id_seq",
            allocationSize = 1
    )
    private long id;
    @OneToOne(
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL
    )
    @JoinColumn(
            name = "subject_id",
            nullable = false,
            referencedColumnName = "id",
            unique = true,
            foreignKey = @ForeignKey(
                    name = "fk_apartments_subjects"
            )
    )
    private SubjectEntity subject;
    private int floor;
    private int rooms;

}
