package ru.education.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.education.dto.GroundDto;
import ru.education.dto.paged.PagedGroundsDto;
import ru.education.entity.ClientToSubjectEntity;
import ru.education.entity.GroundEntity;
import ru.education.entity.GroundEntity_;
import ru.education.entity.SubjectEntity_;
import ru.education.repository.ClientRepository;
import ru.education.repository.ClientToSubjectRepository;
import ru.education.repository.GroundRepository;
import ru.education.utils.Mapper;

import java.math.BigDecimal;
import java.util.ArrayList;

import java.util.Objects;

import static ru.education.service.Utils.addIfNotNull;

@Slf4j
@Service
@AllArgsConstructor
public class GroundService {

    @PersistenceContext
    private EntityManager em;
    private final GroundRepository groundRepository;
    private final ClientRepository clientRepository;
    private final ClientToSubjectRepository clientToSubjectRepository;
    private final Mapper mapper;

    @Transactional
    public GroundDto addNewGround(GroundDto dto) {
        var client = clientRepository.getReferenceById(dto.getOwnerId());

        var groundEntity = this.mapper.toGroundEntity(dto);
        var savedEntity = this.groundRepository.save(groundEntity);

        var clientToSubjectEntity = new ClientToSubjectEntity();
        clientToSubjectEntity.setClient(client);
        clientToSubjectEntity.setSubject(savedEntity.getSubject());
        clientToSubjectEntity.setSubjectOwner(true);

        this.clientToSubjectRepository.save(clientToSubjectEntity);

        return this.mapper.toGroundDto(savedEntity);
    }

    @Transactional(
            readOnly = true
    )
    public PagedGroundsDto findGrounds(
            Long subjectId,
            String country,
            String city,
            String postalCode,
            String street,
            String number,
            BigDecimal price,
            Long id,
            Double area,
            Pageable pageable) {
        var cb = this.em.getCriteriaBuilder();
        var cq = cb.createQuery(GroundEntity.class);
        var root = cq.from(GroundEntity.class);
        var predicates = new ArrayList<Predicate>();

        addIfNotNull(id, v -> predicates.add(cb.equal(root.get(GroundEntity_.ID), v)));
        addIfNotNull(area, v -> predicates.add(cb.equal(root.get(GroundEntity_.AREA), v)));

        boolean needsSubjectJoin = Objects.nonNull(subjectId) || Objects.nonNull(country) || Objects.nonNull(city) || 
                                   Objects.nonNull(postalCode) || Objects.nonNull(street) || Objects.nonNull(number) || 
                                   Objects.nonNull(price);
        
        Path<?> subjectJoin = null;
        if (needsSubjectJoin) {
            subjectJoin = root.join(GroundEntity_.SUBJECT);
            SubjectService.addSubjectPredicates(subjectId, country, city, postalCode, street, number, price, cb, predicates, subjectJoin);
        }

        var order = pageable.getSort().get().findFirst().get();
        var sortedProperty = order.getProperty();
        
        Path<?> orderByPath;
        if (sortedProperty.equals(SubjectEntity_.COUNTRY) || 
            sortedProperty.equals(SubjectEntity_.CITY) || 
            sortedProperty.equals(SubjectEntity_.POSTAL_CODE) || 
            sortedProperty.equals(SubjectEntity_.STREET) || 
            sortedProperty.equals(SubjectEntity_.NUMBER) ||
            sortedProperty.equals(SubjectEntity_.PRICE)) {
            if (subjectJoin == null) {
                subjectJoin = root.join(GroundEntity_.SUBJECT);
            }
            orderByPath = subjectJoin.get(sortedProperty);
        } else {
            orderByPath = root.get(sortedProperty);
        }
        
        var sortedDirection = order.getDirection();
        
        if (sortedDirection == Sort.Direction.DESC) {
            cq.orderBy(cb.desc(orderByPath));
        } else {
            cq.orderBy(cb.asc(orderByPath));
        }

        cq.where(predicates.toArray(Predicate[]::new));

        var query = em.createQuery(cq);
        query.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
        query.setMaxResults(pageable.getPageSize());

        var result = query.getResultList().stream()
                .map(mapper::toGroundDto)
                .toList();

        return new PagedGroundsDto(result, pageable.getPageNumber(), pageable.getPageSize());
    }

}
