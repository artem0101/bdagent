package ru.education.controller.client;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import ru.education.dto.ClientDto;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DeleteClientTest extends BaseClientTest {

    @Test
    void shouldDeleteClientAndExcludeFromSearch() {
        var initialDto = new ClientDto(
                null,
                "Морозов",
                "Максим",
                "Максимович",
                "+79001234567",
                "maksim@example.com",
                Instant.now().minus(1000, ChronoUnit.DAYS),
                null
        );
        createClient(initialDto, HttpStatus.CREATED.value());

        var searchResultBeforeDelete = getClient(
                null,
                initialDto.getSurname(),
                null,
                null,
                null,
                null,
                null,
                0,
                10,
                HttpStatus.OK.value()
        );
        assertFalse(searchResultBeforeDelete.getClients().isEmpty());
        var clientId = searchResultBeforeDelete.getClients().iterator().next().getId();

        deleteClient(clientId, HttpStatus.ACCEPTED.value());

        var searchResultAfterDelete = getClient(
                null,
                initialDto.getSurname(),
                null,
                null,
                null,
                null,
                null,
                0,
                10,
                HttpStatus.OK.value()
        );
        assertNull(searchResultAfterDelete.getClients());

        searchResultAfterDelete = getClient(
                null,
                initialDto.getSurname(),
                null,
                null,
                null,
                null,
                true,
                0,
                10,
                HttpStatus.OK.value()
        );
        assertNotNull(searchResultAfterDelete.getClients());
        assertFalse(searchResultAfterDelete.getClients().isEmpty());

        var searchAllResult = getClient(
                clientId,
                null,
                null,
                null,
                null,
                null,
                true,
                0,
                10,
                HttpStatus.OK.value()
        );
        assertTrue(searchAllResult.getClients().stream()
                .anyMatch(c -> c.getId() != null && c.getId().equals(clientId)));
    }

    @Test
    void shouldNotUpdateDeletedClient() {
        var initialDto = new ClientDto(
                null,
                "Новиков",
                "Дмитрий",
                "Дмитриевич",
                "+79001234567",
                "dmitry@example.com",
                Instant.now().minus(1000, ChronoUnit.DAYS),
                null
        );
        createClient(initialDto, HttpStatus.CREATED.value());

        var searchResult = getClient(
                null,
                initialDto.getSurname(),
                null,
                null,
                null,
                null,
                false,
                0,
                10,
                HttpStatus.OK.value()
        );
        assertFalse(searchResult.getClients().isEmpty());
        var clientId = searchResult.getClients().iterator().next().getId();

        deleteClient(clientId, HttpStatus.ACCEPTED.value());

        var updateDto = new ClientDto(
                clientId,
                "Новиков",
                "Дмитрий",
                "Дмитриевич",
                "+79001234568",
                "dmitry@example.com",
                Instant.now().minus(1000, ChronoUnit.DAYS),
                null
        );

        updateClient(updateDto, clientId, HttpStatus.NOT_FOUND.value());
    }
}
