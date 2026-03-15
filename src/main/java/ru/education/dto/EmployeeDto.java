package ru.education.dto;

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
public class EmployeeDto {

    private long id;
    private String surname;
    private String name;
    private String patronymic;
    private String post;

}
