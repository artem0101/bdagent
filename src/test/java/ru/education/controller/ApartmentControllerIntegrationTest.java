package ru.education.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import ru.education.dto.ApartmentDto;

import java.math.BigDecimal;

import static io.restassured.RestAssured.given;

class ApartmentControllerIntegrationTest extends BaseTest {

    private Long clientId;

    @BeforeEach
    void setUp() {
        clientId = getClient(
                null,
                evdokimovDto.getSurname(),
                evdokimovDto.getName(),
                evdokimovDto.getPatronymic(),
                null,
                null,
                null,
                0,
                10,
                HttpStatus.OK.value()
        ).getClients().stream().findFirst().get().getId();
    }

    @Test
    void shouldCreateApartmentAndReturnCreatedStatus() {
        var dto = ApartmentDto.builder()
                .id(0)
                .country("Тестовая страна")
                .city("Тестовый город")
                .postalCode("123456")
                .street("Тестовая улица")
                .number("1")
                .price(BigDecimal.valueOf(1000000))
                .isActive(true)
                .type("APARTMENT")
                .ownerId(clientId)
                .floor(2)
                .rooms(3)
                .build();

        var response = given(requestSpecification)
                .body(dto)
                .post(APARTMENT_URL);

        response.then()
                .statusCode(HttpStatus.CREATED.value());
    }

    @Test
    void shouldFindApartmentsById() {
        var dto = ApartmentDto.builder()
                .id(0)
                .country("Иванова")
                .city("Санкт-Петербург")
                .postalCode("190000")
                .street("Невский")
                .number("10")
                .price(BigDecimal.valueOf(500000))
                .isActive(true)
                .type("APARTMENT")
                .ownerId(clientId)
                .floor(5)
                .rooms(2)
                .build();
        createApartment(dto, HttpStatus.CREATED.value());

        var result = getApartment(
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

        Assertions.assertNotNull(result);
        Assertions.assertFalse(result.getApartments().isEmpty());

        var apartment = result.getApartments().iterator().next();
        Assertions.assertEquals(dto.getCountry(), apartment.getCountry());
        Assertions.assertEquals(dto.getCity(), apartment.getCity());
        Assertions.assertEquals(dto.getFloor(), apartment.getFloor());
        Assertions.assertEquals(dto.getRooms(), apartment.getRooms());
    }

    @Test
    void shouldFindApartmentsByPrice() {
        var dto = ApartmentDto.builder()
                .id(0)
                .country("Москва")
                .city("Москва")
                .price(BigDecimal.valueOf(750000))
                .isActive(true)
                .type("APARTMENT")
                .ownerId(clientId)
                .build();
        createApartment(dto, HttpStatus.CREATED.value());

        var result = getApartment(
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
                HttpStatus.OK.value()
        );

        Assertions.assertNotNull(result);
        Assertions.assertFalse(result.getApartments().isEmpty());
        Assertions.assertEquals(0, dto.getPrice().compareTo(result.getApartments().iterator().next().getPrice()));
    }

    @Test
    void shouldSupportPagination() {
        for (var i = 0; i < 5; i++) {
            createApartment(ApartmentDto.builder()
                    .id(0)
                    .country("City")
                    .price(BigDecimal.valueOf(1000 + i))
                    .isActive(true)
                    .type("APARTMENT")
                    .ownerId(clientId)
                    .build(), HttpStatus.CREATED.value());
        }

        var resultPage1 = getApartment(
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
                 0,
                2,
                null,
                null,
                HttpStatus.OK.value()
        );

        Assertions.assertEquals(2, resultPage1.getSize());
        Assertions.assertEquals(0, resultPage1.getPage());

        var resultPage2 = getApartment(
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
                1,
                2,
                null,
                null,
                HttpStatus.OK.value()
        );

        Assertions.assertEquals(2, resultPage2.getSize());
    }

    @Test
    void shouldSupportSorting() {
        createApartment(
                ApartmentDto.builder()
                        .id(0)
                        .country("Я")
                        .price(BigDecimal.valueOf(200))
                        .isActive(true)
                        .type("APARTMENT")
                        .ownerId(clientId)
                        .build(),
                HttpStatus.CREATED.value()
        );
        createApartment(
                ApartmentDto.builder()
                        .id(0)
                        .country("A")
                        .price(BigDecimal.valueOf(100))
                        .isActive(true)
                        .type("APARTMENT")
                        .ownerId(clientId).build(),
                HttpStatus.CREATED.value()
        );

        var resultAsc = getApartment(
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
                0,
                10,
                "country",
                "ASC",
                HttpStatus.OK.value()
        );

        Assertions.assertEquals("A", resultAsc.getApartments().stream().findFirst().get().getCountry());

        var resultDesc = getApartment(
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
                0,
                10,
                "country",
                "DESC",
                HttpStatus.OK.value()
        );

        Assertions.assertEquals("Я", resultDesc.getApartments().stream().findFirst().get().getCountry());
    }
}
