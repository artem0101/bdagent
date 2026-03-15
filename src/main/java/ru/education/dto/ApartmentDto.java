package ru.education.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.experimental.SuperBuilder;


@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@NoArgsConstructor
@AllArgsConstructor
public class ApartmentDto extends SubjectDto {

    private long id;
    private int floor;
    private int rooms;

}
