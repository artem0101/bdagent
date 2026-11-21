package ru.education.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import ru.education.dto.EmployeeDto;
import ru.education.enums.PostType;

import java.util.ArrayList;
import java.util.Objects;

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

    @Test
    void shouldFindEmployeeById() {
        var dto = new EmployeeDto(
                null,
                "Михайлов",
                "Михаил",
                "Михайлович",
                PostType.DIRECTOR.name(),
                false
        );
        createEmployee(dto, HttpStatus.CREATED.value());

        var resultBySurname = getEmployee(
                null,
                dto.getSurname(),
                null,
                null,
                null,
                null,
                0,
                10,
                HttpStatus.OK.value()
        );

        Assertions.assertNotNull(resultBySurname);
        Assertions.assertNotNull(resultBySurname.getEmployees());
        Assertions.assertFalse(resultBySurname.getEmployees().isEmpty());

        var employee = resultBySurname.getEmployees().iterator().next();
        Assertions.assertEquals(dto.getSurname(), employee.getSurname());
        Assertions.assertEquals(dto.getName(), employee.getName());
        Assertions.assertEquals(dto.getPatronymic(), employee.getPatronymic());
        Assertions.assertEquals(dto.getPost(), employee.getPost());

        var id = employee.getId();

        var resultById = getEmployee(
                id,
                null,
                null,
                null,
                null,
                null,
                0,
                10,
                HttpStatus.OK.value()
        );
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
                PostType.DIRECTOR.name(),
                false
        );
        createEmployee(dto, HttpStatus.CREATED.value());

        var result = getEmployee(
                null,
                dto.getSurname(),
                null,
                null,
                null,
                null,
                0,
                10,
                HttpStatus.OK.value()
        );

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
                PostType.DIRECTOR.name(),
                false
        );
        createEmployee(dto, HttpStatus.CREATED.value());

        var result = getEmployee(
                null,
                null,
                dto.getName(),
                null,
                null,
                null,
                0,
                10,
                HttpStatus.OK.value()
        );

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
                PostType.DIRECTOR.name(),
                false
        );
        createEmployee(dto, HttpStatus.CREATED.value());

        var result = getEmployee(
                null,
                null,
                null,
                dto.getPatronymic(),
                null,
                null,
                0,
                10,
                HttpStatus.OK.value()
        );

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
                PostType.OPERATOR.name(),
                false
        );
        createEmployee(dto, HttpStatus.CREATED.value());

        var result = getEmployee(
                null,
                null,
                null,
                null,
                dto.getPost(),
                null,
                0,
                10,
                HttpStatus.OK.value()
        );

        Assertions.assertNotNull(result);
        Assertions.assertNotNull(result.getEmployees());
        Assertions.assertFalse(result.getEmployees().isEmpty());

        var employee = result.getEmployees().iterator().next();
        Assertions.assertEquals(dto.getPost(), employee.getPost());
    }

    @Test
    void shouldReturnEmptyListWhenNoEmployeesFound() {
        var result = getEmployee(
                99999L,
                null,
                null,
                null,
                null,
                null,
                0,
                10,
                HttpStatus.OK.value()
        );

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
                PostType.OPERATOR.name(),
                false
        );
        var employee2 = new EmployeeDto(
                null,
                "Петров",
                "Петр",
                "Петрович",
                PostType.OPERATOR.name(),
                false
        );
        var employee3 = new EmployeeDto(
                null,
                "Смирнов",
                "Сергей",
                "Сергеевич",
                PostType.DIRECTOR.name(),
                false
        );

        createEmployee(employee1, HttpStatus.CREATED.value());
        createEmployee(employee2, HttpStatus.CREATED.value());
        createEmployee(employee3, HttpStatus.CREATED.value());

        var resultPage1 = getEmployee(
                null,
                null,
                null,
                null,
                null,
                null,
                0,
                2,
                HttpStatus.OK.value()
        );

        Assertions.assertNotNull(resultPage1);
        Assertions.assertNotNull(resultPage1.getEmployees());
        Assertions.assertEquals(2, resultPage1.getSize());
        Assertions.assertEquals(0, resultPage1.getPage());

        var resultPage2 = getEmployee(
                null,
                null,
                null,
                null,
                null,
                null,
                1,
                2,
                HttpStatus.OK.value()
        );

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
                PostType.OPERATOR.name(),
                false
        );
        var employee2 = new EmployeeDto(
                null,
                "Борисов",
                "Борис",
                "Борисович",
                PostType.OPERATOR.name(),
                false
        );
        var employee3 = new EmployeeDto(
                null,
                "Васильев",
                "Василий",
                "Васильевич",
                PostType.DIRECTOR.name(),
                false
        );

        createEmployee(employee1, HttpStatus.CREATED.value());
        createEmployee(employee2, HttpStatus.CREATED.value());
        createEmployee(employee3, HttpStatus.CREATED.value());

        var resultAsc = getEmployee(
                null,
                null,
                null,
                null,
                null,
                null,
                0,
                10,
                "surname",
                Sort.Direction.ASC.toString(),
                HttpStatus.OK.value()
        );

        Assertions.assertNotNull(resultAsc);
        Assertions.assertNotNull(resultAsc.getEmployees());

        var employeesAsc = new ArrayList<>(resultAsc.getEmployees());
        var firstCharOfFirstClient = employeesAsc.getFirst().getSurname().toCharArray()[0];
        var firstCharOfLastClient = employeesAsc.getLast().getSurname().toCharArray()[0];

        Assertions.assertTrue(firstCharOfLastClient > firstCharOfFirstClient);

        var resultDesc = getEmployee(
                null,
                null,
                null,
                null,
                null,
                null,
                0,
                10,
                "surname",
                Sort.Direction.DESC.toString(),
                HttpStatus.OK.value()
        );

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
                PostType.DIRECTOR.name(),
                false
        );

        createEmployee(employeeDto, HttpStatus.CREATED.value());

        var result = getEmployee(
                null,
                employeeDto.getSurname(),
                employeeDto.getName(),
                employeeDto.getPatronymic(),
                employeeDto.getPost(),
                null,
                0,
                10,
                HttpStatus.OK.value()
        );

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
        var result = getEmployee(
                null,
                null,
                null,
                null,
                null,
                null,
                0,
                10,
                HttpStatus.OK.value()
        );

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
                PostType.DIRECTOR.name(),
                false
        );
        createEmployee(employeeDto, HttpStatus.CREATED.value());

        var result = getEmployee(
                null,
                null,
                null,
                null,
                null,
                null,
                0,
                5,
                HttpStatus.OK.value()
        );

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
                PostType.DIRECTOR.name(),
                false
        );
        createEmployee(employeeDto, HttpStatus.CREATED.value());

        var result = getEmployee(
                null,
                employeeDto.getSurname(),
                employeeDto.getName(),
                null,
                null,
                null,
                0,
                10,
                HttpStatus.OK.value()
        );

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
                PostType.DIRECTOR.name(),
                false
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
                PostType.DIRECTOR.name(),
                false
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
                PostType.DIRECTOR.name(),
                false
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
                "",
                false
        );

        createEmployee(employeeDto, HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void shouldUpdateEmployee() {
        var initialDto = new EmployeeDto(
                null,
                "Сидоров",
                "Сидор",
                "Сидорович",
                PostType.OPERATOR.name(),
                false
        );
        createEmployee(initialDto, HttpStatus.CREATED.value());

        var searchResult = getEmployee(
                null,
                initialDto.getSurname(),
                null,
                null,
                null,
                null,
                0,
                10,
                HttpStatus.OK.value()
        );
        Assertions.assertFalse(searchResult.getEmployees().isEmpty());
        var employeeId = searchResult.getEmployees().iterator().next().getId();

        var updateDto = new EmployeeDto(
                employeeId,
                "Сидоров",
                "Сидор",
                "Сидорович",
                PostType.DIRECTOR.name(),
                false
        );

        updateEmployee(updateDto, employeeId, HttpStatus.NO_CONTENT.value());

        var updatedResult = getEmployee(
                employeeId,
                null,
                null,
                null,
                null,
                null,
                0,
                10,
                HttpStatus.OK.value()
        );
        Assertions.assertFalse(updatedResult.getEmployees().isEmpty());
        var updatedEmployee = updatedResult.getEmployees().iterator().next();

        Assertions.assertEquals(updateDto.getId(), updatedEmployee.getId());
        Assertions.assertEquals(updateDto.getSurname(), updatedEmployee.getSurname());
        Assertions.assertEquals(updateDto.getName(), updatedEmployee.getName());
        Assertions.assertEquals(updateDto.getPatronymic(), updatedEmployee.getPatronymic());
        Assertions.assertEquals(updateDto.getPost(), updatedEmployee.getPost());
    }

    @Test
    void shouldUpdateOnlySomeFields() {
        var initialDto = new EmployeeDto(
                null,
                "Петров",
                "Петр",
                "Петрович",
                PostType.OPERATOR.name(),
                false
        );
        createEmployee(initialDto, HttpStatus.CREATED.value());

        var searchResult = getEmployee(
                null,
                initialDto.getSurname(),
                null,
                null,
                null,
                null,
                0,
                10,
                HttpStatus.OK.value()
        );
        Assertions.assertFalse(searchResult.getEmployees().isEmpty());
        var employeeId = searchResult.getEmployees().iterator().next().getId();

        var updateDto = new EmployeeDto(
                employeeId,
                "Петров",
                "Павел",
                "Петрович",
                PostType.DIRECTOR.name(),
                false
        );

        updateEmployee(updateDto, employeeId, HttpStatus.NO_CONTENT.value());

        var updatedResult = getEmployee(
                employeeId,
                null,
                null,
                null,
                null,
                null,
                0,
                10,
                HttpStatus.OK.value());
        Assertions.assertFalse(updatedResult.getEmployees().isEmpty());
        var updatedEmployee = updatedResult.getEmployees().iterator().next();

        Assertions.assertEquals(updateDto.getId(), updatedEmployee.getId());
        Assertions.assertEquals("Павел", updatedEmployee.getName());
        Assertions.assertEquals(PostType.DIRECTOR.name(), updatedEmployee.getPost());
    }

    @Test
    void shouldReturnNotFoundWhenUpdatingNonExistingEmployee() {
        var updateDto = new EmployeeDto(
                99999L,
                "Иванов",
                "Иван",
                "Иванович",
                PostType.DIRECTOR.name(),
                false
        );

        updateEmployee(updateDto, 999999L, HttpStatus.NOT_FOUND.value());
    }

    @Test
    void shouldHandleEmptySurnameDuringUpdate() {
        var initialDto = new EmployeeDto(
                null,
                "Смирнов",
                "Сергей",
                "Сергеевич",
                PostType.OPERATOR.name(),
                false
        );
        createEmployee(initialDto, HttpStatus.CREATED.value());

        var searchResult = getEmployee(
                null,
                initialDto.getSurname(),
                null,
                null,
                null,
                null,
                0,
                10,
                HttpStatus.OK.value()
        );
        Assertions.assertFalse(searchResult.getEmployees().isEmpty());
        var employeeId = searchResult.getEmployees().iterator().next().getId();

        var updateDto = new EmployeeDto(
                employeeId,
                "",
                "Сергей",
                "Сергеевич",
                PostType.OPERATOR.name(),
                false
        );

        updateEmployee(updateDto, employeeId, HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void shouldHandleEmptyNameDuringUpdate() {
        var initialDto = new EmployeeDto(
                null,
                "Кузнецов",
                "Константин",
                "Константинович",
                PostType.OPERATOR.name(),
                false
        );
        createEmployee(initialDto, HttpStatus.CREATED.value());

        var searchResult = getEmployee(
                null,
                initialDto.getSurname(),
                null,
                null,
                null,
                null,
                0,
                10,
                HttpStatus.OK.value()
        );
        Assertions.assertFalse(searchResult.getEmployees().isEmpty());
        var employeeId = searchResult.getEmployees().iterator().next().getId();

        var updateDto = new EmployeeDto(
                employeeId,
                "Кузнецов",
                "",
                "Константинович",
                PostType.OPERATOR.name(),
                false
        );

        updateEmployee(updateDto, employeeId, HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void shouldHandleEmptyPatronymicDuringUpdate() {
        var initialDto = new EmployeeDto(
                null,
                "Попов",
                "Алексей",
                "Алексеевич",
                PostType.OPERATOR.name(),
                false
        );
        createEmployee(initialDto, HttpStatus.CREATED.value());

        var searchResult = getEmployee(
                null,
                initialDto.getSurname(),
                null,
                null,
                null,
                null,
                0,
                10,
                HttpStatus.OK.value()
        );
        Assertions.assertFalse(searchResult.getEmployees().isEmpty());
        var employeeId = searchResult.getEmployees().iterator().next().getId();

        var updateDto = new EmployeeDto(
                employeeId,
                "Попов",
                "Алексей",
                "",
                PostType.OPERATOR.name(),
                false
        );

        updateEmployee(updateDto, employeeId, HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void shouldHandleEmptyPostDuringUpdate() {
        var initialDto = new EmployeeDto(
                null,
                "Соколов",
                "Михаил",
                "Михайлович",
                PostType.OPERATOR.name(),
                false
        );
        createEmployee(initialDto, HttpStatus.CREATED.value());

        var searchResult = getEmployee(
                null,
                initialDto.getSurname(),
                null,
                null,
                null,
                null,
                0,
                10,
                HttpStatus.OK.value()
        );
        Assertions.assertFalse(searchResult.getEmployees().isEmpty());
        var employeeId = searchResult.getEmployees().iterator().next().getId();

        var updateDto = new EmployeeDto(
                employeeId,
                "Соколов",
                "Михаил",
                "Михайлович",
                "",
                false
        );

        updateEmployee(updateDto, employeeId, HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void shouldUpdateEmployeePostType() {
        var initialDto = new EmployeeDto(
                null,
                "Васильев",
                "Николай",
                "Николаевич",
                PostType.OPERATOR.name(),
                false
        );
        createEmployee(initialDto, HttpStatus.CREATED.value());

        var searchResult = getEmployee(
                null,
                initialDto.getSurname(),
                null,
                null,
                null,
                null,
                0,
                10,
                HttpStatus.OK.value()
        );
        Assertions.assertFalse(searchResult.getEmployees().isEmpty());
        var employeeId = searchResult.getEmployees().iterator().next().getId();

        var updateDto = new EmployeeDto(
                employeeId,
                "Васильев",
                "Николай",
                "Николаевич",
                PostType.DIRECTOR.name(),
                false
        );

        updateEmployee(updateDto, employeeId, HttpStatus.NO_CONTENT.value());

        var updatedResult = getEmployee(
                employeeId,
                null,
                null,
                null,
                null,
                null,
                0,
                10,
                HttpStatus.OK.value()
        );
        Assertions.assertFalse(updatedResult.getEmployees().isEmpty());
        var updatedEmployee = updatedResult.getEmployees().iterator().next();

        Assertions.assertEquals(PostType.DIRECTOR.name(), updatedEmployee.getPost());
    }

    @Test
    void shouldDeleteEmployeeAndExcludeFromSearch() {
        var initialDto = new EmployeeDto(
                null,
                "Морозов",
                "Максим",
                "Максимович",
                PostType.OPERATOR.name(),
                false
        );
        createEmployee(initialDto, HttpStatus.CREATED.value());

        var searchResultBeforeDelete = getEmployee(
                null,
                initialDto.getSurname(),
                null,
                null,
                null,
                null,
                0,
                10,
                HttpStatus.OK.value()
        );
        Assertions.assertFalse(searchResultBeforeDelete.getEmployees().isEmpty());
        var employeeId = searchResultBeforeDelete.getEmployees().iterator().next().getId();

        deleteEmployee(employeeId, HttpStatus.ACCEPTED.value());

        var searchResultAfterDelete = getEmployee(
                null,
                initialDto.getSurname(),
                null,
                null,
                null,
                null,
                0,
                10,
                HttpStatus.OK.value()
        );
        Assertions.assertNull(searchResultAfterDelete.getEmployees());

        searchResultAfterDelete = getEmployee(
                null,
                initialDto.getSurname(),
                null,
                null,
                null,
                true,
                0,
                10,
                HttpStatus.OK.value()
        );
        Assertions.assertNotNull(searchResultAfterDelete.getEmployees());
        Assertions.assertFalse(searchResultAfterDelete.getEmployees().isEmpty());

        var searchAllResult = getEmployee(
                employeeId,
                null,
                null,
                null,
                null,
                true,
                0,
                10,
                HttpStatus.OK.value()
        );
        Assertions.assertTrue(searchAllResult.getEmployees().stream()
                .anyMatch(e -> Objects.equals(e.getId(), employeeId)));
    }

    @Test
    void shouldFindDeletedEmployeeWithIsDeletedTrue() {
        var initialDto = new EmployeeDto(
                null,
                "Волков",
                "Андрей",
                "Андреевич",
                PostType.OPERATOR.name(),
                false
        );
        createEmployee(initialDto, HttpStatus.CREATED.value());

        var searchResultBeforeDelete = getEmployee(
                null,
                initialDto.getSurname(),
                null,
                null,
                null,
                false,
                0,
                10,
                HttpStatus.OK.value()
        );
        Assertions.assertFalse(searchResultBeforeDelete.getEmployees().isEmpty());
        var employeeId = searchResultBeforeDelete.getEmployees().iterator().next().getId();

        deleteEmployee(employeeId, HttpStatus.ACCEPTED.value());

        var deletedEmployeeResult = getEmployee(
                employeeId,
                null,
                null,
                null,
                null,
                true,
                0,
                10,
                "isDeleted",
                Sort.Direction.DESC.toString(),
                HttpStatus.OK.value()
        );
        Assertions.assertNotNull(deletedEmployeeResult.getEmployees());
        Assertions.assertTrue(deletedEmployeeResult.getEmployees().stream()
                .anyMatch(e -> Objects.equals(e.getId(), employeeId) && e.getIsDeleted()));
    }

    @Test
    void shouldNotUpdateDeletedEmployee() {
        var initialDto = new EmployeeDto(
                null,
                "Новиков",
                "Дмитрий",
                "Дмитриевич",
                PostType.OPERATOR.name(),
                false
        );
        createEmployee(initialDto, HttpStatus.CREATED.value());

        var searchResult = getEmployee(
                null,
                initialDto.getSurname(),
                null,
                null,
                null,
                false,
                0,
                10,
                HttpStatus.OK.value()
        );
        Assertions.assertFalse(searchResult.getEmployees().isEmpty());
        var employeeId = searchResult.getEmployees().iterator().next().getId();

        deleteEmployee(employeeId, HttpStatus.ACCEPTED.value());

        var updateDto = new EmployeeDto(
                employeeId,
                "Новиков",
                "Дмитрий",
                "Дмитриевич",
                PostType.DIRECTOR.name(),
                false
        );

        updateEmployee(updateDto, employeeId, HttpStatus.NOT_FOUND.value());
    }
}
