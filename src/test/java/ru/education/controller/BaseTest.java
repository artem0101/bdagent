package ru.education.controller;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.shaded.com.google.common.net.HttpHeaders;
import ru.education.dto.*;
import ru.education.dto.paged.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collection;

import static io.restassured.RestAssured.given;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
class BaseTest {

    protected static final String EMPLOYEE_URL = "/api/v1/employee";
    protected static final String CLIENT_URL = "/api/v1/client";
    protected static final String GROUND_URL = "/api/v1/ground";
    protected static final String APARTMENT_URL = "/api/v1/apartment";
    protected static final String HOUSE_URL = "/api/v1/house";
    protected static final String PLACEMENT_URL = "/api/v1/placement";

    protected ClientDto evdokimovDto;
    protected ClientDto andreevDto;
    protected ClientDto valeryanovDto;
    protected ClientDto tihonovDto;

    @LocalServerPort
    private Integer port;

    protected static RequestSpecification requestSpecification;

    @BeforeAll
    void setUpAndAddClients() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        requestSpecification = new RequestSpecBuilder()
                .setPort(port)
                .addHeader(
                        HttpHeaders.CONTENT_TYPE,
                        MediaType.APPLICATION_JSON_VALUE
                )
                .build();

        evdokimovDto = new ClientDto(
                null,
                "Евдокимов",
                "Евдоким",
                "Евдокимович",
                "+79111234568",
                "test1@example.com",
                Instant.now().minus(1000, ChronoUnit.DAYS)
        );
        createClient(evdokimovDto, HttpStatus.CREATED.value());
        andreevDto = new ClientDto(
                null,
                "Андреев",
                "Андрей",
                "Андреевич",
                "+79131234568",
                "test@example.com",
                Instant.now().minus(1000, ChronoUnit.DAYS)
        );
        createClient(andreevDto, HttpStatus.CREATED.value());
        valeryanovDto = new ClientDto(
                null,
                "Валерьянов",
                "Валерьян",
                "Валерьянович",
                "+79121234568",
                "test@example.com",
                Instant.now().minus(1000, ChronoUnit.DAYS)
        );
        createClient(valeryanovDto, HttpStatus.CREATED.value());
        tihonovDto = new ClientDto(
                null,
                "Тихонов",
                "Тихон",
                "Тихонович",
                "+79101234568",
                "test@example.com",
                Instant.now().minus(1000, ChronoUnit.DAYS)
        );
        createClient(tihonovDto, HttpStatus.CREATED.value());
    }


    protected void assertEmployeeMatchesDto(Collection<EmployeeDto> resultList, EmployeeDto expected) {
        Assertions.assertFalse(resultList.isEmpty());
        var employee = resultList.iterator().next();

        Assertions.assertEquals(expected.getSurname(), employee.getSurname());
        Assertions.assertEquals(expected.getName(), employee.getName());
        Assertions.assertEquals(expected.getPatronymic(), employee.getPatronymic());
        Assertions.assertEquals(expected.getPost(), employee.getPost());
    }

    protected void createEmployee(EmployeeDto dto, int statusCode) {
        given(requestSpecification)
                .when()
                .body(dto)
                .post(EMPLOYEE_URL)
                .then()
                .statusCode(statusCode)
                .log()
                .ifValidationFails(LogDetail.ALL);
    }

    protected void deleteEmployee(long id, int statusCode) {
        given(requestSpecification)
                .delete(EMPLOYEE_URL + "/" + id)
                .then()
                .statusCode(statusCode);
    }

    protected void updateEmployee(EmployeeDto dto, long id, int statusCode) {
        given(requestSpecification)
                .body(dto)
                .put(EMPLOYEE_URL + "/" + id)
                .then()
                .statusCode(statusCode);
    }

    protected PagedEmployeesDto getEmployee(
            Long id,
            String surname,
            String name,
            String patronymic,
            String post,
            Boolean isDeleted,
            Integer page,
            Integer size,
            @DefaultValue("HttpStatus.OK.value()") int statusCode
    ) {
        return getEmployee(id, surname, name, patronymic, post, isDeleted, page, size, null, null, statusCode);
    }

    protected PagedEmployeesDto getEmployee(
            Long id,
            String surname,
            String name,
            String patronymic,
            String post,
            Integer page,
            Integer size,
            @DefaultValue("HttpStatus.OK.value()") int statusCode
    ) {
        return getEmployee(id, surname, name, patronymic, post, null, page, size, null, null, statusCode);
    }

    protected PagedEmployeesDto getEmployee(
            Long id,
            String surname,
            String name,
            String patronymic,
            String post,
            Integer page,
            Integer size,
            String sortBy,
            String sortDirection,
            @DefaultValue("HttpStatus.OK.value()") int statusCode
    ) {
        return getEmployee(id, surname, name, patronymic, post, null, page, size, sortBy, sortDirection, statusCode);
    }

    protected PagedEmployeesDto getEmployee(
            Long id,
            String surname,
            String name,
            String patronymic,
            String post,
            Boolean isDeleted,
            Integer page,
            Integer size,
            String sortBy,
            String sortDirection,
            @DefaultValue("HttpStatus.OK.value()") int statusCode
    ) {
        var path = new StringBuilder(EMPLOYEE_URL);
        var params = new ArrayList<String>();

        if (id != null) params.add("id=" + id);
        if (surname != null && !surname.isEmpty()) params.add("surname=" + surname);
        if (name != null && !name.isEmpty()) params.add("name=" + name);
        if (patronymic != null && !patronymic.isEmpty()) params.add("patronymic=" + patronymic);
        if (post != null && !post.isEmpty()) params.add("post=" + post);
        if (isDeleted != null) params.add("isDeleted=" + isDeleted);
        if (page != null) params.add("page=" + page);
        if (size != null) params.add("size=" + size);
        if (sortBy != null && !sortBy.isEmpty()) params.add("sortBy=" + sortBy);
        if (sortDirection != null && !sortDirection.isEmpty()) params.add("sortDirection=" + sortDirection);

        for (int i = 0; i < params.size(); i++) {
            path.append(i == 0 ? "?" : "&").append(params.get(i));
        }

        return given(requestSpecification)
                .when()
                .get(path.toString())
                .then()
                .statusCode(statusCode)
                .log()
                .ifValidationFails(LogDetail.ALL)
                .extract()
                .body()
                .as(PagedEmployeesDto.class);
    }

    protected void createClient(ClientDto dto, int statusCode) {
        given(requestSpecification)
                .when()
                .body(dto)
                .post(CLIENT_URL)
                .then()
                .statusCode(statusCode)
                .log()
                .ifValidationFails(LogDetail.ALL);
    }

    protected PagedClientsDto getClient(
            Long id,
            String surname,
            String name,
            String patronymic,
            String email,
            String phoneNumber,
            Instant birthday,
            Integer page,
            Integer size,
            @DefaultValue("HttpStatus.OK.value()") int statusCode
    ) {
        return getClient(
                id,
                surname,
                name,
                patronymic,
                email,
                phoneNumber,
                birthday,
                page,
                size,
                null,
                null,
                statusCode
        );
    }

    protected PagedClientsDto getClient(
            Long id,
            String surname,
            String name,
            String patronymic,
            String email,
            String phoneNumber,
            Instant birthday,
            Integer page,
            Integer size,
            String sortBy,
            String sortDirection,
            @DefaultValue("HttpStatus.OK.value()") int statusCode
    ) {
        var path = new StringBuilder(CLIENT_URL);
        var params = new ArrayList<String>();

        if (id != null) params.add("id=" + id);
        if (surname != null && !surname.isEmpty()) params.add("surname=" + surname);
        if (name != null && !name.isEmpty()) params.add("name=" + name);
        if (patronymic != null && !patronymic.isEmpty()) params.add("patronymic=" + patronymic);
        if (email != null && !email.isEmpty()) params.add("email=" + email);
        if (phoneNumber != null && !phoneNumber.isEmpty()) params.add("phoneNumber=" + phoneNumber);
        if (birthday != null) params.add("birthday=" + birthday);
        if (page != null) params.add("page=" + page);
        if (size != null) params.add("size=" + size);
        if (sortBy != null && !sortBy.isEmpty()) params.add("sortBy=" + sortBy);
        if (sortDirection != null && !sortDirection.isEmpty()) params.add("sortDirection=" + sortDirection);

        for (int i = 0; i < params.size(); i++) {
            path.append(i == 0 ? "?" : "&").append(params.get(i));
        }

        return given(requestSpecification)
                .when()
                .get(path.toString())
                .then()
                .statusCode(statusCode)
                .log()
                .ifValidationFails(LogDetail.ALL)
                .extract()
                .body()
                .as(PagedClientsDto.class);
    }

    protected void createGround(GroundDto dto, int statusCode) {
        given(requestSpecification)
                .when()
                .body(dto)
                .post(GROUND_URL)
                .then()
                .statusCode(statusCode)
                .log()
                .ifValidationFails(LogDetail.ALL);
    }

    protected PagedGroundsDto getGround(
            Long id,
            String country,
            String city,
            String postalCode,
            String street,
            String number,
            BigDecimal price,
            Double area,
            Integer page,
            Integer size,
            @DefaultValue("HttpStatus.OK.value()") int statusCode
    ) {
        return getGround(id, country, city, postalCode, street, number, price, area, page, size, null, null, statusCode);
    }

    protected void createApartment(ApartmentDto dto, int statusCode) {
        given(requestSpecification)
                .when()
                .body(dto)
                .post(APARTMENT_URL)
                .then()
                .statusCode(statusCode)
                .log()
                .ifValidationFails(LogDetail.ALL);
    }

    protected PagedApartmentsDto getApartment(
            Long id,
            String country,
            String city,
            String postalCode,
            String street,
            String number,
            BigDecimal price,
            Long subjectId,
            Integer floor,
            Integer room,
            Integer page,
            Integer size,
            String sortBy,
            String sortDirection,
            @DefaultValue("HttpStatus.OK.value()") int statusCode
    ) {
        var path = new StringBuilder(APARTMENT_URL);
        var params = new ArrayList<String>();

        if (id != null) params.add("id=" + id);
        if (country != null && !country.isEmpty()) params.add("country=" + country);
        if (city != null && !city.isEmpty()) params.add("city=" + city);
        if (postalCode != null && !postalCode.isEmpty()) params.add("postalCode=" + postalCode);
        if (street != null && !street.isEmpty()) params.add("street=" + street);
        if (number != null && !number.isEmpty()) params.add("number=" + number);
        if (price != null) params.add("price=" + price);
        if (subjectId != null) params.add("subjectId=" + subjectId);
        if (floor != null) params.add("floor=" + floor);
        if (room != null) params.add("room=" + room);
        if (page != null) params.add("page=" + page);
        if (size != null) params.add("size=" + size);
        if (sortBy != null && !sortBy.isEmpty()) params.add("sortBy=" + sortBy);
        if (sortDirection != null && !sortDirection.isEmpty()) params.add("sortDirection=" + sortDirection);

        for (int i = 0; i < params.size(); i++) {
            path.append(i == 0 ? "?" : "&").append(params.get(i));
        }

        return given(requestSpecification)
                .when()
                .get(path.toString())
                .then()
                .statusCode(statusCode)
                .log()
                .ifValidationFails(LogDetail.ALL)
                .extract()
                .body()
                .as(PagedApartmentsDto.class);
    }

    protected void createHouse(HouseDto dto, int statusCode) {
        given(requestSpecification)
                .when()
                .body(dto)
                .post(HOUSE_URL)
                .then()
                .statusCode(statusCode)
                .log()
                .ifValidationFails(LogDetail.ALL);
    }

    protected PagedHousesDto getHouse(
            Long id,
            String country,
            String city,
            String postalCode,
            String street,
            String number,
            BigDecimal price,
            Long subjectId,
            Integer floors,
            Integer rooms,
            Double areaGround,
            Double areaHouse,
            Integer page,
            Integer size,
            String sortBy,
            String sortDirection,
            @DefaultValue("HttpStatus.OK.value()") int statusCode
    ) {
        var path = new StringBuilder(HOUSE_URL);
        var params = new ArrayList<String>();

        if (id != null) params.add("id=" + id);
        if (country != null && !country.isEmpty()) params.add("country=" + country);
        if (city != null && !city.isEmpty()) params.add("city=" + city);
        if (postalCode != null && !postalCode.isEmpty()) params.add("postalCode=" + postalCode);
        if (street != null && !street.isEmpty()) params.add("street=" + street);
        if (number != null && !number.isEmpty()) params.add("number=" + number);
        if (price != null) params.add("price=" + price);
        if (subjectId != null) params.add("subjectId=" + subjectId);
        if (floors != null) params.add("floors=" + floors);
        if (rooms != null) params.add("rooms=" + rooms);
        if (areaGround != null) params.add("areaGround=" + areaGround);
        if (areaHouse != null) params.add("areaHouse=" + areaHouse);
        if (page != null) params.add("page=" + page);
        if (size != null) params.add("size=" + size);
        if (sortBy != null && !sortBy.isEmpty()) params.add("sortBy=" + sortBy);
        if (sortDirection != null && !sortDirection.isEmpty()) params.add("sortDirection=" + sortDirection);

        for (int i = 0; i < params.size(); i++) {
            path.append(i == 0 ? "?" : "&").append(params.get(i));
        }

        return given(requestSpecification)
                .when()
                .get(path.toString())
                .then()
                .statusCode(statusCode)
                .log()
                .ifValidationFails(LogDetail.ALL)
                .extract()
                .body()
                .as(PagedHousesDto.class);
    }

    protected void createPlacement(PlacementDto dto, int statusCode) {
        given(requestSpecification)
                .when()
                .body(dto)
                .post(PLACEMENT_URL)
                .then()
                .statusCode(statusCode)
                .log()
                .ifValidationFails(LogDetail.ALL);
    }

    protected PagedPlacementsDto getPlacement(
            Long id,
            String country,
            String city,
            String postalCode,
            String street,
            String number,
            BigDecimal price,
            Long subjectId,
            Integer floors,
            Integer rooms,
            Double area,
            Integer page,
            Integer size,
            String sortBy,
            String sortDirection,
            @DefaultValue("HttpStatus.OK.value()") int statusCode
    ) {
        var path = new StringBuilder(PLACEMENT_URL);
        var params = new ArrayList<String>();

        if (id != null) params.add("id=" + id);
        if (country != null && !country.isEmpty()) params.add("country=" + country);
        if (city != null && !city.isEmpty()) params.add("city=" + city);
        if (postalCode != null && !postalCode.isEmpty()) params.add("postalCode=" + postalCode);
        if (street != null && !street.isEmpty()) params.add("street=" + street);
        if (number != null && !number.isEmpty()) params.add("number=" + number);
        if (price != null) params.add("price=" + price);
        if (subjectId != null) params.add("subjectId=" + subjectId);
        if (floors != null) params.add("floors=" + floors);
        if (rooms != null) params.add("rooms=" + rooms);
        if (area != null) params.add("area=" + area);
        if (page != null) params.add("page=" + page);
        if (size != null) params.add("size=" + size);
        if (sortBy != null && !sortBy.isEmpty()) params.add("sortBy=" + sortBy);
        if (sortDirection != null && !sortDirection.isEmpty()) params.add("sortDirection=" + sortDirection);

        for (int i = 0; i < params.size(); i++) {
            path.append(i == 0 ? "?" : "&").append(params.get(i));
        }

        return given(requestSpecification)
                .when()
                .get(path.toString())
                .then()
                .statusCode(statusCode)
                .log()
                .ifValidationFails(LogDetail.ALL)
                .extract()
                .body()
                .as(PagedPlacementsDto.class);
    }

    protected PagedGroundsDto getGround(
            Long id,
            String country,
            String city,
            String postalCode,
            String street,
            String number,
            BigDecimal price,
            Double area,
            Integer page,
            Integer size,
            String sortBy,
            String sortDirection,
            @DefaultValue("HttpStatus.OK.value()") int statusCode
    ) {
        var path = new StringBuilder(GROUND_URL);
        var params = new ArrayList<String>();

        if (id != null) params.add("id=" + id);
        if (country != null && !country.isEmpty()) params.add("country=" + country);
        if (city != null && !city.isEmpty()) params.add("city=" + city);
        if (postalCode != null && !postalCode.isEmpty()) params.add("postalCode=" + postalCode);
        if (street != null && !street.isEmpty()) params.add("street=" + street);
        if (number != null && !number.isEmpty()) params.add("number=" + number);
        if (price != null) params.add("price=" + price);
        if (area != null) params.add("area=" + area);
        if (page != null) params.add("page=" + page);
        if (size != null) params.add("size=" + size);
        if (sortBy != null && !sortBy.isEmpty()) params.add("sortBy=" + sortBy);
        if (sortDirection != null && !sortDirection.isEmpty()) params.add("sortDirection=" + sortDirection);

        for (int i = 0; i < params.size(); i++) {
            path.append(i == 0 ? "?" : "&").append(params.get(i));
        }

        return given(requestSpecification)
                .when()
                .get(path.toString())
                .then()
                .statusCode(statusCode)
                .log()
                .ifValidationFails(LogDetail.ALL)
                .extract()
                .body()
                .as(PagedGroundsDto.class);
    }

}
