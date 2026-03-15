package ru.education.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.Basic;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.education.enums.TransactionStatus;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
        schema = "realtor_agency",
        name = "transactions"
)
public class TransactionEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "transactions_id_seq"
    )
    @SequenceGenerator(
            name = "transactions_id_seq",
            sequenceName = "realtor_agency.transactions_id_seq",
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
                    name = "fk_transactions_subjects"
            )
    )
    private SubjectEntity subject;
    @OneToOne(
            fetch = FetchType.LAZY
    )
    @JoinColumn(
            name = "seller_id",
            nullable = false,
            referencedColumnName = "id",
            unique = true,
            foreignKey = @ForeignKey(
                    name = "fk_transactions_sellers"
            )
    )
    private ClientEntity seller;
    @OneToOne(
            fetch = FetchType.LAZY
    )
    @JoinColumn(
            name = "buyer_id",
            nullable = false,
            referencedColumnName = "id",
            unique = true,
            foreignKey = @ForeignKey(
                    name = "fk_transactions_buyers"
            )
    )
    private ClientEntity buyer;
    @OneToOne(
            fetch = FetchType.LAZY
    )
    @JoinColumn(
            name = "employee_id",
            nullable = false,
            referencedColumnName = "id",
            unique = true,
            foreignKey = @ForeignKey(
                    name = "fk_transactions_employees"
            )
    )
    private EmployeeEntity employee;
    private Instant transactionDate;
    private BigDecimal amount;
    @Basic
    @Enumerated(EnumType.STRING)
    private TransactionStatus status;

}
