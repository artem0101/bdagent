package ru.education.controller.client;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import ru.education.dto.ClientDto;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.nullValue;

class CreateClientTest extends BaseClientTest {

    @Test
    void shouldCreateClientAndReturnCreatedStatus() {
        var dto = new ClientDto(
                null,
                "Тестов",
                "Тест",
                "Тестович",
                "+79001234567",
                "test@example.com",
                Instant.now().minus(1000, ChronoUnit.DAYS),
                false
        );

        var response = given(requestSpecification)
                .body(dto)
                .post(CLIENT_URL);

        response.then()
                .statusCode(HttpStatus.CREATED.value())
                .header("Content-Type", nullValue());
    }
}
