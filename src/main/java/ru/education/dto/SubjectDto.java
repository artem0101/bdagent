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

    protected long subjectId;
    protected String country;
    protected String city;
    protected String postalCode;
    protected String street;
    protected String number;
    protected BigDecimal price;
    protected boolean isActive;
    protected String type;
    protected long ownerId;

}
