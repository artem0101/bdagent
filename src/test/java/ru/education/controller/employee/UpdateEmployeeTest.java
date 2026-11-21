package ru.education.controller.employee;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import ru.education.dto.EmployeeDto;
import ru.education.enums.PostType;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;

class UpdateEmployeeTest extends BaseEmployeeTest {

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
        assertFalse(searchResult.getEmployees().isEmpty());
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
        assertFalse(updatedResult.getEmployees().isEmpty());
        var updatedEmployee = updatedResult.getEmployees().iterator().next();

        assertEquals(updateDto.getId(), updatedEmployee.getId());
        assertEquals(updateDto.getSurname(), updatedEmployee.getSurname());
        assertEquals(updateDto.getName(), updatedEmployee.getName());
        assertEquals(updateDto.getPatronymic(), updatedEmployee.getPatronymic());
        assertEquals(updateDto.getPost(), updatedEmployee.getPost());
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
        assertFalse(searchResult.getEmployees().isEmpty());
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
        assertFalse(updatedResult.getEmployees().isEmpty());
        var updatedEmployee = updatedResult.getEmployees().iterator().next();

        assertEquals(updateDto.getId(), updatedEmployee.getId());
        assertEquals("Павел", updatedEmployee.getName());
        assertEquals(PostType.DIRECTOR.name(), updatedEmployee.getPost());
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
        assertFalse(searchResult.getEmployees().isEmpty());
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
        assertFalse(searchResult.getEmployees().isEmpty());
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
        assertFalse(searchResult.getEmployees().isEmpty());
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
        assertFalse(searchResult.getEmployees().isEmpty());
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
        assertFalse(searchResult.getEmployees().isEmpty());
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
        assertFalse(updatedResult.getEmployees().isEmpty());
        var updatedEmployee = updatedResult.getEmployees().iterator().next();

        assertEquals(PostType.DIRECTOR.name(), updatedEmployee.getPost());
    }
}
