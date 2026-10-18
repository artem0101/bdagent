package ru.education.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import ru.education.dto.ClientDto;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.nullValue;

class ClientControllerIntegrationTest extends BaseTest {

    @Test
    void shouldCreateClientAndReturnCreatedStatus() {
        var dto = new ClientDto(
                null,
                "Тестов",
                "Тест",
                "Тестович",
                "+79001234567",
                "test@example.com",
                Instant.now().minus(1000, ChronoUnit.DAYS)
        );

        var response = given(requestSpecification)
                .body(dto)
                .post(CLIENT_URL);

        response.then()
                .statusCode(HttpStatus.CREATED.value())
                .header("Content-Type", nullValue());
    }

    @Test
    void shouldFindClientById() {
        var dto = new ClientDto(
                null,
                "Михайлов",
                "МИхаил",
                "михайлович",
                "+79001234568",
                "test@example.com",
                Instant.now().minus(1000, ChronoUnit.DAYS)
        );
        createClient(dto, HttpStatus.CREATED.value());

        var resultByName = getClient(
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
        ).getClients().stream().findFirst();

        var result = getClient(
                resultByName.get().getId(),
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

        Assertions.assertNotNull(result);
        Assertions.assertNotNull(result.getClients());
        Assertions.assertFalse(result.getClients().isEmpty());

        var client = result.getClients().iterator().next();
        Assertions.assertEquals(dto.getSurname(), client.getSurname());
        Assertions.assertEquals(dto.getName(), client.getName());
        Assertions.assertEquals(dto.getPatronymic(), client.getPatronymic());
        Assertions.assertEquals(dto.getEmail(), client.getEmail());
        Assertions.assertEquals(dto.getPhoneNumber(), client.getPhoneNumber());
    }

    @Test
    void shouldFindClientsByName() {
        var dto = new ClientDto(
                null,
                "Стасов",
                "Стас",
                "Станиславович",
                "+79001234569",
                "test@example.com",
                Instant.now().minus(1000, ChronoUnit.DAYS)
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

        Assertions.assertNotNull(result);
        Assertions.assertNotNull(result.getClients());
        Assertions.assertFalse(result.getClients().isEmpty());

        var client = result.getClients().iterator().next();
        Assertions.assertEquals(dto.getName(), client.getName());
    }

    @Test
    void shouldFindClientsBySurname() {
        var dto = new ClientDto(
                null,
                "Антонов",
                "Антон",
                "Антонович",
                "+79001234570",
                "test@example.com",
                Instant.now().minus(1000, ChronoUnit.DAYS)
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

        Assertions.assertNotNull(result);
        Assertions.assertNotNull(result.getClients());
        Assertions.assertFalse(result.getClients().isEmpty());

        var client = result.getClients().iterator().next();
        Assertions.assertEquals(dto.getSurname(), client.getSurname());
    }

    @Test
    void shouldFindClientsByPatronymic() {
        var dto = new ClientDto(
                null,
                "Юрьев",
                "Юрий",
                "Юрьевич",
                "+79001234571",
                "test@example.com",
                Instant.now().minus(1000, ChronoUnit.DAYS)
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

        Assertions.assertNotNull(result);
        Assertions.assertNotNull(result.getClients());
        Assertions.assertFalse(result.getClients().isEmpty());

        var client = result.getClients().iterator().next();
        Assertions.assertEquals(dto.getPatronymic(), client.getPatronymic());
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
                Instant.now().minus(1000, ChronoUnit.DAYS)
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

        Assertions.assertNotNull(result);
        Assertions.assertNotNull(result.getClients());
        Assertions.assertFalse(result.getClients().isEmpty());

        var client = result.getClients().iterator().next();
        Assertions.assertEquals(dto.getEmail(), client.getEmail());
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
                Instant.now().minus(1000, ChronoUnit.DAYS)
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

        Assertions.assertNotNull(result);
        Assertions.assertNotNull(result.getClients());
        Assertions.assertFalse(result.getClients().isEmpty());

        var client = result.getClients().iterator().next();
        Assertions.assertEquals(dto.getPhoneNumber(), client.getPhoneNumber());
    }

    @Test
    void shouldFindClientsByBirthday() {
        var dto = new ClientDto(
                null,
                "Сергеев",
                "Сергей",
                "Сергеевич",
                "+79001234574",
                "gtest@example.com",
                Instant.now().minus(2000, ChronoUnit.DAYS)
        );
        createClient(dto, HttpStatus.CREATED.value());

        var result = getClient(
                null, 
                null,
                null,
                null,
                null,
                null,
                dto.getBirthday(),
                0,
                10,
                HttpStatus.OK.value()
        );

        Assertions.assertNotNull(result);
        Assertions.assertNotNull(result.getClients());
        Assertions.assertFalse(result.getClients().isEmpty());
        var correctBirthDay = dto.getBirthday().atZone(ZoneOffset.UTC).toLocalDate();
        dto.setBirthday(correctBirthDay.atStartOfDay(ZoneOffset.UTC).toInstant());

        var client = result.getClients().iterator().next();
        Assertions.assertEquals(
                dto.getBirthday().truncatedTo(java.time.temporal.ChronoUnit.MICROS),
            client.getBirthday().truncatedTo(java.time.temporal.ChronoUnit.MICROS)
        );
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

        Assertions.assertNotNull(result);
        Assertions.assertNull(result.getClients());
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
                Instant.now().minus(365, ChronoUnit.DAYS)
        );
        var client2 = new ClientDto(
                null,
                "Петров",
                "Петр",
                "Петрович",
                "+79002222222",
                "petr@example.com",
                Instant.now().minus(365, ChronoUnit.DAYS)
        );
        var client3 = new ClientDto(
                null,
                "Смирнов",
                "Сергей",
                "Сергеевич",
                "+79003333333",
                "sergey@example.com",
                Instant.now().minus(365, ChronoUnit.DAYS)
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


        Assertions.assertNotNull(resultPage1);
        Assertions.assertNotNull(resultPage1.getClients());
        Assertions.assertEquals(2, resultPage1.getSize());
        Assertions.assertEquals(0, resultPage1.getPage());

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

        Assertions.assertNotNull(resultPage2);
        Assertions.assertNotNull(resultPage2.getClients());
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
                Instant.now().minus(365, ChronoUnit.DAYS)
        );
        var client2 = new ClientDto(
                null,
                "Борисов",
                "Борис",
                "Борисович",
                "+79002222222",
                "boris@example.com",
                Instant.now().minus(365, ChronoUnit.DAYS)
        );
        var client3 = new ClientDto(
                null,
                "Васильев",
                "Василий",
                "Васильевич",
                "+79003333333",
                "vasily@example.com",
                Instant.now().minus(365, ChronoUnit.DAYS)
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
                "ASC",
                HttpStatus.OK.value()
        );

        Assertions.assertNotNull(resultAsc);
        Assertions.assertNotNull(resultAsc.getClients());
        
        var clientsAsc = new ArrayList<>(resultAsc.getClients());
        var firstCharOfFirstClient = clientsAsc.getFirst().getSurname().toCharArray()[0];
        var firstCharOfLastClient = clientsAsc.getLast().getSurname().toCharArray()[0];

        Assertions.assertTrue(firstCharOfLastClient > firstCharOfFirstClient);

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
                "DESC",
                HttpStatus.OK.value()
        );

        Assertions.assertNotNull(resultDesc);
        Assertions.assertNotNull(resultDesc.getClients());
        
        var clientsDesc = new ArrayList<>(resultDesc.getClients());
        firstCharOfFirstClient = clientsDesc.getFirst().getSurname().toCharArray()[0];
        firstCharOfLastClient = clientsDesc.getLast().getSurname().toCharArray()[0];

        Assertions.assertTrue(firstCharOfLastClient < firstCharOfFirstClient);
    }

    @Test
    void shouldCreateClientWithAllFields() {
        var clientDto = new ClientDto(
                null,
                "Иванов",
                "Иван",
                "Иванович",
                "+79001234567",
                "ivan@example.com",
                Instant.now().minus(3000, ChronoUnit.DAYS)
        );

        createClient(clientDto, HttpStatus.CREATED.value());

        var result = getClient(
                null,
                clientDto.getSurname(),
                clientDto.getName(),
                clientDto.getPatronymic(),
                clientDto.getEmail(),
                clientDto.getPhoneNumber(),
                clientDto.getBirthday(),
                0, 10,
                HttpStatus.OK.value()
        );

        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.getClients().size());

        var correctBirthDay = clientDto.getBirthday().atZone(ZoneOffset.UTC).toLocalDate();
        clientDto.setBirthday(correctBirthDay.atStartOfDay(ZoneOffset.UTC).toInstant());

        var createdClient = result.getClients().iterator().next();
        Assertions.assertEquals(clientDto.getSurname(), createdClient.getSurname());
        Assertions.assertEquals(clientDto.getName(), createdClient.getName());
        Assertions.assertEquals(clientDto.getPatronymic(), createdClient.getPatronymic());
        Assertions.assertEquals(clientDto.getEmail(), createdClient.getEmail());
        Assertions.assertEquals(clientDto.getPhoneNumber(), createdClient.getPhoneNumber());
        Assertions.assertEquals(clientDto.getBirthday(), createdClient.getBirthday());
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


        Assertions.assertNotNull(result);
        Assertions.assertNotNull(result.getClients());
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
                Instant.now().minus(100, ChronoUnit.DAYS)
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

        Assertions.assertNotNull(result);
        Assertions.assertEquals(0, result.getPage());
        Assertions.assertEquals(5, result.getSize());
    }

    @Test
    void shouldFindClientByCombinedParameters() {
        var clientDto = new ClientDto(
                null,
                "Петров",
                "Пётр",
                "Петрович",
                "+79001234598",
                "ivan@example.com",
                Instant.now().minus(3000, ChronoUnit.DAYS)
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

        Assertions.assertNotNull(result);
        Assertions.assertFalse(result.getClients().isEmpty());

        var client = result.getClients().iterator().next();
        Assertions.assertEquals(clientDto.getSurname(), client.getSurname());
        Assertions.assertEquals(clientDto.getName(), client.getName());
    }

    @Test
    void incorrectSurnameTest() {
        var clientDto = new ClientDto(
                null,
                "",
                "Иван",
                "Иванович",
                "+79001234567",
                "ivan@example.com",
                Instant.now().minus(3000, ChronoUnit.DAYS)
        );

        createClient(clientDto, HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void incorrectNameTest() {
        var clientDto = new ClientDto(
                null,
                "Ivanov",
                "",
                "Иванович",
                "+79001234567",
                "ivan@example.com",
                Instant.now().minus(3000, ChronoUnit.DAYS)
        );

        createClient(clientDto, HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void incorrectPatronimycTest() {
        var clientDto = new ClientDto(
                null,
                "Ivanov",
                "Иван",
                "",
                "+79001234567",
                "ivan@example.com",
                Instant.now().minus(3000, ChronoUnit.DAYS)
        );

        createClient(clientDto, HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void incorrectPhoneTest() {
        var clientDto = new ClientDto(
                null,
                "Ivanov",
                "Иван",
                "Иванович",
                "+79001234567ssd",
                "ivan@example.com",
                Instant.now().minus(3000, ChronoUnit.DAYS)
        );

        createClient(clientDto, HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void incorrectEmailTest() {
        var clientDto = new ClientDto(
                null,
                "Ivanov",
                "Иван",
                "Иванович",
                "+79001234567",
                "ivanexample.com",
                Instant.now().minus(3000, ChronoUnit.DAYS)
        );

        createClient(clientDto, HttpStatus.BAD_REQUEST.value());
    }

}
