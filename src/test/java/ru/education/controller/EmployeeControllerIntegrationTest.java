package ru.education.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import ru.education.dto.EmployeeDto;
import ru.education.enums.PostType;

class EmployeeControllerIntegrationTest extends BaseTest {

    @Test
    void findNonExistedEmployee() {
        var resultListById = getEmployee(333L, null, null, null, null, null, null, HttpStatus.OK.value());
        Assertions.assertNull(resultListById.getEmployees());

        var resultListBySurname = getEmployee(null, "dto.getSurname()", null, null, null, null, null, HttpStatus.OK.value());
        Assertions.assertNull(resultListBySurname.getEmployees());

        var resultListByName = getEmployee(null, null, "dto.getName()", null, null, null, null, HttpStatus.OK.value());
        Assertions.assertNull(resultListByName.getEmployees());

        var resultListByPatronymic = getEmployee(null, null, null, "dto.getPatronymic()", null, null, null, HttpStatus.OK.value());
        Assertions.assertNull(resultListByPatronymic.getEmployees());
    }

    @Test
    void createEmployee() {
        var dto = new EmployeeDto(
                1L,
                "Тестов",
                "Тест",
                "Тестович",
                PostType.DIRECTOR.name()
        );

        createEmployee(dto, HttpStatus.CREATED.value());

        var resultListBySurname = getEmployee(null, dto.getSurname(), null, null, null, null, null, HttpStatus.OK.value());
        assertEmployeeMatchesDto(resultListBySurname.getEmployees(), dto);

        var id = resultListBySurname.getEmployees().stream().findFirst().get().getId();

        var resultListById = getEmployee(id, null, null, null, null, null, null, HttpStatus.OK.value());
        assertEmployeeMatchesDto(resultListById.getEmployees(), dto);

        var resultListByName = getEmployee(null, null, dto.getName(), null, null, null, null, HttpStatus.OK.value());
        assertEmployeeMatchesDto(resultListByName.getEmployees(), dto);

        var resultListByPatronymic = getEmployee(null, null, null, dto.getPatronymic(), null, null, null, HttpStatus.OK.value());
        assertEmployeeMatchesDto(resultListByPatronymic.getEmployees(), dto);

        var resultListByPost = getEmployee(null, null, null, null, dto.getPost(), null, null, HttpStatus.OK.value());
        assertEmployeeMatchesDto(resultListByPost.getEmployees(), dto);
    }

    @Test
    void findEmployeesWithPaginationReturnsCorrectPageTest() {
        var emp1 = new EmployeeDto();
        emp1.setSurname("Ivanov");
        emp1.setName("Ivan");
        emp1.setPatronymic("Ivanovich");
        emp1.setPost(PostType.OPERATOR.name());

        var emp2 = new EmployeeDto();
        emp2.setSurname("Petrov");
        emp2.setName("Petr");
        emp2.setPatronymic("Petrovich");
        emp2.setPost(PostType.OPERATOR.name());

        var emp3 = new EmployeeDto();
        emp3.setSurname("Sidorov");
        emp3.setName("Sidor");
        emp3.setPatronymic("Sidorovich");
        emp3.setPost(PostType.OPERATOR.name());

        createEmployee(emp1, HttpStatus.CREATED.value());
        createEmployee(emp2, HttpStatus.CREATED.value());
        createEmployee(emp3, HttpStatus.CREATED.value());

        var result = getEmployee(null, null, null, null, null, 0, 2, HttpStatus.OK.value());

        Assertions.assertNotNull(result);
        Assertions.assertEquals(2, result.getEmployees().size());
        Assertions.assertEquals(0, result.getPage());
        Assertions.assertEquals(2, result.getSize());
    }

    @Test
    void findEmployeesWithPaginationSecondPageTest() {
        var emp1 = new EmployeeDto();
        emp1.setSurname("Ivanova");
        emp1.setName("Ivana");
        emp1.setPatronymic("Ivanovicha");
        emp1.setPost(PostType.OPERATOR.name());

        var emp2 = new EmployeeDto();
        emp2.setSurname("Petrova");
        emp2.setName("Petra");
        emp2.setPatronymic("Petrovicha");
        emp2.setPost(PostType.OPERATOR.name());

        var emp3 = new EmployeeDto();
        emp3.setSurname("Sidorova");
        emp3.setName("Sidora");
        emp3.setPatronymic("Sidorovicha");
        emp3.setPost(PostType.OPERATOR.name());

        createEmployee(emp1, HttpStatus.CREATED.value());
        createEmployee(emp2, HttpStatus.CREATED.value());
        createEmployee(emp3, HttpStatus.CREATED.value());

        var result = getEmployee(null, null, null, null, null, 1, 2, HttpStatus.OK.value());

        Assertions.assertNotNull(result);
        Assertions.assertFalse(result.getEmployees().isEmpty());
        Assertions.assertEquals(1, result.getPage());
        Assertions.assertEquals(2, result.getSize());
    }

    @Test
    void findEmployeesWithPaginationFilteringAndPaginationTest() {
        var emp1 = new EmployeeDto();
        emp1.setSurname("Ivanov");
        emp1.setName("Ivan");
        emp1.setPatronymic("Ivanovich");
        emp1.setPost(PostType.OPERATOR.name());

        var emp2 = new EmployeeDto();
        emp2.setSurname("Petrov");
        emp2.setName("Petr");
        emp2.setPatronymic("Petrovich");
        emp2.setPost(PostType.OPERATOR.name());

        var emp3 = new EmployeeDto();
        emp3.setSurname("Sidorov");
        emp3.setName("Sidor");
        emp3.setPatronymic("Sidorovich");
        emp3.setPost(PostType.OPERATOR.name());

        createEmployee(emp1, HttpStatus.CREATED.value());
        createEmployee(emp2, HttpStatus.CREATED.value());
        createEmployee(emp3, HttpStatus.CREATED.value());

        var result = getEmployee(null, null, null, null, PostType.OPERATOR.name(), 0, 2, HttpStatus.OK.value());

        Assertions.assertNotNull(result);
        Assertions.assertEquals(2, result.getEmployees().size());
        Assertions.assertEquals(0, result.getPage());
        Assertions.assertEquals(2, result.getSize());

        Assertions.assertTrue(result.getEmployees().stream()
                .allMatch(e -> "OPERATOR".equals(e.getPost())));
    }

    @Test
    void createEmployeeWithoutRequiredFieldsTest() {
        var emp1 = new EmployeeDto();
        emp1.setSurname("Ivanov");
        emp1.setName("Ivan");
        emp1.setPatronymic("Ivanovich");

        var emp2 = new EmployeeDto();
        emp2.setSurname("Petrov");
        emp2.setName("Petr");
        emp2.setPost(PostType.OPERATOR.name());

        var emp3 = new EmployeeDto();
        emp3.setSurname("Sidorov");
        emp3.setPatronymic("Sidorovich");
        emp3.setPost(PostType.OPERATOR.name());

        var emp4 = new EmployeeDto();
        emp4.setName("Sidor");
        emp4.setPatronymic("Sidorovich");
        emp4.setPost(PostType.OPERATOR.name());

        createEmployee(emp1, HttpStatus.BAD_REQUEST.value());
        createEmployee(emp2, HttpStatus.BAD_REQUEST.value());
        createEmployee(emp3, HttpStatus.BAD_REQUEST.value());
        createEmployee(emp4, HttpStatus.BAD_REQUEST.value());
    }

}
