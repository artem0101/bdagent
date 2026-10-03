package ru.education.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.education.dto.ClientDto;
import ru.education.dto.paged.PagedClientsDto;
import ru.education.service.ClientService;

import java.time.Instant;

@RestController
@RequestMapping({"api/v1/client"})
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;

    @PostMapping
    public void addClient(@RequestBody ClientDto dto) {
        this.clientService.addNewClient(dto);
    }

    @GetMapping
    public PagedClientsDto findClients(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) String surname,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String patronymic,
            @RequestParam(required = false) Instant birthday,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "ASC") String sortDirection) {
        var direction = Sort.Direction.fromString(sortDirection);
        var pageable = PageRequest.of(page, size, direction, sortBy);

        return clientService.findClients(id, surname, name, patronymic, birthday, pageable);
    }

}