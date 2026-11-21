package ru.education.controller.client;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import ru.education.dto.ClientDto;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

class ValidationClientTest extends BaseClientTest {

    @Test
    void incorrectSurnameTest() {
        var clientDto = new ClientDto(
                null,
                "",
                "Иван",
                "Иванович",
                "+79001234567",
                "ivan@example.com",
                Instant.now().minus(1000, ChronoUnit.DAYS),
                null
        );

        createClient(clientDto, HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void incorrectNameTest() {
        var clientDto = new ClientDto(
                null,
                "Ivanov",
                "",
                "Иванович",
                "+79001234567",
                "ivan@example.com",
                Instant.now().minus(1000, ChronoUnit.DAYS),
                null
        );

        createClient(clientDto, HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void incorrectPatronymicTest() {
        var clientDto = new ClientDto(
                null,
                "Ivanov",
                "Иван",
                "",
                "+79001234567",
                "ivan@example.com",
                Instant.now().minus(1000, ChronoUnit.DAYS),
                null
        );

        createClient(clientDto, HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void incorrectEmailTest() {
        var clientDto = new ClientDto(
                null,
                "Ivanov",
                "Иван",
                "Иванович",
                "+79001234567",
                "invalid-email",
                Instant.now().minus(1000, ChronoUnit.DAYS),
                null
        );

        createClient(clientDto, HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void incorrectPhoneNumberTest() {
        var clientDto = new ClientDto(
                null,
                "Ivanov",
                "Иван",
                "Иванович",
                "invalid-phone",
                "ivan@example.com",
                Instant.now().minus(1000, ChronoUnit.DAYS),
                null
        );

        createClient(clientDto, HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void invalidPhoneNumberFormatWithPlus7() {
        var clientDto = new ClientDto(
                null,
                "Ivanov",
                "Иван",
                "Иванович",
                "+7900123456",
                "ivan@example.com",
                Instant.now().minus(1000, ChronoUnit.DAYS),
                null
        );

        createClient(clientDto, HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void invalidPhoneNumberFormatWith8() {
        var clientDto = new ClientDto(
                null,
                "Ivanov",
                "Иван",
                "Иванович",
                "8900123456",
                "ivan@example.com",
                Instant.now().minus(1000, ChronoUnit.DAYS),
                null
        );

        createClient(clientDto, HttpStatus.BAD_REQUEST.value());
    }
}
