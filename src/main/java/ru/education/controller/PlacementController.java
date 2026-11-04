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
import ru.education.dto.PlacementDto;
import ru.education.dto.paged.PagedPlacementsDto;
import ru.education.service.PlacementService;

import java.math.BigDecimal;

@RestController
@RequestMapping({"api/v1/placement"})
@RequiredArgsConstructor
public class PlacementController {

    private final PlacementService service;

    @PostMapping
    public ResponseEntity<Void> addPlacement(@RequestBody @Validated PlacementDto dto) {
        service.addNewPlacement(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<PagedPlacementsDto> findPlacements(
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
            @RequestParam(required = false) Double area,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "ASC") String sortDirection) {
        var direction = Sort.Direction.fromString(sortDirection.toLowerCase());
        var pageable = PageRequest.of(page, size, direction, sortBy);

        var result = service.findPlacements(
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
                area,
                pageable
        );

        return ResponseEntity.ok(result);
    }

}
