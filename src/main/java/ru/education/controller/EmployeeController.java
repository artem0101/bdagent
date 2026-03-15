package ru.education.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.education.dto.EmployeeDto;
import ru.education.enums.PostType;
import ru.education.service.EmployeeService;

import java.util.Collection;

@RestController
@RequestMapping({"api/v1/employee"})
@RequiredArgsConstructor

public class EmployeeController {

    private final EmployeeService employeeService;

    @PostMapping
    public void addEmployee(@RequestBody EmployeeDto dto) {
        this.employeeService.addNewEmployee(dto);
    }

    @GetMapping
    public Collection<EmployeeDto> getAllEmployee(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) String surname,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String patronymic,
            @RequestParam(required = false) PostType post) {
        return this.employeeService.findEmployeesByParamsDto(id, surname, name, patronymic, post);
    }

}
