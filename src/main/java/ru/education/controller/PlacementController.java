package ru.education.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.education.dto.PlacementDto;
import ru.education.service.PlacementService;

import java.math.BigDecimal;
import java.util.Collection;

@RestController
@RequestMapping({"api/v1/placement"})
@RequiredArgsConstructor
public class PlacementController {

    private final PlacementService service;

    @PostMapping
    public void addPlacement(@RequestBody PlacementDto dto) {
        this.service.addNewPlacement(dto);
    }

    @GetMapping
    public Collection<PlacementDto> getPlacements(
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
            @RequestParam(required = false) Double area) {
        return this.service.findPlacements(
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
                area
        );
    }

}
