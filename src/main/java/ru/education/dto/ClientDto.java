package ru.education.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Builder
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@NoArgsConstructor
@AllArgsConstructor
public class ClientDto {

    private long id;
    private String surname;
    private String name;
    private String patronymic;
    private String phoneNumber;
    private String email;
    private Instant birthday;

}
