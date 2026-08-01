package ru.education.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import ru.education.dto.ApartmentDto;
import ru.education.service.ApartmentService;

import java.math.BigDecimal;
import java.util.Collection;

@RestController
@RequestMapping({"api/v1/apartment"})
@RequiredArgsConstructor
public class ApartmentController {

    private final ApartmentService service;

    @PostMapping
    public void addApartment(@RequestBody ApartmentDto dto) {
        this.service.addNewApartment(dto);
    }

    @GetMapping
    public Collection<ApartmentDto> getApartments(
            @RequestParam(required = false) Long subjectId,
            @RequestParam(required = false) String country,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String postalCode,
            @RequestParam(required = false) String street,
            @RequestParam(required = false) String number,
            @RequestParam(required = false) BigDecimal price,
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) Integer floor,
            @RequestParam(required = false) Integer room) {
        return this.service.findApartments(
                subjectId,
                country,
                city,
                postalCode,
                street,
                number,
                price,
                id,
                floor,
                room
        );
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
