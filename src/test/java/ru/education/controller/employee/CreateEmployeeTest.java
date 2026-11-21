package ru.education.controller.employee;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import ru.education.dto.EmployeeDto;
import ru.education.enums.PostType;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.nullValue;

class CreateEmployeeTest extends BaseEmployeeTest {

    @Test
    void shouldCreateEmployeeAndReturnCreatedStatus() {
        var dto = new EmployeeDto(
                null,
                "Тестов",
                "Тест",
                "Тестович",
                PostType.DIRECTOR.name(),
                false
        );

        var response = given(requestSpecification)
                .body(dto)
                .post(EMPLOYEE_URL);

        response.then()
                .statusCode(HttpStatus.CREATED.value())
                .header("Content-Type", nullValue());
    }
}
