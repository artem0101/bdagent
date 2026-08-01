package ru.education.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.education.dto.EmployeeDto;
import ru.education.dto.PagedEmployeesDto;
import ru.education.enums.PostType;
import ru.education.service.EmployeeService;

@RestController
@RequestMapping({"api/v1/employee"})
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService service;

    @PostMapping
    public ResponseEntity<EmployeeDto> addEmployee(@RequestBody @Validated EmployeeDto dto) {
        EmployeeDto createdEmployee = service.addNewEmployee(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdEmployee);
    }

    @GetMapping
    public PagedEmployeesDto findEmployees(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) String surname,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String patronymic,
            @RequestParam(required = false) PostType post,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "ASC") String sortDirection) {

        var direction = Sort.Direction.fromString(sortDirection);
        var pageable = PageRequest.of(page, size, direction, sortBy);
        return service.findEmployees(id, surname, name, patronymic, post, pageable);
    }

}
