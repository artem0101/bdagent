package ru.education.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.education.dto.HouseDto;
import ru.education.service.HouseService;

import java.math.BigDecimal;
import java.util.Collection;

@RestController
@RequestMapping({"api/v1/house"})
@RequiredArgsConstructor
public class HouseController {

    private final HouseService service;

    @PostMapping
    public void addHouse(@RequestBody HouseDto dto) {
        this.service.addNewHouse(dto);
    }

    @GetMapping
    public Collection<HouseDto> getHouses(
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
            @RequestParam(required = false) Double areaHouse) {
        return this.service.findHouses(
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
                areaHouse
        );
    }

}
