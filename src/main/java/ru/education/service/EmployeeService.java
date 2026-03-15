package ru.education.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.Collection;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.education.dto.EmployeeDto;
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
    private final EmployeeRepository employeeRepository;
    private final Mapper mapper;

    @Transactional
    public void addNewEmployee(EmployeeDto dto) {
        this.employeeRepository.save(this.mapper.toEmployeeEntity(dto));
    }

    @Transactional(
            readOnly = true
    )
    public Collection<EmployeeDto> findEmployeesByParamsDto(
            Long id,
            String surname,
            String name,
            String patronymic,
            PostType post) {
        var cb = this.em.getCriteriaBuilder();
        var cq = cb.createQuery(EmployeeEntity.class);
        var root = cq.from(EmployeeEntity.class);
        var predicates = new ArrayList<Predicate>();

        addIfNotNull(id, v -> predicates.add(cb.equal(root.get(EmployeeEntity_.id), v)));
        addLikeIgnoreCase(surname, root.get(EmployeeEntity_.surname), cb, predicates);
        addLikeIgnoreCase(name, root.get(EmployeeEntity_.name), cb, predicates);
        addLikeIgnoreCase(patronymic, root.get(EmployeeEntity_.patronymic), cb, predicates);

        if (post != null) {
            var postAsString = root.get(EmployeeEntity_.post).as(String.class);
            Utils.addLikeIgnoreCase(post.name(), postAsString, cb, predicates);
        }

        cq.select(root)
                .where(predicates.toArray(Predicate[]::new))
                .orderBy(cb.asc(root.get(EmployeeEntity_.ID)));

        return em.createQuery(cq)
                .getResultList()
                .stream()
                .map(mapper::toEmployeeDto)
                .toList();
    }

}
