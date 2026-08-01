package ru.education.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.education.dto.ClientDto;
import ru.education.service.ClientService;

import java.time.Instant;
import java.util.Collection;

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
    public Collection<ClientDto> findClients(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) String surname,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String patronymic,
            @RequestParam(required = false) Instant birthday) {
        return clientService.findClients(id, surname, name, patronymic, birthday);
    }

}