package ru.education.controller.client;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import ru.education.dto.ClientDto;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class UpdateClientTest extends BaseClientTest {

    @Test
    void shouldUpdateClient() {
        var initialDto = new ClientDto(
                null,
                "Сидоров",
                "Сидор",
                "Сидорович",
                "+79001234567",
                "sidorov@example.com",
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
                null,
                0,
                10,
                HttpStatus.OK.value()
        );
        assertFalse(searchResult.getClients().isEmpty());
        var clientId = searchResult.getClients().iterator().next().getId();

        var updateDto = new ClientDto(
                clientId,
                "Сидоров",
                "Сидор",
                "Сидорович",
                "+79001234568",
                "sidorov@example.com",
                Instant.now().minus(1000, ChronoUnit.DAYS),
                null
        );

        updateClient(updateDto, clientId, HttpStatus.NO_CONTENT.value());

        var updatedResult = getClient(
                clientId,
                null,
                null,
                null,
                null,
                null,
                null,
                0,
                10,
                HttpStatus.OK.value()
        );
        assertFalse(updatedResult.getClients().isEmpty());
        var updatedClient = updatedResult.getClients().iterator().next();

        assertEquals(updateDto.getId(), updatedClient.getId());
        assertEquals(updateDto.getSurname(), updatedClient.getSurname());
        assertEquals(updateDto.getName(), updatedClient.getName());
        assertEquals(updateDto.getPatronymic(), updatedClient.getPatronymic());
        assertEquals(updateDto.getEmail(), updatedClient.getEmail());
        assertEquals(updateDto.getPhoneNumber(), updatedClient.getPhoneNumber());
    }

    @Test
    void shouldUpdateOnlySomeFields() {
        var initialDto = new ClientDto(
                null,
                "Петров",
                "Петр",
                "Петрович",
                "+79001234567",
                "petrov@example.com",
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
                null,
                0,
                10,
                HttpStatus.OK.value()
        );
        assertFalse(searchResult.getClients().isEmpty());
        var clientId = searchResult.getClients().iterator().next().getId();

        var updateDto = new ClientDto(
                clientId,
                "Петров",
                "Павел",
                "Петрович",
                "+79001234568",
                "petrov@example.com",
                Instant.now().minus(1000, ChronoUnit.DAYS),
                null
        );

        updateClient(updateDto, clientId, HttpStatus.NO_CONTENT.value());

        var updatedResult = getClient(
                clientId,
                null,
                null,
                null,
                null,
                null,
                null,
                0,
                10,
                HttpStatus.OK.value()
        );
        assertFalse(updatedResult.getClients().isEmpty());
        var updatedClient = updatedResult.getClients().iterator().next();

        assertEquals(updateDto.getId(), updatedClient.getId());
        assertEquals("Павел", updatedClient.getName());
        assertEquals("+79001234568", updatedClient.getPhoneNumber());
    }

    @Test
    void shouldReturnNotFoundWhenUpdatingNonExistingClient() {
        var updateDto = new ClientDto(
                99999L,
                "Иванов",
                "Иван",
                "Иванович",
                "+79001234567",
                "ivan@example.com",
                Instant.now().minus(1000, ChronoUnit.DAYS),
                null
        );

        updateClient(updateDto, 99999L, HttpStatus.NOT_FOUND.value());
    }

    @Test
    void shouldHandleEmptySurnameDuringUpdate() {
        var initialDto = new ClientDto(
                null,
                "Смирнов",
                "Сергей",
                "Сергеевич",
                "+79001234567",
                "smirnov@example.com",
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
                null,
                0,
                10,
                HttpStatus.OK.value()
        );
        assertFalse(searchResult.getClients().isEmpty());
        var clientId = searchResult.getClients().iterator().next().getId();

        var updateDto = new ClientDto(
                clientId,
                "",
                "Сергей",
                "Сергеевич",
                "+79001234567",
                "smirnov@example.com",
                Instant.now().minus(1000, ChronoUnit.DAYS),
                null
        );

        updateClient(updateDto, clientId, HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void shouldHandleEmptyNameDuringUpdate() {
        var initialDto = new ClientDto(
                null,
                "Кузнецов",
                "Константин",
                "Константинович",
                "+79001234567",
                "kuznetsov@example.com",
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
                null,
                0,
                10,
                HttpStatus.OK.value()
        );
        assertFalse(searchResult.getClients().isEmpty());
        var clientId = searchResult.getClients().iterator().next().getId();

        var updateDto = new ClientDto(
                clientId,
                "Кузнецов",
                "",
                "Константинович",
                "+79001234567",
                "kuznetsov@example.com",
                Instant.now().minus(1000, ChronoUnit.DAYS),
                null
        );

        updateClient(updateDto, clientId, HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void shouldHandleEmptyPatronymicDuringUpdate() {
        var initialDto = new ClientDto(
                null,
                "Попов",
                "Алексей",
                "Алексеевич",
                "+79001234567",
                "popov@example.com",
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
                null,
                0,
                10,
                HttpStatus.OK.value()
        );
        assertFalse(searchResult.getClients().isEmpty());
        var clientId = searchResult.getClients().iterator().next().getId();

        var updateDto = new ClientDto(
                clientId,
                "Попов",
                "Алексей",
                "",
                "+79001234567",
                "popov@example.com",
                Instant.now().minus(1000, ChronoUnit.DAYS),
                null
        );

        updateClient(updateDto, clientId, HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void shouldHandleEmptyEmailDuringUpdate() {
        var initialDto = new ClientDto(
                null,
                "Соколов",
                "Михаил",
                "Михайлович",
                "+79001234567",
                "sokolov@example.com",
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
                null,
                0,
                10,
                HttpStatus.OK.value()
        );
        assertFalse(searchResult.getClients().isEmpty());
        var clientId = searchResult.getClients().iterator().next().getId();

        var updateDto = new ClientDto(
                clientId,
                "Соколов",
                "Михаил",
                "Михайлович",
                "+79001234567",
                "",
                Instant.now().minus(1000, ChronoUnit.DAYS),
                null
        );

        updateClient(updateDto, clientId, HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void shouldHandleEmptyPhoneNumberDuringUpdate() {
        var initialDto = new ClientDto(
                null,
                "Васильев",
                "Николай",
                "Николаевич",
                "+79001234567",
                "vasilev@example.com",
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
                null,
                0,
                10,
                HttpStatus.OK.value()
        );
        assertFalse(searchResult.getClients().isEmpty());
        var clientId = searchResult.getClients().iterator().next().getId();

        var updateDto = new ClientDto(
                clientId,
                "Васильев",
                "Николай",
                "Николаевич",
                "",
                "vasilev@example.com",
                Instant.now().minus(1000, ChronoUnit.DAYS),
                null
        );

        updateClient(updateDto, clientId, HttpStatus.BAD_REQUEST.value());
    }
}
