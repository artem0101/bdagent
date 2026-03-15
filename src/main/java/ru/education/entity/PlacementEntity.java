package ru.education.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
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
        name = "placements"
)
public class PlacementEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "houses_id_seq"
    )
    @SequenceGenerator(
            name = "houses_id_seq",
            sequenceName = "realtor_agency.houses_id_seq",
            allocationSize = 1
    )
    private long id;
    @OneToOne(
            fetch = FetchType.LAZY
    )
    @JoinColumn(
            name = "subject_id",
            nullable = false,
            referencedColumnName = "id",
            unique = true,
            foreignKey = @ForeignKey(
                    name = "fk_placements_subjects"
            )
    )
    private SubjectEntity subject;
    private int floors;
    private int rooms;
    private double area;

}
