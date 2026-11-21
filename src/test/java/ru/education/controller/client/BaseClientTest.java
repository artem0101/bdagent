package ru.education.controller.client;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.education.dto.ClientDto;
import ru.education.dto.paged.PagedClientsDto;

import java.util.ArrayList;

import static io.restassured.RestAssured.given;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
class BaseClientTest {

    protected static final String CLIENT_URL = "/api/v1/client";

    @LocalServerPort
    private Integer port;

    protected static RequestSpecification requestSpecification;

    @BeforeAll
    void setUp() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        requestSpecification = new RequestSpecBuilder()
                .setPort(port)
                .addHeader(
                        "Content-Type",
                        MediaType.APPLICATION_JSON_VALUE
                )
                .build();
    }

    protected void createClient(ClientDto dto, int statusCode) {
        given(requestSpecification)
                .when()
                .body(dto)
                .post(CLIENT_URL)
                .then()
                .statusCode(statusCode)
                .log()
                .ifValidationFails(LogDetail.ALL);
    }

    protected void deleteClient(long id, int statusCode) {
        given(requestSpecification)
                .delete(CLIENT_URL + "/" + id)
                .then()
                .statusCode(statusCode);
    }

    protected void updateClient(ClientDto dto, long id, int statusCode) {
        given(requestSpecification)
                .body(dto)
                .put(CLIENT_URL + "/" + id)
                .then()
                .statusCode(statusCode);
    }

    protected PagedClientsDto getClient(
            Long id,
            String surname,
            String name,
            String patronymic,
            String email,
            String phoneNumber,
            Boolean isDeleted,
            Integer page,
            Integer size,
            @DefaultValue("HttpStatus.OK.value()") int statusCode
    ) {
        return getClient(id, surname, name, patronymic, email, phoneNumber, isDeleted, page, size, null, null, statusCode);
    }

    protected PagedClientsDto getClient(
            Long id,
            String surname,
            String name,
            String patronymic,
            String email,
            String phoneNumber,
            Boolean isDeleted,
            Integer page,
            Integer size,
            String sortBy,
            String sortDirection,
            int statusCode
    ) {
        var path = new StringBuilder(CLIENT_URL);
        var params = new ArrayList<String>();

        if (id != null) params.add("id=" + id);
        if (surname != null && !surname.isEmpty()) params.add("surname=" + surname);
        if (name != null && !name.isEmpty()) params.add("name=" + name);
        if (patronymic != null && !patronymic.isEmpty()) params.add("patronymic=" + patronymic);
        if (email != null && !email.isEmpty()) params.add("email=" + email);
        if (phoneNumber != null && !phoneNumber.isEmpty()) params.add("phoneNumber=" + phoneNumber);
        if (isDeleted != null) params.add("isDeleted=" + isDeleted);
        if (page != null) params.add("page=" + page);
        if (size != null) params.add("size=" + size);
        if (sortBy != null && !sortBy.isEmpty()) params.add("sortBy=" + sortBy);
        if (sortDirection != null && !sortDirection.isEmpty()) params.add("sortDirection=" + sortDirection);

        for (int i = 0; i < params.size(); i++) {
            path.append(i == 0 ? "?" : "&").append(params.get(i));
        }

        return given(requestSpecification)
                .when()
                .get(path.toString())
                .then()
                .statusCode(statusCode)
                .log()
                .ifValidationFails(LogDetail.ALL)
                .extract()
                .body()
                .as(PagedClientsDto.class);
    }
}
