package ru.education.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import ru.education.dto.GroundDto;
import ru.education.service.GroundService;

import java.math.BigDecimal;
import java.util.Collection;

@RestController
@RequestMapping({"api/v1/ground"})
@RequiredArgsConstructor
public class GroundController {

    private final GroundService service;

    @PostMapping
    public void addGround(@RequestBody GroundDto dto) {
        this.service.addNewGround(dto);
    }

    @GetMapping
    public Collection<GroundDto> getApartments(
            @RequestParam(required = false) Long subjectId,
            @RequestParam(required = false) String country,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String postalCode,
            @RequestParam(required = false) String street,
            @RequestParam(required = false) String number,
            @RequestParam(required = false) BigDecimal price,
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) Double area) {
        return this.service.findGrounds(
                subjectId,
                country,
                city,
                postalCode,
                street,
                number,
                price,
                id,
                area
        );
    }

}
