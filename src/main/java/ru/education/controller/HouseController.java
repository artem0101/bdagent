package ru.education.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.education.dto.HouseDto;
import ru.education.dto.paged.PagedHousesDto;
import ru.education.service.HouseService;

import java.math.BigDecimal;

@RestController
@RequestMapping({"api/v1/house"})
@RequiredArgsConstructor
public class HouseController {

    private final HouseService service;

    @PostMapping
    public ResponseEntity<Void> addHouse(@RequestBody @Validated HouseDto dto) {
        service.addNewHouse(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<PagedHousesDto> findHouses(
            @RequestParam(required = false) Long subjectId,
            @RequestParam(required = false) String country,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String postalCode,
            @RequestParam(required = false) String street,
            @RequestParam(required = false) String number,
            @RequestParam(required = false) BigDecimal price,
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) Integer floors,
            @RequestParam(required = false) Integer rooms,
            @RequestParam(required = false) Double areaGround,
            @RequestParam(required = false) Double areaHouse,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "ASC") String sortDirection) {
        var direction = Sort.Direction.fromString(sortDirection.toLowerCase());
        var pageable = PageRequest.of(page, size, direction, sortBy);

        var result = service.findHouses(
                subjectId,
                country,
                city,
                postalCode,
                street,
                number,
                price,
                id,
                floors,
                rooms,
                areaGround,
                areaHouse,
                pageable
        );

        return ResponseEntity.ok(result);
    }

}
