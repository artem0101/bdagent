package ru.education.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import ru.education.dto.ApartmentDto;
import ru.education.dto.paged.PagedApartmentsDto;
import ru.education.service.ApartmentService;

import java.math.BigDecimal;

@RestController
@RequestMapping({"api/v1/apartment"})
@RequiredArgsConstructor
public class ApartmentController {

    private final ApartmentService service;

    @PostMapping
    public ResponseEntity<Void> addApartment(@RequestBody @Validated ApartmentDto dto) {
        service.addNewApartment(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<PagedApartmentsDto> findApartments(
            @RequestParam(required = false) Long subjectId,
            @RequestParam(required = false) String country,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String postalCode,
            @RequestParam(required = false) String street,
            @RequestParam(required = false) String number,
            @RequestParam(required = false) BigDecimal price,
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) Integer floor,
            @RequestParam(required = false) Integer room,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "ASC") String sortDirection) {
        var direction = Sort.Direction.fromString(sortDirection.toLowerCase());
        var pageable = PageRequest.of(page, size, direction, sortBy);

        var result = service.findApartments(
                subjectId,
                country,
                city,
                postalCode,
                street,
                number,
                price,
                id,
                floor,
                room,
                pageable
        );

        return ResponseEntity.ok(result);
    }

    @DeleteMapping
    public void removeApartment(@RequestParam long id) {
        this.service.removeApartment(id);
    }

    @PutMapping
    public void updateApartment(@RequestBody ApartmentDto dto) {
        this.service.updateApartment(dto);
    }

}
