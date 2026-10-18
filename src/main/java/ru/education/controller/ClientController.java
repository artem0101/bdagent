package ru.education.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.education.dto.ClientDto;
import ru.education.dto.paged.PagedClientsDto;
import ru.education.service.ClientService;

import java.time.Instant;

@RestController
@RequestMapping("/api/v1/client")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;

    @PostMapping
    public ResponseEntity<Void> addClient(@RequestBody @Validated ClientDto dto) {
        clientService.addNewClient(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<PagedClientsDto> findClients(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) String surname,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String patronymic,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String phoneNumber,
            @RequestParam(required = false) Instant birthday,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "ASC") String sortDirection) {
        
        if (!sortDirection.equalsIgnoreCase("ASC") && !sortDirection.equalsIgnoreCase("DESC")) {
            return ResponseEntity.badRequest().build();
        }
        
        var direction = Sort.Direction.fromString(sortDirection.toLowerCase());
        var pageable = PageRequest.of(page, size, direction, sortBy);
        var result = clientService.findClients(id, surname, name, patronymic, email, phoneNumber, birthday, pageable);

        return ResponseEntity.ok(result);
    }

}