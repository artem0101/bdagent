package ru.education.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.springframework.http.HttpStatus;
import ru.education.dto.PlacementDto;

import java.math.BigDecimal;

import static io.restassured.RestAssured.given;

class PlacementControllerIntegrationTest extends BaseTest {

    private Long clientId;

    @BeforeEach
    void setUp() {
        clientId = getClient(
                null,
                tihonovDto.getSurname(),
                tihonovDto.getName(),
                tihonovDto.getPatronymic(),
                null,
                null,
                null,
                0,
                10,
                HttpStatus.OK.value()
        ).getClients().stream().findFirst().get().getId();
    }

    @Test
    void shouldCreatePlacementAndReturnCreatedStatus() {
        var dto = ru.education.dto.PlacementDto.builder()
                .id(0)
                .country("Тестовая страна")
                .city("Тестовый город")
                .postalCode("123456")
                .street("Тестовая улица")
                .number("1")
                .price(BigDecimal.valueOf(1000000))
                .isActive(true)
                .type("PLACEMENT")
                .ownerId(clientId)
                .floors(1)
                .rooms(2)
                .area(50.0)
                .build();

        var response = given(requestSpecification)
                .body(dto)
                .post(PLACEMENT_URL);

        response.then()
                .statusCode(HttpStatus.CREATED.value());
    }

    @Test
    void shouldFindPlacementsById() {
        var dto = ru.education.dto.PlacementDto.builder()
                .id(0)
                .country("Иванова")
                .city("Санкт-Петербург")
                .postalCode("190000")
                .street("Невский")
                .number("10")
                .price(BigDecimal.valueOf(500000))
                .isActive(true)
                .type("PLACEMENT")
                .ownerId(clientId)
                .floors(1)
                .rooms(2)
                .area(50.0)
                .build();
        createPlacement(dto, HttpStatus.CREATED.value());

        var result = getPlacement(
                null,
                dto.getCountry(),
                dto.getCity(),
                dto.getPostalCode(),
                dto.getStreet(),
                dto.getNumber(),
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                HttpStatus.OK.value()
        );

        Assertions.assertNotNull(result);
        Assertions.assertFalse(result.getPlacements().isEmpty());

        var placement = result.getPlacements().iterator().next();
        Assertions.assertEquals(dto.getCountry(), placement.getCountry());
        Assertions.assertEquals(dto.getCity(), placement.getCity());
        Assertions.assertEquals(dto.getArea(), placement.getArea());
    }

    @Test
    void shouldFindPlacementsByPrice() {
        var dto = ru.education.dto.PlacementDto.builder()
                .id(0)
                .country("Москва")
                .city("Москва")
                .price(BigDecimal.valueOf(750000))
                .isActive(true)
                .type("PLACEMENT")
                .ownerId(clientId)
                .build();
        createPlacement(dto, HttpStatus.CREATED.value());

        var result = getPlacement(
                null,
                null,
                null,
                null,
                null,
                null,
                BigDecimal.valueOf(750000),
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                HttpStatus.OK.value()
        );

        Assertions.assertNotNull(result);
        Assertions.assertFalse(result.getPlacements().isEmpty());
        Assertions.assertEquals(0, dto.getPrice().compareTo(result.getPlacements().iterator().next().getPrice()));
    }

    @Test
    void shouldSupportPagination() {
        for (var i = 0; i < 5; i++) {
            createPlacement(ru.education.dto.PlacementDto.builder()
                    .id(0)
                    .country("City")
                    .price(BigDecimal.valueOf(1000 + i))
                    .isActive(true)
                    .type("PLACEMENT")
                    .ownerId(clientId)
                    .build(), HttpStatus.CREATED.value());
        }

        var resultPage1 = getPlacement(
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                HttpStatus.OK.value()
        );

        Assertions.assertEquals(10, resultPage1.getSize());
        Assertions.assertEquals(0, resultPage1.getPage());

        var resultPage2 = getPlacement(
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                HttpStatus.OK.value()
        );

        Assertions.assertEquals(10, resultPage2.getSize());
    }

    @Test
    void shouldSupportSorting() {
        createPlacement(
                PlacementDto.builder()
                        .id(0)
                        .country("Я")
                        .price(BigDecimal.valueOf(200))
                        .isActive(true)
                        .type("PLACEMENT")
                        .ownerId(clientId)
                        .build(),
                HttpStatus.CREATED.value()
        );
        createPlacement(
                PlacementDto.builder()
                        .id(0)
                        .country("A")
                        .price(BigDecimal.valueOf(100))
                        .isActive(true)
                        .type("PLACEMENT")
                        .ownerId(clientId).build(),
                HttpStatus.CREATED.value()
        );

        var resultAsc = getPlacement(
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                "country",
                "ASC",
                HttpStatus.OK.value()
        ).getPlacements()
                .stream()
                .findFirst()
                .get();

        Assertions.assertEquals("A", resultAsc.getCountry());

        var resultDesc = getPlacement(
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                "country",
                "DESC", HttpStatus.OK.value()
        );

        Assertions.assertEquals("Я", resultDesc.getPlacements().stream().findFirst().get().getCountry());
    }
}
