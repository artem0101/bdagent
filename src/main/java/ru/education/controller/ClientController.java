package ru.education.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.education.dto.ClientDto;
import ru.education.service.ClientService;

@RestController
@RequestMapping({"api/v1/client"})
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;

    @PostMapping
    public void addCLient(@RequestBody ClientDto dto) {
        this.clientService.addNewClient(dto);
    }

}