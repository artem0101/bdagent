package ru.education.entity;

import jakarta.persistence.Basic;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.education.enums.PostType;

import java.io.Serial;
import java.io.Serializable;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
        schema = "realtor_agency",
        name = "employees"
)
public class EmployeeEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "employees_id_seq"
    )
    @SequenceGenerator(
            name = "employees_id_seq",
            sequenceName = "realtor_agency.employees_id_seq",
            allocationSize = 1
    )
    private long id;
    private String surname;
    private String name;
    private String patronymic;
    @Basic
    @Enumerated(EnumType.STRING)
    private PostType post;

}
