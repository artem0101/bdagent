package ru.education.controller;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.shaded.com.google.common.net.HttpHeaders;
import ru.education.dto.EmployeeDto;
import ru.education.dto.PagedEmployeesDto;
import ru.education.enums.PostType;

import java.util.ArrayList;
import java.util.Collection;

import static io.restassured.RestAssured.given;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
class EmployeeControllerIntegrationTest {

    private static final String TEST_URL = "/api/v1/employee";

    @LocalServerPort
    private Integer port;

    private RequestSpecification requestSpecification;

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

    @Test
    void findNonExistedEmployee() {
        var resultListById = getEmployee(333L, null, null, null, null, HttpStatus.OK.value());
        Assertions.assertNull(resultListById.getEmployees());

        var resultListBySurname = getEmployee(null, "dto.getSurname()", null, null, null, HttpStatus.OK.value());
        Assertions.assertNull(resultListBySurname.getEmployees());

        var resultListByName = getEmployee(null, null, "dto.getName()", null, null, HttpStatus.OK.value());
        Assertions.assertNull(resultListByName.getEmployees());

        var resultListByPatronymic = getEmployee(null, null, null, "dto.getPatronymic()", null, HttpStatus.OK.value());
        Assertions.assertNull(resultListByPatronymic.getEmployees());
    }

    @Test
    void createEmployee() {
        var dto = new EmployeeDto(
                1L,
                "Тестов",
                "Тест",
                "Тестович",
                PostType.DIRECTOR.name()
        );

        createEmployee(dto, HttpStatus.CREATED.value());

        var resultListBySurname = getEmployee(null, dto.getSurname(), null, null, null, HttpStatus.OK.value());
        assertEmployeeMatchesDto(resultListBySurname.getEmployees(), dto);

        var id = resultListBySurname.getEmployees().stream().findFirst().get().getId();

        var resultListById = getEmployee(id, null, null, null, null, HttpStatus.OK.value());
        assertEmployeeMatchesDto(resultListById.getEmployees(), dto);

        var resultListByName = getEmployee(null, null, dto.getName(), null, null, HttpStatus.OK.value());
        assertEmployeeMatchesDto(resultListByName.getEmployees(), dto);

        var resultListByPatronymic = getEmployee(null, null, null, dto.getPatronymic(), null, HttpStatus.OK.value());
        assertEmployeeMatchesDto(resultListByPatronymic.getEmployees(), dto);

        var resultListByPost = getEmployee(null, null, null, null, dto.getPost(), HttpStatus.OK.value());
        assertEmployeeMatchesDto(resultListByPost.getEmployees(), dto);
    }

    @Test
    void findEmployeesWithPaginationReturnsCorrectPageTest() {
        var emp1 = new EmployeeDto();
        emp1.setSurname("Ivanov");
        emp1.setName("Ivan");
        emp1.setPatronymic("Ivanovich");
        emp1.setPost(PostType.OPERATOR.name());

        var emp2 = new EmployeeDto();
        emp2.setSurname("Petrov");
        emp2.setName("Petr");
        emp2.setPatronymic("Petrovich");
        emp2.setPost(PostType.OPERATOR.name());

        var emp3 = new EmployeeDto();
        emp3.setSurname("Sidorov");
        emp3.setName("Sidor");
        emp3.setPatronymic("Sidorovich");
        emp3.setPost(PostType.OPERATOR.name());

        createEmployee(emp1, HttpStatus.CREATED.value());
        createEmployee(emp2, HttpStatus.CREATED.value());
        createEmployee(emp3, HttpStatus.CREATED.value());

        PagedEmployeesDto result = getEmployeeWithPagination(null, null, null, null, null, 0, 2, HttpStatus.OK.value());

        Assertions.assertNotNull(result);
        Assertions.assertEquals(2, result.getEmployees().size());
        Assertions.assertEquals(0, result.getPage());
        Assertions.assertEquals(2, result.getSize());
    }

    @Test
    void findEmployeesWithPaginationSecondPageTest() {
        var emp1 = new EmployeeDto();
        emp1.setSurname("Ivanova");
        emp1.setName("Ivana");
        emp1.setPatronymic("Ivanovicha");
        emp1.setPost(PostType.OPERATOR.name());

        var emp2 = new EmployeeDto();
        emp2.setSurname("Petrova");
        emp2.setName("Petra");
        emp2.setPatronymic("Petrovicha");
        emp2.setPost(PostType.OPERATOR.name());

        var emp3 = new EmployeeDto();
        emp3.setSurname("Sidorova");
        emp3.setName("Sidora");
        emp3.setPatronymic("Sidorovicha");
        emp3.setPost(PostType.OPERATOR.name());

        createEmployee(emp1, HttpStatus.CREATED.value());
        createEmployee(emp2, HttpStatus.CREATED.value());
        createEmployee(emp3, HttpStatus.CREATED.value());

        var result = getEmployeeWithPagination(null, null, null, null, null, 1, 2, HttpStatus.OK.value());

        Assertions.assertNotNull(result);
        Assertions.assertTrue(result.getEmployees().size() >= 1);
        Assertions.assertEquals(1, result.getPage());
        Assertions.assertEquals(2, result.getSize());
    }

    @Test
    void findEmployeesWithPaginationFilteringAndPaginationTest() {
        var emp1 = new EmployeeDto();
        emp1.setSurname("Ivanov");
        emp1.setName("Ivan");
        emp1.setPatronymic("Ivanovich");
        emp1.setPost(PostType.OPERATOR.name());

        var emp2 = new EmployeeDto();
        emp2.setSurname("Petrov");
        emp2.setName("Petr");
        emp2.setPatronymic("Petrovich");
        emp2.setPost(PostType.OPERATOR.name());

        var emp3 = new EmployeeDto();
        emp3.setSurname("Sidorov");
        emp3.setName("Sidor");
        emp3.setPatronymic("Sidorovich");
        emp3.setPost(PostType.OPERATOR.name());

        createEmployee(emp1, HttpStatus.CREATED.value());
        createEmployee(emp2, HttpStatus.CREATED.value());
        createEmployee(emp3, HttpStatus.CREATED.value());

        PagedEmployeesDto result = getEmployeeWithPagination(null, null, null, null, PostType.OPERATOR.name(), 0, 2, HttpStatus.OK.value());

        Assertions.assertNotNull(result);
        Assertions.assertEquals(2, result.getEmployees().size());
        Assertions.assertEquals(0, result.getPage());
        Assertions.assertEquals(2, result.getSize());


        Assertions.assertTrue(result.getEmployees().stream()
                .allMatch(e -> "OPERATOR".equals(e.getPost())));
    }

    @Test
    void createEmployeeWithoutRequiredFieldsTest() {
        var emp1 = new EmployeeDto();
        emp1.setSurname("Ivanov");
        emp1.setName("Ivan");
        emp1.setPatronymic("Ivanovich");

        var emp2 = new EmployeeDto();
        emp2.setSurname("Petrov");
        emp2.setName("Petr");
        emp2.setPost(PostType.OPERATOR.name());

        var emp3 = new EmployeeDto();
        emp3.setSurname("Sidorov");
        emp3.setPatronymic("Sidorovich");
        emp3.setPost(PostType.OPERATOR.name());

        var emp4 = new EmployeeDto();
        emp4.setName("Sidor");
        emp4.setPatronymic("Sidorovich");
        emp4.setPost(PostType.OPERATOR.name());

        createEmployee(emp1, HttpStatus.BAD_REQUEST.value());
        createEmployee(emp2, HttpStatus.BAD_REQUEST.value());
        createEmployee(emp3, HttpStatus.BAD_REQUEST.value());
        createEmployee(emp4, HttpStatus.BAD_REQUEST.value());
    }

    private void assertEmployeeMatchesDto(Collection<EmployeeDto> resultList, EmployeeDto expected) {
        Assertions.assertFalse(resultList.isEmpty());
        var employee = resultList.iterator().next();

        Assertions.assertEquals(expected.getSurname(), employee.getSurname());
        Assertions.assertEquals(expected.getName(), employee.getName());
        Assertions.assertEquals(expected.getPatronymic(), employee.getPatronymic());
        Assertions.assertEquals(expected.getPost(), employee.getPost());
    }

    private PagedEmployeesDto getEmployee(
            Long id,
            String surname,
            String name,
            String patronymic,
            String post,
            @DefaultValue("HttpStatus.OK.value()") int statusCode
    ) {
        var path = new StringBuilder(TEST_URL);
        var params = new ArrayList<String>();

        if (id != null) params.add("id=" + id);
        if (surname != null && !surname.isEmpty()) params.add("surname=" + surname);
        if (name != null && !name.isEmpty()) params.add("name=" + name);
        if (patronymic != null && !patronymic.isEmpty()) params.add("patronymic=" + patronymic);
        if (post != null && !post.isEmpty()) params.add("post=" + post);

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

    private void createEmployee(EmployeeDto dto, int statusCode) {
        given(requestSpecification)
                .when()
                .body(dto)
                .post(TEST_URL)
                .then()
                .statusCode(statusCode)
                .log()
                .ifValidationFails(LogDetail.ALL);
    }

    private PagedEmployeesDto getEmployeeWithPagination(
            Long id,
            String surname,
            String name,
            String patronymic,
            String post,
            int page,
            int size,
            @DefaultValue("HttpStatus.OK.value()") int statusCode
    ) {
        var path = new StringBuilder(TEST_URL);
        var params = new ArrayList<String>();

        if (id != null) params.add("id=" + id);
        if (surname != null && !surname.isEmpty()) params.add("surname=" + surname);
        if (name != null && !name.isEmpty()) params.add("name=" + name);
        if (patronymic != null && !patronymic.isEmpty()) params.add("patronymic=" + patronymic);
        if (post != null && !post.isEmpty()) params.add("post=" + post);
        params.add("page=" + page);
        params.add("size=" + size);

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
