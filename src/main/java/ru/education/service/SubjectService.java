package ru.education.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.education.dto.SubjectDto;
import ru.education.entity.SubjectEntity;
import ru.education.entity.SubjectEntity_;
import ru.education.repository.SubjectRepository;
import ru.education.utils.Mapper;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;

import static ru.education.service.Utils.addIfNotNull;
import static ru.education.service.Utils.addLikeIgnoreCase;

@Slf4j
@Service
@AllArgsConstructor
public class SubjectService {

    @PersistenceContext
    private EntityManager em;
    private final SubjectRepository repository;
    private final Mapper mapper;

    @Transactional
    public void addNewSubject(SubjectDto dto) {
        this.repository.save(this.mapper.toSubjectEntity(dto));
    }

    @Transactional(
            readOnly = true
    )
    public Collection<SubjectDto> findSubjects(
            Long id,
            String country,
            String city,
            String postalCode,
            String street,
            String number,
            BigDecimal price) {
        var cb = this.em.getCriteriaBuilder();
        var cq = cb.createQuery(SubjectEntity.class);
        var root = cq.from(SubjectEntity.class);
        var predicates = new ArrayList<Predicate>();

        addSubjectPredicates(id, country, city, postalCode, street, number, price, cb, predicates, root);

        cq.select(root)
                .where(predicates.toArray(Predicate[]::new))
                .orderBy(cb.asc(root.get(SubjectEntity_.ID)));

        return em.createQuery(cq)
                .getResultList()
                .stream()
                .map(mapper::toSubjectDto)
                .toList();
    }

    static void addSubjectPredicates(
            Long id,
            String country,
            String city,
            String postalCode,
            String street,
            String number,
            BigDecimal price,
            CriteriaBuilder cb,
            Collection<Predicate> predicates,
            Path<?> subject) {
        addIfNotNull(id, v -> predicates.add(cb.equal(subject.get(SubjectEntity_.ID), v)));
        addLikeIgnoreCase(country, subject.get(SubjectEntity_.COUNTRY), cb, predicates);
        addLikeIgnoreCase(city, subject.get(SubjectEntity_.CITY), cb, predicates);
        addLikeIgnoreCase(postalCode, subject.get(SubjectEntity_.POSTAL_CODE), cb, predicates);
        addLikeIgnoreCase(street, subject.get(SubjectEntity_.STREET), cb, predicates);
        addLikeIgnoreCase(number, subject.get(SubjectEntity_.NUMBER), cb, predicates);
        addIfNotNull(price, v -> predicates.add(cb.equal(subject.get(SubjectEntity_.PRICE), v)));
    }

}
