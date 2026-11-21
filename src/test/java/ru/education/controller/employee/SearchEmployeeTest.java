package ru.education.controller.employee;

import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import ru.education.dto.EmployeeDto;
import ru.education.enums.PostType;

import java.util.ArrayList;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

class SearchEmployeeTest extends BaseEmployeeTest {

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

        assertNotNull(resultBySurname);
        assertNotNull(resultBySurname.getEmployees());
        assertFalse(resultBySurname.getEmployees().isEmpty());

        var employee = resultBySurname.getEmployees().iterator().next();
        assertEquals(dto.getSurname(), employee.getSurname());
        assertEquals(dto.getName(), employee.getName());
        assertEquals(dto.getPatronymic(), employee.getPatronymic());
        assertEquals(dto.getPost(), employee.getPost());

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
        assertNotNull(resultById);
        assertNotNull(resultById.getEmployees());
        assertFalse(resultById.getEmployees().isEmpty());

        var employeeById = resultById.getEmployees().iterator().next();
        assertEquals(dto.getSurname(), employeeById.getSurname());
        assertEquals(dto.getName(), employeeById.getName());
        assertEquals(dto.getPatronymic(), employeeById.getPatronymic());
        assertEquals(dto.getPost(), employeeById.getPost());
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

        assertNotNull(result);
        assertNotNull(result.getEmployees());
        assertFalse(result.getEmployees().isEmpty());

        var employee = result.getEmployees().iterator().next();
        assertEquals(dto.getSurname(), employee.getSurname());
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

        assertNotNull(result);
        assertNotNull(result.getEmployees());
        assertFalse(result.getEmployees().isEmpty());

        var employee = result.getEmployees().iterator().next();
        assertEquals(dto.getName(), employee.getName());
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

        assertNotNull(result);
        assertNotNull(result.getEmployees());
        assertFalse(result.getEmployees().isEmpty());

        var employee = result.getEmployees().iterator().next();
        assertEquals(dto.getPatronymic(), employee.getPatronymic());
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

        assertNotNull(result);
        assertNotNull(result.getEmployees());
        assertFalse(result.getEmployees().isEmpty());

        var employee = result.getEmployees().iterator().next();
        assertEquals(dto.getPost(), employee.getPost());
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

        assertNotNull(result);
        assertNull(result.getEmployees());
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

        assertNotNull(resultPage1);
        assertNotNull(resultPage1.getEmployees());
        assertEquals(2, resultPage1.getSize());
        assertEquals(0, resultPage1.getPage());

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

        assertNotNull(resultPage2);
        assertNotNull(resultPage2.getEmployees());
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

        assertNotNull(resultAsc);
        assertNotNull(resultAsc.getEmployees());

        var employeesAsc = new ArrayList<>(resultAsc.getEmployees());
        var firstCharOfFirstClient = employeesAsc.getFirst().getSurname().toCharArray()[0];
        var firstCharOfLastClient = employeesAsc.getLast().getSurname().toCharArray()[0];

        assertTrue(firstCharOfLastClient > firstCharOfFirstClient);

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

        assertNotNull(resultDesc);
        assertNotNull(resultDesc.getEmployees());

        var employeesDesc = new ArrayList<>(resultDesc.getEmployees());
        firstCharOfFirstClient = employeesDesc.getFirst().getSurname().toCharArray()[0];
        firstCharOfLastClient = employeesDesc.getLast().getSurname().toCharArray()[0];

        assertTrue(firstCharOfLastClient < firstCharOfFirstClient);
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

        assertNotNull(result);
        assertEquals(0, result.getPage());
        assertEquals(5, result.getSize());
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

        assertNotNull(result);
        assertFalse(result.getEmployees().isEmpty());

        var employee = result.getEmployees().iterator().next();
        assertEquals(employeeDto.getSurname(), employee.getSurname());
        assertEquals(employeeDto.getName(), employee.getName());
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

        assertNotNull(result);
        assertNotNull(result.getEmployees());
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
        assertFalse(searchResultBeforeDelete.getEmployees().isEmpty());
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
        assertNotNull(deletedEmployeeResult.getEmployees());
        assertTrue(deletedEmployeeResult.getEmployees().stream()
                .anyMatch(e -> Objects.equals(e.getId(), employeeId) && e.getIsDeleted()));
    }
}
