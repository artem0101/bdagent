package ru.education.controller.employee;

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
import ru.education.dto.EmployeeDto;
import ru.education.dto.paged.PagedEmployeesDto;

import java.util.ArrayList;

import static io.restassured.RestAssured.given;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
class BaseEmployeeTest {

    protected static final String EMPLOYEE_URL = "/api/v1/employee";

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

    protected void deleteEmployee(long id, int statusCode) {
        given(requestSpecification)
                .delete(EMPLOYEE_URL + "/" + id)
                .then()
                .statusCode(statusCode);
    }

    protected void updateEmployee(EmployeeDto dto, long id, int statusCode) {
        given(requestSpecification)
                .body(dto)
                .put(EMPLOYEE_URL + "/" + id)
                .then()
                .statusCode(statusCode);
    }

    protected PagedEmployeesDto getEmployee(
            Long id,
            String surname,
            String name,
            String patronymic,
            String post,
            Boolean isDeleted,
            Integer page,
            Integer size,
            @DefaultValue("HttpStatus.OK.value()") int statusCode
    ) {
        return getEmployee(id, surname, name, patronymic, post, isDeleted, page, size, null, null, statusCode);
    }

    protected PagedEmployeesDto getEmployee(
            Long id,
            String surname,
            String name,
            String patronymic,
            String post,
            Boolean isDeleted,
            Integer page,
            Integer size,
            String sortBy,
            String sortDirection,
            int statusCode
    ) {
        var path = new StringBuilder(EMPLOYEE_URL);
        var params = new ArrayList<String>();

        if (id != null) params.add("id=" + id);
        if (surname != null && !surname.isEmpty()) params.add("surname=" + surname);
        if (name != null && !name.isEmpty()) params.add("name=" + name);
        if (patronymic != null && !patronymic.isEmpty()) params.add("patronymic=" + patronymic);
        if (post != null && !post.isEmpty()) params.add("post=" + post);
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
                .as(PagedEmployeesDto.class);
    }
}
