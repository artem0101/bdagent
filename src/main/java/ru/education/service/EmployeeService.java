package ru.education.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.education.dto.EmployeeDto;
import ru.education.dto.PagedEmployeesDto;
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

   /* @Transactional(readOnly = true)
    public PagedEmployeesDto findEmployees(Long id, String surname, String name, String patronymic, PostType post) {
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
                .where(predicates.toArray(Predicate[]::new))
                .orderBy(cb.asc(root.get(EmployeeEntity_.ID)));

        var result = em.createQuery(cq).getResultList().stream()
                .map(mapper::toEmployeeDto)
                .toList();

        return new PagedEmployeesDto(result, 0, 10);
    }*/

    @Transactional(readOnly = true)
    public PagedEmployeesDto findEmployees(Long id, String surname, String name, String patronymic, PostType post, Pageable pageable) {
        // New method with pagination support
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
                .where(predicates.toArray(Predicate[]::new))
                .orderBy(cb.asc(root.get(EmployeeEntity_.ID)));

        // Apply pagination to the query
        var query = em.createQuery(cq);
        query.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
        query.setMaxResults(pageable.getPageSize());

        var result = query.getResultList().stream()
                .map(mapper::toEmployeeDto)
                .toList();

        return new PagedEmployeesDto(result, pageable.getPageNumber(), pageable.getPageSize());
    }

    private long getTotalCount(Long id, String surname, String name, String patronymic, PostType post) {
        // Helper method to get total count for pagination
        var cb = this.em.getCriteriaBuilder();
        var cq = cb.createQuery(Long.class);
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

        cq.select(cb.count(root))
                .where(predicates.toArray(Predicate[]::new));

        return em.createQuery(cq).getSingleResult();
    }

}
