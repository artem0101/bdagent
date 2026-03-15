package ru.education.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
        schema = "realtor_agency",
        name = "clients"
)
public class ClientEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "clients_id_seq"
    )
    @SequenceGenerator(
            name = "clients_id_seq",
            sequenceName = "realtor_agency.clients_id_seq",
            allocationSize = 1
    )
    private long id;
    private String surname;
    private String name;
    private String patronymic;
    private String phoneNumber;
    private Instant birthday;

}
