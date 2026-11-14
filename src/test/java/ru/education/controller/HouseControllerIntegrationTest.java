package ru.education.controller;

import org.junit.jupiter.api.*;
import org.springframework.http.HttpStatus;
import ru.education.dto.HouseDto;

import java.math.BigDecimal;

import static io.restassured.RestAssured.given;

//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class HouseControllerIntegrationTest extends BaseTest {

    private Long clientId;

    @BeforeEach
    void setUp() {
        clientId = getClient(
                null,
                valeryanovDto.getSurname(),
                valeryanovDto.getName(),
                valeryanovDto.getPatronymic(),
                null,
                null,
                null,
                0,
                10,
                HttpStatus.OK.value()
        ).getClients().stream().findFirst().get().getId();
    }

    @Test
    void shouldCreateHouseAndReturnCreatedStatus() {
        var dto = HouseDto.builder()
                .id(0)
                .country("Тестовая страна")
                .city("Тестовый город")
                .postalCode("123456")
                .street("Тестовая улица")
                .number("1")
                .price(BigDecimal.valueOf(1000000))
                .isActive(true)
                .type("HOUSE")
                .ownerId(clientId)
                .floors(2)
                .rooms(3)
                .areaGround(50.0)
                .areaHouse(150.0)
                .build();

        var response = given(requestSpecification)
                .body(dto)
                .post(HOUSE_URL);

        response.then()
                .statusCode(HttpStatus.CREATED.value());
    }

    @Test
    void shouldFindHousesById() {
        var dto = HouseDto.builder()
                .id(0)
                .country("Иванова")
                .city("Санкт-Петербург")
                .postalCode("190000")
                .street("Невский")
                .number("10")
                .price(BigDecimal.valueOf(500000))
                .isActive(true)
                .type("HOUSE")
                .ownerId(clientId)
                .floors(1)
                .rooms(2)
                .areaGround(40.0)
                .areaHouse(100.0)
                .build();
        createHouse(dto, HttpStatus.CREATED.value());

        var result = getHouse(
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
                null,
                HttpStatus.OK.value()
        );

        Assertions.assertNotNull(result);
        Assertions.assertFalse(result.getHouses().isEmpty());

        var house = result.getHouses().iterator().next();
        Assertions.assertEquals(dto.getCountry(), house.getCountry());
        Assertions.assertEquals(dto.getCity(), house.getCity());
        Assertions.assertEquals(dto.getFloors(), house.getFloors());
        Assertions.assertEquals(dto.getRooms(), house.getRooms());
    }

    @Test
    void shouldFindHousesByPrice() {
        var dto = HouseDto.builder()
                .id(0)
                .country("Москва")
                .city("Москва")
                .price(BigDecimal.valueOf(750000))
                .isActive(true)
                .type("HOUSE")
                .ownerId(clientId)
                .build();
        createHouse(dto, HttpStatus.CREATED.value());

        var result = getHouse(
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
                null,
                HttpStatus.OK.value()
        );

        Assertions.assertNotNull(result);
        Assertions.assertFalse(result.getHouses().isEmpty());
        Assertions.assertEquals(0, dto.getPrice().compareTo(result.getHouses().iterator().next().getPrice()));
    }

    @Test
    void shouldSupportPagination() {
        for (var i = 0; i < 5; i++) {
            createHouse(HouseDto.builder()
                    .id(0)
                    .country("City")
                    .price(BigDecimal.valueOf(1000 + i))
                    .isActive(true)
                    .type("HOUSE")
                    .ownerId(clientId)
                    .build(), HttpStatus.CREATED.value());
        }

        var resultPage1 = getHouse(
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
                null,
                HttpStatus.OK.value()
        );

        Assertions.assertTrue(2 <= resultPage1.getSize());
        Assertions.assertEquals(0, resultPage1.getPage());

        var resultPage2 = getHouse(
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
                null,
                HttpStatus.OK.value()
        );

        Assertions.assertEquals(10, resultPage2.getSize());
    }

    @Test
    void shouldSupportSorting() {
        createHouse(
                HouseDto.builder()
                        .id(0)
                        .country("Я")
                        .price(BigDecimal.valueOf(200))
                        .isActive(true)
                        .type("HOUSE")
                        .ownerId(clientId)
                        .build(),
                HttpStatus.CREATED.value()
        );
        createHouse(
                HouseDto.builder()
                        .id(0)
                        .country("A")
                        .price(BigDecimal.valueOf(100))
                        .isActive(true)
                        .type("HOUSE")
                        .ownerId(clientId)
                        .build(),
                HttpStatus.CREATED.value()
        );

        var resultAsc = getHouse(
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
                "country",
                "ASC",
                HttpStatus.OK.value()
        );

        Assertions.assertEquals("A", resultAsc.getHouses().stream().findFirst().get().getCountry());

        var resultDesc = getHouse(
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
                "country",
                "DESC",
                HttpStatus.OK.value()
        );

        Assertions.assertEquals("Я", resultDesc.getHouses().stream().findFirst().get().getCountry());
    }
}
