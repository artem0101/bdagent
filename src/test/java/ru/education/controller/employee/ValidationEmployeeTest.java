package ru.education.controller.employee;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import ru.education.dto.EmployeeDto;
import ru.education.enums.PostType;

class ValidationEmployeeTest extends BaseEmployeeTest {

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
}
