package ru.education.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.education.dto.SubjectDto;
import ru.education.service.SubjectService;

import java.math.BigDecimal;
import java.util.Collection;

@RestController
@RequestMapping({"api/v1/subject"})
@RequiredArgsConstructor
public class SubjectController {

    private final SubjectService service;

    @PostMapping
    public void addSubject(@RequestBody SubjectDto dto) {
        this.service.addNewSubject(dto);
    }

    @GetMapping
    public Collection<SubjectDto> getSubjects(
            @RequestParam(required = false) Long subjectId,
            @RequestParam(required = false) String country,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String postalCode,
            @RequestParam(required = false) String street,
            @RequestParam(required = false) String number,
            @RequestParam(required = false) BigDecimal price) {
        return this.service.findSubjects(
                subjectId,
                country,
                city,
                postalCode,
                street,
                number,
                price
        );
    }

}
