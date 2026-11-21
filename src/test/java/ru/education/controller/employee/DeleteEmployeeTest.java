package ru.education.controller.employee;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import ru.education.dto.EmployeeDto;
import ru.education.enums.PostType;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DeleteEmployeeTest extends BaseEmployeeTest {

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
        assertFalse(searchResultBeforeDelete.getEmployees().isEmpty());
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
        assertNull(searchResultAfterDelete.getEmployees());

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
        assertNotNull(searchResultAfterDelete.getEmployees());
        assertFalse(searchResultAfterDelete.getEmployees().isEmpty());

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
        assertTrue(searchAllResult.getEmployees().stream()
                .anyMatch(e -> e.getId() != null && e.getId().equals(employeeId)));
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
        assertFalse(searchResult.getEmployees().isEmpty());
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
