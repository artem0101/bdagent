package ru.education.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import ru.education.dto.ClientDto;

import java.time.Instant;

class ClientControllerIntegrationTest extends BaseTest {

    @Test
    void findNonExistedClient() {
        var resultListById = getClient(333L, null, null, null, null, null, null, null, null, HttpStatus.OK.value());
        Assertions.assertNull(resultListById.getClients());

        var resultListByName = getClient(null, null, "dto.getName()", null, null,null, null, null, null, HttpStatus.OK.value());
        Assertions.assertNull(resultListByName.getClients());

        var resultListBySurname= getClient(null, "dto.getSurame()", null, null, null,null, null, null, null, HttpStatus.OK.value());
        Assertions.assertNull(resultListBySurname.getClients());

        var resultListByPatronymic = getClient(null, null, null, "dto.getPatronymic()",  null, null, null, null, null, HttpStatus.OK.value());
        Assertions.assertNull(resultListByPatronymic.getClients());

        var resultListByEmail = getClient(null, null, "dto.getEmail()", null, null,  null, null, null, null, HttpStatus.OK.value());
        Assertions.assertNull(resultListByEmail.getClients());

        var resultListByPhoneNumber = getClient(null, null, null, null,  null, "dto.getPhoneNumber()", null, null, null, HttpStatus.OK.value());
        Assertions.assertNull(resultListByPhoneNumber.getClients());

        var resultListByBirthday = getClient(null, null, null, null, null, null, Instant.now(), null, null, HttpStatus.OK.value());
        Assertions.assertNull(resultListByBirthday.getClients());
    }

    @Test
    void createClient() {
        var clientDto = new ClientDto(1L, "Климентов", "Клиент", "Клиентович", "test@test.ru", "899999", Instant.now());

        var resultListById = getClient(1L, null, null, null, null,  null, null, null, null, HttpStatus.OK.value());
        Assertions.assertNull(resultListById.getClients());

        var resultListByName = getClient(null, null, clientDto.getName(), null, null, null, null, null, null, HttpStatus.OK.value());
        Assertions.assertNull(resultListByName.getClients());

        var resultListBySurname = getClient(null, clientDto.getSurname(), null, null, null, null, null, null, null, HttpStatus.OK.value());
        Assertions.assertNull(resultListBySurname.getClients());

        var resultListByPatronymic = getClient(null, null, null, clientDto.getPatronymic(), null, null,null, null, null, HttpStatus.OK.value());
        Assertions.assertNull(resultListByPatronymic.getClients());

        var resultListByEmail = getClient(null, null, null, null, clientDto.getEmail(), null, null, null, null, HttpStatus.OK.value());
        Assertions.assertNull(resultListByEmail.getClients());

        var resultListByPhoneNumber = getClient(null, null, null, null,  null,clientDto.getPhoneNumber(), null, null, null, HttpStatus.OK.value());
        Assertions.assertNull(resultListByPhoneNumber.getClients());

        var resultListByBirthday = getClient(null, null, null, null, null, null, Instant.now(), null, null, HttpStatus.OK.value());
        Assertions.assertNull(resultListByBirthday.getClients());
    }

}