package ru.education.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.http.HttpStatus;
import ru.education.dto.ClientDto;
import ru.education.dto.GroundDto;

import ru.education.enums.SubjectType;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

import static io.restassured.RestAssured.given;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class GroundControllerIntegrationTest extends BaseTest {

    private Long clientId;

    @BeforeEach
    void setUp() {
        var dto = new ClientDto(
                null,
                "Тихонов",
                "Тихон",
                "Тихонович",
                "+79101234568",
                "test@example.com",
                Instant.now().minus(1000, ChronoUnit.DAYS)
        );
        createClient(dto, HttpStatus.CREATED.value());

        clientId = getClient(
                null,
                dto.getSurname(),
                dto.getName(),
                dto.getPatronymic(),
                null,
                null,
                null,
                0,
                10,
                HttpStatus.OK.value()
        ).getClients().stream().findFirst().get().getId();
    }

    @Test
    void shouldCreateGroundAndReturnCreatedStatus() {
        var dto = GroundDto.builder()
                .id(0)
                .country("Тестовая земля")
                .city("Москва")
                .postalCode("123456")
                .street("Тестовая")
                .number("1")
                .price(BigDecimal.valueOf(1000000))
                .isActive(true)
                .type(SubjectType.GROUND.name())
                .ownerId(clientId)
                .build();

        var response = given(requestSpecification)
                .body(dto)
                .post(GROUND_URL);

        response.then()
                .statusCode(HttpStatus.CREATED.value());
    }

    @Test
    void shouldFindGroundById() {
        var dto = GroundDto.builder()
                .id(0)
                .country("Иванова")
                .city("Санкт-Петербург")
                .postalCode("190000")
                .street("Невский")
                .number("10")
                .price(BigDecimal.valueOf(500000))
                .isActive(true)
                .type(SubjectType.GROUND.name())
                .ownerId(clientId)
                .build();
        createGround(dto, HttpStatus.CREATED.value());

        var ground = getGround(
                null,
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
        ).getGrounds().stream().findFirst().get();

        var id = ground.getId();

        var resultById = getGround(
                id,
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

        Assertions.assertNotNull(resultById);
        Assertions.assertNotNull(resultById.getGrounds());
        Assertions.assertFalse(resultById.getGrounds().isEmpty());

        var groundById = resultById.getGrounds().iterator().next();
        Assertions.assertEquals(ground.getCountry(), groundById.getCountry());
        Assertions.assertEquals(ground.getCity(), groundById.getCity());
        Assertions.assertEquals(ground.getPostalCode(), groundById.getPostalCode());
        Assertions.assertEquals(ground.getStreet(), groundById.getStreet());
        Assertions.assertEquals(ground.getNumber(), groundById.getNumber());
        Assertions.assertEquals(ground.getPrice(), groundById.getPrice());
    }

    @Test
    void shouldFindGroundsByCountry() {
        var dto = GroundDto.builder()
                .id(0)
                .country("Москва")
                .city("Москва")
                .postalCode("123456")
                .street("Тверская")
                .number("1")
                .price(BigDecimal.valueOf(750000))
                .isActive(true)
                .type(SubjectType.GROUND.name())
                .ownerId(1)
                .build();
        createGround(dto, HttpStatus.CREATED.value());

        var result = getGround(
                null,
                dto.getCountry(),
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
        Assertions.assertNotNull(result.getGrounds());
        Assertions.assertFalse(result.getGrounds().isEmpty());

        var ground = result.getGrounds().iterator().next();
        Assertions.assertEquals(dto.getCountry(), ground.getCountry());
    }

    @Test
    void shouldFindGroundsByCity() {
        var dto = GroundDto.builder()
                .id(0)
                .country("Санкт-Петербург")
                .city("Санкт-Петербург")
                .postalCode("190000")
                .street("Невский")
                .number("2")
                .price(BigDecimal.valueOf(600000))
                .isActive(true)
                .type(SubjectType.GROUND.name())
                .ownerId(1)
                .build();
        createGround(dto, HttpStatus.CREATED.value());

        var result = getGround(
                null,
                null,
                dto.getCity(),
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
        Assertions.assertNotNull(result.getGrounds());
        Assertions.assertFalse(result.getGrounds().isEmpty());

        var ground = result.getGrounds().iterator().next();
        Assertions.assertEquals(dto.getCity(), ground.getCity());
    }

    @Test
    void shouldFindGroundsByPostalCode() {
        var dto = GroundDto.builder()
                .id(0)
                .country("Новосибирск")
                .city("Новосибирск")
                .postalCode("630000")
                .street("Красный")
                .number("3")
                .price(BigDecimal.valueOf(450000))
                .isActive(true)
                .type(SubjectType.GROUND.name())
                .ownerId(1)
                .build();
        createGround(dto, HttpStatus.CREATED.value());

        var result = getGround(
                null,
                null,
                null,
                dto.getPostalCode(),
                null,
                null,
                null,
                null,
                0,
                10,
                HttpStatus.OK.value()
        );

        Assertions.assertNotNull(result);
        Assertions.assertNotNull(result.getGrounds());
        Assertions.assertFalse(result.getGrounds().isEmpty());

        var ground = result.getGrounds().iterator().next();
        Assertions.assertEquals(dto.getPostalCode(), ground.getPostalCode());
    }

    @Test
    void shouldFindGroundsByStreet() {
        var dto = GroundDto.builder()
                .id(0)
                .country("Екатеринбург")
                .city("Екатеринбург")
                .postalCode("620000")
                .street("Уральская")
                .number("4")
                .price(BigDecimal.valueOf(550000))
                .isActive(true)
                .type(SubjectType.GROUND.name())
                .ownerId(1)
                .build();
        createGround(dto, HttpStatus.CREATED.value());

        var result = getGround(
                null,
                null,
                null,
                null,
                dto.getStreet(),
                null,
                null,
                null,
                0,
                10,
                HttpStatus.OK.value()
        );

        Assertions.assertNotNull(result);
        Assertions.assertNotNull(result.getGrounds());
        Assertions.assertFalse(result.getGrounds().isEmpty());

        var ground = result.getGrounds().iterator().next();
        Assertions.assertEquals(dto.getStreet(), ground.getStreet());
    }

    @Test
    void shouldFindGroundsByNumber() {
        var dto = GroundDto.builder()
                .id(0)
                .country("Казань")
                .city("Казань")
                .postalCode("420000")
                .street("Кремлевская")
                .number("5")
                .price(BigDecimal.valueOf(650000))
                .isActive(true)
                .type(SubjectType.GROUND.name())
                .ownerId(1)
                .build();
        createGround(dto, HttpStatus.CREATED.value());

        var result = getGround(
                null,
                null,
                null,
                null,
                null,
                dto.getNumber(),
                null,
                null,
                0,
                10,
                HttpStatus.OK.value()
        );

        Assertions.assertNotNull(result);
        Assertions.assertNotNull(result.getGrounds());
        Assertions.assertFalse(result.getGrounds().isEmpty());

        var ground = result.getGrounds().iterator().next();
        Assertions.assertEquals(dto.getNumber(), ground.getNumber());
    }

    @Test
    void shouldFindGroundsByPrice() {
        var dto = GroundDto.builder()
                .id(0)
                .country("Ростов")
                .city("Ростов")
                .postalCode("344000")
                .street("Пушкинская")
                .number("6")
                .price(BigDecimal.valueOf(800000))
                .isActive(true)
                .type(SubjectType.GROUND.name())
                .ownerId(1)
                .build();
        createGround(dto, HttpStatus.CREATED.value());

        var result = getGround(
                null,
                null,
                null,
                null,
                null,
                null,
                dto.getPrice(),
                null,
                0,
                10,
                HttpStatus.OK.value()
        );

        Assertions.assertNotNull(result);
        Assertions.assertNotNull(result.getGrounds());
        Assertions.assertFalse(result.getGrounds().isEmpty());

        var ground = result.getGrounds().iterator().next();
        Assertions.assertEquals(0, dto.getPrice().compareTo(ground.getPrice()));
    }

    @Test
    void shouldReturnEmptyListWhenNoGroundsFound() {
        var result = getGround(
                99999L,
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
        Assertions.assertNull(result.getGrounds());
    }

    @Test
    void shouldSupportPagination() {
        var ground1 = GroundDto.builder()
                .id(0)
                .country("Пушкин")
                .city("Пушкин")
                .postalCode("196600")
                .street("Лермонтовская")
                .number("1")
                .price(BigDecimal.valueOf(400000))
                .isActive(true)
                .type(SubjectType.GROUND.name())
                .ownerId(1)
                .build();
        var ground2 = GroundDto.builder()
                .id(0)
                .country("Пушкин")
                .city("Пушкин")
                .postalCode("196600")
                .street("Декабристов")
                .number("2")
                .price(BigDecimal.valueOf(450000))
                .isActive(true)
                .type(SubjectType.GROUND.name())
                .ownerId(1)
                .build();
        var ground3 = GroundDto.builder()
                .id(0)
                .country("Пушкин")
                .city("Пушкин")
                .postalCode("196600")
                .street("Невский")
                .number("3")
                .price(BigDecimal.valueOf(500000))
                .isActive(true)
                .type(SubjectType.GROUND.name())
                .ownerId(1)
                .build();

        createGround(ground1, HttpStatus.CREATED.value());
        createGround(ground2, HttpStatus.CREATED.value());
        createGround(ground3, HttpStatus.CREATED.value());

        var resultPage1 = getGround(
                null,
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
        Assertions.assertNotNull(resultPage1.getGrounds());
        Assertions.assertEquals(2, resultPage1.getSize());
        Assertions.assertEquals(0, resultPage1.getPage());

        var resultPage2 = getGround(
                null,
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
        Assertions.assertNotNull(resultPage2.getGrounds());
    }

    @Test
    void shouldSupportSorting() {
        var ground1 = GroundDto.builder()
                .id(0)
                .country("Алексеев")
                .city("Алексеев")
                .postalCode("196600")
                .street("Лермонтовская")
                .number("1")
                .price(BigDecimal.valueOf(400000))
                .isActive(true)
                .type(SubjectType.GROUND.name())
                .ownerId(1)
                .build();
        var ground2 = GroundDto.builder()
                .id(0)
                .country("Борисов")
                .city("Борисов")
                .postalCode("196600")
                .street("Декабристов")
                .number("2")
                .price(BigDecimal.valueOf(450000))
                .isActive(true)
                .type(SubjectType.GROUND.name())
                .ownerId(1)
                .build();
        var ground3 = GroundDto.builder()
                .id(0)
                .country("Васильев")
                .city("Васильев")
                .postalCode("196600")
                .street("Невский")
                .number("3")
                .price(BigDecimal.valueOf(500000))
                .isActive(true)
                .type(SubjectType.GROUND.name())
                .ownerId(1)
                .build();

        createGround(ground1, HttpStatus.CREATED.value());
        createGround(ground2, HttpStatus.CREATED.value());
        createGround(ground3, HttpStatus.CREATED.value());

        var resultAsc = getGround(
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                0,
                10,
                "country",
                "ASC",
                HttpStatus.OK.value()
        );

        Assertions.assertNotNull(resultAsc);
        Assertions.assertNotNull(resultAsc.getGrounds());

        var groundsAsc = new ArrayList<>(resultAsc.getGrounds());
        var firstCharOfFirstClient = groundsAsc.getFirst().getCountry().toCharArray()[0];
        var firstCharOfLastClient = groundsAsc.getLast().getCountry().toCharArray()[0];

        Assertions.assertTrue(firstCharOfLastClient > firstCharOfFirstClient);

        var resultDesc = getGround(
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                0,
                10,
                "country",
                "DESC",
                HttpStatus.OK.value()
        );

        Assertions.assertNotNull(resultDesc);
        Assertions.assertNotNull(resultDesc.getGrounds());

        var groundsDesc = new ArrayList<>(resultDesc.getGrounds());
        firstCharOfFirstClient = groundsDesc.getFirst().getCountry().toCharArray()[0];
        firstCharOfLastClient = groundsDesc.getLast().getCountry().toCharArray()[0];

        Assertions.assertTrue(firstCharOfLastClient < firstCharOfFirstClient);
    }

    @Test
    void shouldCreateGroundWithAllFields() {
        var groundDto = GroundDto.builder()
                .id(0)
                .country("Иванов")
                .city("Иваново")
                .postalCode("153000")
                .street("Ленина")
                .number("10")
                .price(BigDecimal.valueOf(700000))
                .isActive(true)
                .type(SubjectType.GROUND.name())
                .ownerId(1)
                .build();

        createGround(groundDto, HttpStatus.CREATED.value());

        var result = getGround(
                null,
                groundDto.getCountry(),
                groundDto.getCity(),
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
        Assertions.assertEquals(1, result.getGrounds().size());

        var createdGround = result.getGrounds().iterator().next();
        Assertions.assertEquals(groundDto.getCountry(), createdGround.getCountry());
        Assertions.assertEquals(groundDto.getCity(), createdGround.getCity());
        Assertions.assertEquals(groundDto.getPostalCode(), createdGround.getPostalCode());
        Assertions.assertEquals(groundDto.getStreet(), createdGround.getStreet());
        Assertions.assertEquals(groundDto.getNumber(), createdGround.getNumber());
        Assertions.assertEquals(0, groundDto.getPrice().compareTo(createdGround.getPrice()));
    }

    @Test
    void shouldReturnCorrectPageInfo() {
        var groundDto = GroundDto.builder()
                .id(0)
                .country("Тестов")
                .city("Тестов")
                .postalCode("196600")
                .street("Тестовая")
                .number("1")
                .price(BigDecimal.valueOf(500000))
                .isActive(true)
                .type(SubjectType.GROUND.name())
                .ownerId(1)
                .build();
        createGround(groundDto, HttpStatus.CREATED.value());

        var result = getGround(
                null,
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
    void shouldFindGroundsByCombinedParameters() {
        var groundDto = GroundDto.builder()
                .id(0)
                .country("Петров")
                .city("Петров")
                .postalCode("196600")
                .street("Невский")
                .number("5")
                .price(BigDecimal.valueOf(600000))
                .isActive(true)
                .type(SubjectType.GROUND.name())
                .ownerId(1)
                .build();
        createGround(groundDto, HttpStatus.CREATED.value());

        var result = getGround(
                null,
                groundDto.getCountry(),
                groundDto.getCity(),
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
        Assertions.assertFalse(result.getGrounds().isEmpty());

        var ground = result.getGrounds().iterator().next();
        Assertions.assertEquals(groundDto.getCountry(), ground.getCountry());
        Assertions.assertEquals(groundDto.getCity(), ground.getCity());
    }

    @Test
    void shouldSortByPrice() {
        var ground1 = GroundDto.builder()
                .id(0)
                .country("Алексеев")
                .city("Алексеев")
                .postalCode("196600")
                .street("Лермонтовская")
                .number("1")
                .price(BigDecimal.valueOf(300000))
                .isActive(true)
                .type(SubjectType.GROUND.name())
                .ownerId(clientId)
                .build();
        var ground2 = GroundDto.builder()
                .id(0)
                .country("Борисов")
                .city("Борисов")
                .postalCode("196600")
                .street("Декабристов")
                .number("2")
                .price(BigDecimal.valueOf(500000))
                .isActive(true)
                .type(SubjectType.GROUND.name())
                .ownerId(clientId)
                .build();
        var ground3 = GroundDto.builder()
                .id(0)
                .country("Васильев")
                .city("Васильев")
                .postalCode("196600")
                .street("Невский")
                .number("3")
                .price(BigDecimal.valueOf(400000))
                .isActive(true)
                .type(SubjectType.GROUND.name())
                .ownerId(clientId)
                .build();

        createGround(ground1, HttpStatus.CREATED.value());
        createGround(ground2, HttpStatus.CREATED.value());
        createGround(ground3, HttpStatus.CREATED.value());

        var resultAsc = getGround(
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                0,
                10,
                "price",
                "ASC",
                HttpStatus.OK.value()
        );

        Assertions.assertNotNull(resultAsc);
        Assertions.assertNotNull(resultAsc.getGrounds());

        var groundsAsc = new ArrayList<>(resultAsc.getGrounds());
        Assertions.assertTrue(groundsAsc.getFirst().getPrice().compareTo(groundsAsc.getLast().getPrice()) <= 0);

        var resultDesc = getGround(
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                0,
                10,
                "price",
                "DESC",
                HttpStatus.OK.value()
        );

        Assertions.assertNotNull(resultDesc);
        Assertions.assertNotNull(resultDesc.getGrounds());

        var groundsDesc = new ArrayList<>(resultDesc.getGrounds());
        Assertions.assertTrue(groundsDesc.getFirst().getPrice().compareTo(groundsDesc.getLast().getPrice()) >= 0);
    }

    @Test
    void shouldFindGroundsByArea() {
        var ground1 = GroundDto.builder()
                .id(0)
                .country("Алексеев")
                .city("Алексеев")
                .postalCode("196600")
                .street("Лермонтовская")
                .number("1")
                .price(BigDecimal.valueOf(300000))
                .isActive(true)
                .type(SubjectType.GROUND.name())
                .ownerId(clientId)
                .build();
        ground1.setArea(5.5);
        
        var ground2 = GroundDto.builder()
                .id(0)
                .country("Борисов")
                .city("Борисов")
                .postalCode("196600")
                .street("Декабристов")
                .number("2")
                .price(BigDecimal.valueOf(400000))
                .isActive(true)
                .type(SubjectType.GROUND.name())
                .ownerId(clientId)
                .build();
        ground2.setArea(7.5);
        
        var ground3 = GroundDto.builder()
                .id(0)
                .country("Васильев")
                .city("Васильев")
                .postalCode("196600")
                .street("Невский")
                .number("3")
                .price(BigDecimal.valueOf(500000))
                .isActive(true)
                .type(SubjectType.GROUND.name())
                .ownerId(clientId)
                .build();
        ground3.setArea(6.5);

        createGround(ground1, HttpStatus.CREATED.value());
        createGround(ground2, HttpStatus.CREATED.value());
        createGround(ground3, HttpStatus.CREATED.value());

        var result = getGround(
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                6.5,
                0,
                10,
                HttpStatus.OK.value()
        );

        Assertions.assertNotNull(result);
        Assertions.assertFalse(result.getGrounds().isEmpty());
    }
}
