package ru.education.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
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

    private Long id;

    @NotBlank
    private String surname;

    @NotBlank
    private String name;

    @NotBlank
    private String patronymic;

    @NotBlank
    @Pattern(regexp = "^\\+7[0-9]{10}$|^8[0-9]{10}$", message = "Phone number must be in format +79001234567 or 89001234567")
    private String phoneNumber;

    @NotBlank
    @Email(message = "Email must be valid")
    private String email;

    private Instant birthday;

}
