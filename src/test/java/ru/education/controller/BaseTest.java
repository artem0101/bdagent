package ru.education.controller;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.shaded.com.google.common.net.HttpHeaders;
import ru.education.dto.ClientDto;
import ru.education.dto.EmployeeDto;
import ru.education.dto.paged.PagedClientsDto;
import ru.education.dto.paged.PagedEmployeesDto;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;

import static io.restassured.RestAssured.given;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
class BaseTest {

    protected static final String EMPLOYEE_URL = "/api/v1/employee";
    protected static final String CLIENT_URL = "/api/v1/client";

    @LocalServerPort
    private Integer port;

    protected RequestSpecification requestSpecification;

    @BeforeEach
    void setUpAbstractIntegrationTest() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        requestSpecification = new RequestSpecBuilder()
                .setPort(port)
                .addHeader(
                        HttpHeaders.CONTENT_TYPE,
                        MediaType.APPLICATION_JSON_VALUE
                )
                .build();
    }

    protected void assertEmployeeMatchesDto(Collection<EmployeeDto> resultList, EmployeeDto expected) {
        Assertions.assertFalse(resultList.isEmpty());
        var employee = resultList.iterator().next();

        Assertions.assertEquals(expected.getSurname(), employee.getSurname());
        Assertions.assertEquals(expected.getName(), employee.getName());
        Assertions.assertEquals(expected.getPatronymic(), employee.getPatronymic());
        Assertions.assertEquals(expected.getPost(), employee.getPost());
    }

    protected void createEmployee(EmployeeDto dto, int statusCode) {
        given(requestSpecification)
                .when()
                .body(dto)
                .post(EMPLOYEE_URL)
                .then()
                .statusCode(statusCode)
                .log()
                .ifValidationFails(LogDetail.ALL);
    }

    protected PagedEmployeesDto getEmployee(
            Long id,
            String surname,
            String name,
            String patronymic,
            String post,
            Integer page,
            Integer size,
            @DefaultValue("HttpStatus.OK.value()") int statusCode
    ) {
        var path = new StringBuilder(EMPLOYEE_URL);
        var params = new ArrayList<String>();

        if (id != null) params.add("id=" + id);
        if (surname != null && !surname.isEmpty()) params.add("surname=" + surname);
        if (name != null && !name.isEmpty()) params.add("name=" + name);
        if (patronymic != null && !patronymic.isEmpty()) params.add("patronymic=" + patronymic);
        if (post != null && !post.isEmpty()) params.add("post=" + post);
        if (page != null) params.add("page=" + page);
        if (size != null) params.add("size=" + size);

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
                .as(PagedEmployeesDto.class);
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

    protected PagedClientsDto getClient(
            Long id,
            String surname,
            String name,
            String patronymic,
            String email,
            String phoneNumber,
            Instant birthday,
            Integer page,
            Integer size,
            @DefaultValue("HttpStatus.OK.value()") int statusCode
    ) {
        var path = new StringBuilder(EMPLOYEE_URL);
        var params = new ArrayList<String>();

        if (id != null) params.add("id=" + id);
        if (surname != null && !surname.isEmpty()) params.add("surname=" + surname);
        if (name != null && !name.isEmpty()) params.add("name=" + name);
        if (patronymic != null && !patronymic.isEmpty()) params.add("patronymic=" + patronymic);
        if (email != null && !email.isEmpty()) params.add("email=" + email);
        if (phoneNumber != null && !phoneNumber.isEmpty()) params.add("phoneNumber=" + phoneNumber);
        if (birthday != null) params.add("birthday=" + birthday);
        if (page != null) params.add("page=" + page);
        if (size != null) params.add("size=" + size);

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
