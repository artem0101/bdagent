package ru.education.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import ru.education.dto.EmployeeDto;
import ru.education.enums.PostType;

import java.util.ArrayList;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.nullValue;

class EmployeeControllerIntegrationTest extends BaseTest {

    @Test
    void shouldCreateEmployeeAndReturnCreatedStatus() {
        var dto = new EmployeeDto(
                null,
                "Тестов",
                "Тест",
                "Тестович",
                PostType.DIRECTOR.name()
        );

        var response = given(requestSpecification)
                .body(dto)
                .post(EMPLOYEE_URL);

        response.then()
                .statusCode(HttpStatus.CREATED.value())
                .header("Content-Type", nullValue());
    }

    @Test
    void shouldFindEmployeeById() {
        var dto = new EmployeeDto(
                null,
                "Михайлов",
                "Михаил",
                "Михайлович",
                PostType.DIRECTOR.name()
        );
        createEmployee(dto, HttpStatus.CREATED.value());

        var resultBySurname = getEmployee(null, dto.getSurname(), null, null, null, 0, 10, HttpStatus.OK.value());

        Assertions.assertNotNull(resultBySurname);
        Assertions.assertNotNull(resultBySurname.getEmployees());
        Assertions.assertFalse(resultBySurname.getEmployees().isEmpty());

        var employee = resultBySurname.getEmployees().iterator().next();
        Assertions.assertEquals(dto.getSurname(), employee.getSurname());
        Assertions.assertEquals(dto.getName(), employee.getName());
        Assertions.assertEquals(dto.getPatronymic(), employee.getPatronymic());
        Assertions.assertEquals(dto.getPost(), employee.getPost());

        Long id = employee.getId();

        var resultById = getEmployee(id, null, null, null, null, 0, 10, HttpStatus.OK.value());
        Assertions.assertNotNull(resultById);
        Assertions.assertNotNull(resultById.getEmployees());
        Assertions.assertFalse(resultById.getEmployees().isEmpty());

        var employeeById = resultById.getEmployees().iterator().next();
        Assertions.assertEquals(dto.getSurname(), employeeById.getSurname());
        Assertions.assertEquals(dto.getName(), employeeById.getName());
        Assertions.assertEquals(dto.getPatronymic(), employeeById.getPatronymic());
        Assertions.assertEquals(dto.getPost(), employeeById.getPost());
    }

    @Test
    void shouldFindEmployeesBySurname() {
        var dto = new EmployeeDto(
                null,
                "Стасов",
                "Стас",
                "Станиславович",
                PostType.DIRECTOR.name()
        );
        createEmployee(dto, HttpStatus.CREATED.value());

        var result = getEmployee(null, dto.getSurname(), null, null, null, 0, 10, HttpStatus.OK.value());

        Assertions.assertNotNull(result);
        Assertions.assertNotNull(result.getEmployees());
        Assertions.assertFalse(result.getEmployees().isEmpty());

        var employee = result.getEmployees().iterator().next();
        Assertions.assertEquals(dto.getSurname(), employee.getSurname());
    }

    @Test
    void shouldFindEmployeesByName() {
        var dto = new EmployeeDto(
                null,
                "Юрьев",
                "Юрий",
                "Юрьевич",
                PostType.DIRECTOR.name()
        );
        createEmployee(dto, HttpStatus.CREATED.value());

        var result = getEmployee(null, null, dto.getName(), null, null, 0, 10, HttpStatus.OK.value());

        Assertions.assertNotNull(result);
        Assertions.assertNotNull(result.getEmployees());
        Assertions.assertFalse(result.getEmployees().isEmpty());

        var employee = result.getEmployees().iterator().next();
        Assertions.assertEquals(dto.getName(), employee.getName());
    }

    @Test
    void shouldFindEmployeesByPatronymic() {
        var dto = new EmployeeDto(
                null,
                "Антонов",
                "Антон",
                "Антонович",
                PostType.DIRECTOR.name()
        );
        createEmployee(dto, HttpStatus.CREATED.value());

        var result = getEmployee(null, null, null, dto.getPatronymic(), null, 0, 10, HttpStatus.OK.value());

        Assertions.assertNotNull(result);
        Assertions.assertNotNull(result.getEmployees());
        Assertions.assertFalse(result.getEmployees().isEmpty());

        var employee = result.getEmployees().iterator().next();
        Assertions.assertEquals(dto.getPatronymic(), employee.getPatronymic());
    }

    @Test
    void shouldFindEmployeesByPost() {
        var dto = new EmployeeDto(
                null,
                "Гришин",
                "Григорий",
                "Григорьевич",
                PostType.OPERATOR.name()
        );
        createEmployee(dto, HttpStatus.CREATED.value());

        var result = getEmployee(null, null, null, null, dto.getPost(), 0, 10, HttpStatus.OK.value());

        Assertions.assertNotNull(result);
        Assertions.assertNotNull(result.getEmployees());
        Assertions.assertFalse(result.getEmployees().isEmpty());

        var employee = result.getEmployees().iterator().next();
        Assertions.assertEquals(dto.getPost(), employee.getPost());
    }

    @Test
    void shouldReturnEmptyListWhenNoEmployeesFound() {
        var result = getEmployee(99999L, null, null, null, null, 0, 10, HttpStatus.OK.value());

        Assertions.assertNotNull(result);
        Assertions.assertNull(result.getEmployees());
    }

    @Test
    void shouldSupportPagination() {
        var employee1 = new EmployeeDto(
                null,
                "Сидоров",
                "Иван",
                "Иванович",
                PostType.OPERATOR.name()
        );
        var employee2 = new EmployeeDto(
                null,
                "Петров",
                "Петр",
                "Петрович",
                PostType.OPERATOR.name()
        );
        var employee3 = new EmployeeDto(
                null,
                "Смирнов",
                "Сергей",
                "Сергеевич",
                PostType.DIRECTOR.name()
        );

        createEmployee(employee1, HttpStatus.CREATED.value());
        createEmployee(employee2, HttpStatus.CREATED.value());
        createEmployee(employee3, HttpStatus.CREATED.value());

        var resultPage1 = getEmployee(null, null, null, null, null, 0, 2, HttpStatus.OK.value());

        Assertions.assertNotNull(resultPage1);
        Assertions.assertNotNull(resultPage1.getEmployees());
        Assertions.assertEquals(2, resultPage1.getSize());
        Assertions.assertEquals(0, resultPage1.getPage());

        var resultPage2 = getEmployee(null, null, null, null, null, 1, 2, HttpStatus.OK.value());

        Assertions.assertNotNull(resultPage2);
        Assertions.assertNotNull(resultPage2.getEmployees());
    }

    @Test
    void shouldSupportSorting() {
        var employee1 = new EmployeeDto(
                null,
                "Алексеев",
                "Алексей",
                "Алексеевич",
                PostType.OPERATOR.name()
        );
        var employee2 = new EmployeeDto(
                null,
                "Борисов",
                "Борис",
                "Борисович",
                PostType.OPERATOR.name()
        );
        var employee3 = new EmployeeDto(
                null,
                "Васильев",
                "Василий",
                "Васильевич",
                PostType.DIRECTOR.name()
        );

        createEmployee(employee1, HttpStatus.CREATED.value());
        createEmployee(employee2, HttpStatus.CREATED.value());
        createEmployee(employee3, HttpStatus.CREATED.value());

        var resultAsc = getEmployee(null, null, null, null, null, 0, 10, "surname", "ASC", HttpStatus.OK.value());

        Assertions.assertNotNull(resultAsc);
        Assertions.assertNotNull(resultAsc.getEmployees());

        var employeesAsc = new ArrayList<>(resultAsc.getEmployees());
        var firstCharOfFirstClient = employeesAsc.getFirst().getSurname().toCharArray()[0];
        var firstCharOfLastClient = employeesAsc.getLast().getSurname().toCharArray()[0];

        Assertions.assertTrue(firstCharOfLastClient > firstCharOfFirstClient);

        var resultDesc = getEmployee(null, null, null, null, null, 0, 10, "surname", "DESC", HttpStatus.OK.value());

        Assertions.assertNotNull(resultDesc);
        Assertions.assertNotNull(resultDesc.getEmployees());

        var employeesDesc = new ArrayList<>(resultDesc.getEmployees());
        firstCharOfFirstClient = employeesDesc.getFirst().getSurname().toCharArray()[0];
        firstCharOfLastClient = employeesDesc.getLast().getSurname().toCharArray()[0];

        Assertions.assertTrue(firstCharOfLastClient < firstCharOfFirstClient);
    }

    @Test
    void shouldCreateEmployeeWithAllFields() {
        var employeeDto = new EmployeeDto(
                null,
                "Иванов",
                "Иван",
                "Иванович",
                PostType.DIRECTOR.name()
        );

        createEmployee(employeeDto, HttpStatus.CREATED.value());

        var result = getEmployee(null, employeeDto.getSurname(), employeeDto.getName(), employeeDto.getPatronymic(), employeeDto.getPost(), 0, 10, HttpStatus.OK.value());

        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.getEmployees().size());

        var createdEmployee = result.getEmployees().iterator().next();
        Assertions.assertEquals(employeeDto.getSurname(), createdEmployee.getSurname());
        Assertions.assertEquals(employeeDto.getName(), createdEmployee.getName());
        Assertions.assertEquals(employeeDto.getPatronymic(), createdEmployee.getPatronymic());
        Assertions.assertEquals(employeeDto.getPost(), createdEmployee.getPost());
    }

    @Test
    void shouldHandleEmptySearchParameters() {
        var result = getEmployee(null, null, null, null, null, 0, 10, HttpStatus.OK.value());

        Assertions.assertNotNull(result);
        Assertions.assertNotNull(result.getEmployees());
    }

    @Test
    void shouldReturnCorrectPageInfo() {
        var employeeDto = new EmployeeDto(
                null,
                "Тестов",
                "Тест",
                "Тестович",
                PostType.DIRECTOR.name()
        );
        createEmployee(employeeDto, HttpStatus.CREATED.value());

        var result = getEmployee(null, null, null, null, null, 0, 5, HttpStatus.OK.value());

        Assertions.assertNotNull(result);
        Assertions.assertEquals(0, result.getPage());
        Assertions.assertEquals(5, result.getSize());
    }

    @Test
    void shouldFindEmployeesByCombinedParameters() {
        var employeeDto = new EmployeeDto(
                null,
                "Петров",
                "Пётр",
                "Петрович",
                PostType.DIRECTOR.name()
        );
        createEmployee(employeeDto, HttpStatus.CREATED.value());

        var result = getEmployee(null, employeeDto.getSurname(), employeeDto.getName(), null, null, 0, 10, HttpStatus.OK.value());

        Assertions.assertNotNull(result);
        Assertions.assertFalse(result.getEmployees().isEmpty());

        var employee = result.getEmployees().iterator().next();
        Assertions.assertEquals(employeeDto.getSurname(), employee.getSurname());
        Assertions.assertEquals(employeeDto.getName(), employee.getName());
    }

    @Test
    void incorrectSurnameTest() {
        var employeeDto = new EmployeeDto(
                null,
                "",
                "Иван",
                "Иванович",
                PostType.DIRECTOR.name()
        );

        createEmployee(employeeDto, HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void incorrectNameTest() {
        var employeeDto = new EmployeeDto(
                null,
                "Ivanov",
                "",
                "Иванович",
                PostType.DIRECTOR.name()
        );

        createEmployee(employeeDto, HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void incorrectPatronymicTest() {
        var employeeDto = new EmployeeDto(
                null,
                "Ivanov",
                "Иван",
                "",
                PostType.DIRECTOR.name()
        );

        createEmployee(employeeDto, HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void incorrectPostTest() {
        var employeeDto = new EmployeeDto(
                null,
                "Ivanov",
                "Иван",
                "Иванович",
                ""
        );

        createEmployee(employeeDto, HttpStatus.BAD_REQUEST.value());
    }
}
