package ru.education.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.education.dto.EmployeeDto;
import ru.education.dto.paged.PagedEmployeesDto;
import ru.education.entity.EmployeeEntity;
import ru.education.entity.EmployeeEntity_;
import ru.education.enums.PostType;
import ru.education.repository.EmployeeRepository;
import ru.education.utils.Mapper;

import static ru.education.service.Utils.addIfNotNull;
import static ru.education.service.Utils.addLikeIgnoreCase;

@Slf4j
@Service
@AllArgsConstructor
public class EmployeeService {

    @PersistenceContext
    private EntityManager em;
    private final EmployeeRepository repository;
    private final Mapper mapper;

    @Transactional
    public EmployeeDto addNewEmployee(EmployeeDto dto) {
        var entity = this.mapper.toEmployeeEntity(dto);
        var savedEntity = this.repository.save(entity);
        return this.mapper.toEmployeeDto(savedEntity);
    }

    @Transactional(readOnly = true)
    public PagedEmployeesDto findEmployees(
            Long id,
            String surname,
            String name,
            String patronymic,
            PostType post,
            Pageable pageable) {
        var cb = this.em.getCriteriaBuilder();
        var cq = cb.createQuery(EmployeeEntity.class);
        var root = cq.from(EmployeeEntity.class);
        var predicates = new ArrayList<Predicate>();

        addIfNotNull(id, v -> predicates.add(cb.equal(root.get(EmployeeEntity_.ID), v)));
        addLikeIgnoreCase(surname, root.get(EmployeeEntity_.SURNAME), cb, predicates);
        addLikeIgnoreCase(name, root.get(EmployeeEntity_.NAME), cb, predicates);
        addLikeIgnoreCase(patronymic, root.get(EmployeeEntity_.PATRONYMIC), cb, predicates);

        if (Objects.nonNull(post)) {
            var postAsString = root.get(EmployeeEntity_.post).as(String.class);
            Utils.addLikeIgnoreCase(post.name(), postAsString, cb, predicates);
        }

        cq.select(root)
                .where(predicates.toArray(Predicate[]::new));

        var order = pageable.getSort().get().findFirst().get();

        var sortedDirection = order.getDirection();
        var sortedProperty = order.getProperty();

        if (sortedDirection == Sort.Direction.DESC) {
            cq.orderBy(cb.desc(root.get(getProperty(sortedProperty))));
        } else {
            cq.orderBy(cb.asc(root.get(getProperty(sortedProperty))));
        }

        var query = em.createQuery(cq);
        query.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
        query.setMaxResults(pageable.getPageSize());

        var result = query.getResultList().stream()
                .map(mapper::toEmployeeDto)
                .toList();

        return new PagedEmployeesDto(result, pageable.getPageNumber(), pageable.getPageSize());
    }

    private String getProperty(String property) {
        return switch (property) {
            case EmployeeEntity_.ID -> EmployeeEntity_.ID;
            case EmployeeEntity_.SURNAME -> EmployeeEntity_.SURNAME;
            case EmployeeEntity_.NAME -> EmployeeEntity_.NAME;
            case EmployeeEntity_.PATRONYMIC -> EmployeeEntity_.PATRONYMIC;
            case "post" -> "post";
            default -> throw new RuntimeException("incorrectProperty");
        };
    }

}
