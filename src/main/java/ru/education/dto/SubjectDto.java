package ru.education.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@SuperBuilder
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@NoArgsConstructor
@AllArgsConstructor
public class SubjectDto {

    private long subjectId;
    private String country;
    private String city;
    private String postalCode;
    private String street;
    private String number;
    private BigDecimal price;
    private boolean isActive;
    private String type;

}
