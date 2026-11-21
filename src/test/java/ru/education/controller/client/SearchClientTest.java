package ru.education.controller.client;

import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import ru.education.dto.ClientDto;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SearchClientTest extends BaseClientTest {

    @Test
    void shouldFindClientById() {
        var dto = new ClientDto(
                null,
                "Михайлов",
                "Михаил",
                "Михайлович",
                "+79001234568",
                "test@example.com",
                Instant.now().minus(1000, ChronoUnit.DAYS),
                false
        );
        createClient(dto, HttpStatus.CREATED.value());

        var resultBySurname = getClient(
                null,
                dto.getSurname(),
                null,
                null,
                null,
                null,
                null,
                0,
                10,
                HttpStatus.OK.value()
        );

        assertNotNull(resultBySurname);
        assertNotNull(resultBySurname.getClients());
        assertFalse(resultBySurname.getClients().isEmpty());

        var client = resultBySurname.getClients().iterator().next();
        assertEquals(dto.getSurname(), client.getSurname());
        assertEquals(dto.getName(), client.getName());
        assertEquals(dto.getPatronymic(), client.getPatronymic());
        assertEquals(dto.getEmail(), client.getEmail());
        assertEquals(dto.getPhoneNumber(), client.getPhoneNumber());

        var id = client.getId();

        var resultById = getClient(
                id,
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
        assertNotNull(resultById);
        assertNotNull(resultById.getClients());
        assertFalse(resultById.getClients().isEmpty());

        var clientById = resultById.getClients().iterator().next();
        assertEquals(dto.getSurname(), clientById.getSurname());
        assertEquals(dto.getName(), clientById.getName());
        assertEquals(dto.getPatronymic(), clientById.getPatronymic());
        assertEquals(dto.getEmail(), clientById.getEmail());
        assertEquals(dto.getPhoneNumber(), clientById.getPhoneNumber());
    }

    @Test
    void shouldFindClientsBySurname() {
        var dto = new ClientDto(
                null,
                "Стасов",
                "Стас",
                "Станиславович",
                "+79001234569",
                "test@example.com",
                Instant.now().minus(1000, ChronoUnit.DAYS),
                false
        );
        createClient(dto, HttpStatus.CREATED.value());

        var result = getClient(
                null,
                dto.getSurname(),
                null,
                null,
                null,
                null,
                null,
                0,
                10,
                HttpStatus.OK.value()
        );

        assertNotNull(result);
        assertNotNull(result.getClients());
        assertFalse(result.getClients().isEmpty());

        var client = result.getClients().iterator().next();
        assertEquals(dto.getSurname(), client.getSurname());
    }

    @Test
    void shouldFindClientsByName() {
        var dto = new ClientDto(
                null,
                "Юрьев",
                "Юрий",
                "Юрьевич",
                "+79001234570",
                "test@example.com",
                Instant.now().minus(1000, ChronoUnit.DAYS),
                false
        );
        createClient(dto, HttpStatus.CREATED.value());

        var result = getClient(
                null,
                null,
                dto.getName(),
                null,
                null,
                null,
                null,
                0,
                10,
                HttpStatus.OK.value()
        );

        assertNotNull(result);
        assertNotNull(result.getClients());
        assertFalse(result.getClients().isEmpty());

        var client = result.getClients().iterator().next();
        assertEquals(dto.getName(), client.getName());
    }

    @Test
    void shouldFindClientsByPatronymic() {
        var dto = new ClientDto(
                null,
                "Антонов",
                "Антон",
                "Антонович",
                "+79001234571",
                "test@example.com",
                Instant.now().minus(1000, ChronoUnit.DAYS),
                false
        );
        createClient(dto, HttpStatus.CREATED.value());

        var result = getClient(
                null,
                null,
                null,
                dto.getPatronymic(),
                null,
                null,
                null,
                0,
                10,
                HttpStatus.OK.value()
        );

        assertNotNull(result);
        assertNotNull(result.getClients());
        assertFalse(result.getClients().isEmpty());

        var client = result.getClients().iterator().next();
        assertEquals(dto.getPatronymic(), client.getPatronymic());
    }

    @Test
    void shouldFindClientsByEmail() {
        var dto = new ClientDto(
                null,
                "Гришин",
                "Григорий",
                "Григорьевич",
                "+79001234572",
                "gtest@example.com",
                Instant.now().minus(1000, ChronoUnit.DAYS),
                false
        );
        createClient(dto, HttpStatus.CREATED.value());

        var result = getClient(
                null,
                null,
                null,
                null,
                dto.getEmail(),
                null,
                null,
                0,
                10,
                HttpStatus.OK.value()
        );

        assertNotNull(result);
        assertNotNull(result.getClients());
        assertFalse(result.getClients().isEmpty());

        var client = result.getClients().iterator().next();
        assertEquals(dto.getEmail(), client.getEmail());
    }

    @Test
    void shouldFindClientsByPhoneNumber() {
        var dto = new ClientDto(
                null,
                "Иванов",
                "Иван",
                "Иванович",
                "+79001234573",
                "gtest@example.com",
                Instant.now().minus(1000, ChronoUnit.DAYS),
                false
        );
        createClient(dto, HttpStatus.CREATED.value());

        var result = getClient(
                null,
                null,
                null,
                null,
                null,
                dto.getPhoneNumber(),
                null,
                0,
                10,
                HttpStatus.OK.value()
        );

        assertNotNull(result);
        assertNotNull(result.getClients());
        assertFalse(result.getClients().isEmpty());

        var client = result.getClients().iterator().next();
        assertEquals(dto.getPhoneNumber(), client.getPhoneNumber());
    }

    @Test
    void shouldReturnEmptyListWhenNoClientsFound() {
        var result = getClient(
                99999L,
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

        assertNotNull(result);
        assertNull(result.getClients());
    }

    @Test
    void shouldSupportPagination() {
        var client1 = new ClientDto(
                null,
                "Сидоров",
                "Иван",
                "Иванович",
                "+79001111111",
                "ivan@example.com",
                Instant.now().minus(365, ChronoUnit.DAYS),
                false
        );
        var client2 = new ClientDto(
                null,
                "Петров",
                "Петр",
                "Петрович",
                "+79002222222",
                "petr@example.com",
                Instant.now().minus(365, ChronoUnit.DAYS),
                false
        );
        var client3 = new ClientDto(
                null,
                "Смирнов",
                "Сергей",
                "Сергеевич",
                "+79003333333",
                "sergey@example.com",
                Instant.now().minus(365, ChronoUnit.DAYS),
                false
        );

        createClient(client1, HttpStatus.CREATED.value());
        createClient(client2, HttpStatus.CREATED.value());
        createClient(client3, HttpStatus.CREATED.value());

        var resultPage1 = getClient(
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                0,
                2,
                HttpStatus.OK.value()
        );

        assertNotNull(resultPage1);
        assertNotNull(resultPage1.getClients());
        assertEquals(2, resultPage1.getSize());
        assertEquals(0, resultPage1.getPage());

        var resultPage2 = getClient(
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                1,
                2,
                HttpStatus.OK.value()
        );

        assertNotNull(resultPage2);
        assertNotNull(resultPage2.getClients());
    }

    @Test
    void shouldSupportSorting() {
        var client1 = new ClientDto(
                null,
                "Алексеев",
                "Алексей",
                "Алексеевич",
                "+79001111111",
                "alex@example.com",
                Instant.now().minus(365, ChronoUnit.DAYS),
                false
        );
        var client2 = new ClientDto(
                null,
                "Борисов",
                "Борис",
                "Борисович",
                "+79002222222",
                "boris@example.com",
                Instant.now().minus(365, ChronoUnit.DAYS),
                false
        );
        var client3 = new ClientDto(
                null,
                "Васильев",
                "Василий",
                "Васильевич",
                "+79003333333",
                "vasily@example.com",
                Instant.now().minus(365, ChronoUnit.DAYS),
                false
        );

        createClient(client1, HttpStatus.CREATED.value());
        createClient(client2, HttpStatus.CREATED.value());
        createClient(client3, HttpStatus.CREATED.value());

        var resultAsc = getClient(
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                0,
                10,
                "surname",
                Sort.Direction.ASC.toString(),
                HttpStatus.OK.value()
        );

        assertNotNull(resultAsc);
        assertNotNull(resultAsc.getClients());

        var clientsAsc = new ArrayList<>(resultAsc.getClients());
        var firstCharOfFirstClient = clientsAsc.getFirst().getSurname().toCharArray()[0];
        var firstCharOfLastClient = clientsAsc.getLast().getSurname().toCharArray()[0];

        assertTrue(firstCharOfLastClient > firstCharOfFirstClient);

        var resultDesc = getClient(
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                0,
                10,
                "surname",
                Sort.Direction.DESC.toString(),
                HttpStatus.OK.value()
        );

        assertNotNull(resultDesc);
        assertNotNull(resultDesc.getClients());

        var clientsDesc = new ArrayList<>(resultDesc.getClients());
        firstCharOfFirstClient = clientsDesc.getFirst().getSurname().toCharArray()[0];
        firstCharOfLastClient = clientsDesc.getLast().getSurname().toCharArray()[0];

        assertTrue(firstCharOfLastClient < firstCharOfFirstClient);
    }

    @Test
    void shouldReturnCorrectPageInfo() {
        var clientDto = new ClientDto(
                null,
                "Тестов",
                "Тест",
                "Тестович",
                "+79001234567",
                "test@example.com",
                Instant.now().minus(100, ChronoUnit.DAYS),
                false
        );
        createClient(clientDto, HttpStatus.CREATED.value());

        var result = getClient(
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                0,
                5,
                HttpStatus.OK.value()
        );

        assertNotNull(result);
        assertEquals(0, result.getPage());
        assertEquals(5, result.getSize());
    }

    @Test
    void shouldFindClientsByCombinedParameters() {
        var clientDto = new ClientDto(
                null,
                "Петров",
                "Пётр",
                "Петрович",
                "+79001234598",
                "ivan@example.com",
                Instant.now().minus(3000, ChronoUnit.DAYS),
                false
        );
        createClient(clientDto, HttpStatus.CREATED.value());

        var result = getClient(
                null,
                clientDto.getSurname(),
                clientDto.getName(),
                null,
                null,
                null,
                null,
                0,
                10,
                HttpStatus.OK.value()
        );

        assertNotNull(result);
        assertFalse(result.getClients().isEmpty());

        var client = result.getClients().iterator().next();
        assertEquals(clientDto.getSurname(), client.getSurname());
        assertEquals(clientDto.getName(), client.getName());
    }

    @Test
    void shouldHandleEmptySearchParameters() {
        var result = getClient(
                null,
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

        assertNotNull(result);
        assertNotNull(result.getClients());
    }

    @Test
    void shouldFindDeletedClientWithIsDeletedTrue() {
        var initialDto = new ClientDto(
                null,
                "Волков",
                "Андрей",
                "Андреевич",
                "+79001234567",
                "andrey@example.com",
                Instant.now().minus(1000, ChronoUnit.DAYS),
                false
        );
        createClient(initialDto, HttpStatus.CREATED.value());

        var searchResultBeforeDelete = getClient(
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
        assertFalse(searchResultBeforeDelete.getClients().isEmpty());
        var clientId = searchResultBeforeDelete.getClients().iterator().next().getId();

        deleteClient(clientId, HttpStatus.ACCEPTED.value());

        var deletedClientResult = getClient(
                clientId,
                null,
                null,
                null,
                null,
                null,
                true,
                0,
                10,
                "isDeleted",
                Sort.Direction.DESC.toString(),
                HttpStatus.OK.value()
        );
        assertNotNull(deletedClientResult.getClients());
        assertTrue(deletedClientResult.getClients().stream()
                .anyMatch(c -> c.getId() != null && c.getId().equals(clientId) && c.getIsDeleted()));
    }
}
