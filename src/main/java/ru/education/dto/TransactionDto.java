package ru.education.dto;

import java.math.BigDecimal;
import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDto {

    private long id;
    private SubjectDto subject;
    private ClientDto seller;
    private ClientDto buyer;
    private EmployeeDto employee;
    private Instant transactionDate;
    private BigDecimal amount;
    private String status;

}
