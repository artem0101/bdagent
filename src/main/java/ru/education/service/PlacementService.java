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
import ru.education.dto.PlacementDto;
import ru.education.dto.paged.PagedPlacementsDto;
import ru.education.entity.ClientToSubjectEntity;
import ru.education.entity.PlacementEntity;
import ru.education.entity.PlacementEntity_;
import ru.education.entity.SubjectEntity_;
import ru.education.repository.ClientRepository;
import ru.education.repository.ClientToSubjectRepository;
import ru.education.repository.PlacementRepository;
import ru.education.utils.Mapper;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Objects;

import static ru.education.service.Utils.addIfNotNull;

@Slf4j
@Service
@AllArgsConstructor
public class PlacementService {

    @PersistenceContext
    private EntityManager em;
    private final PlacementRepository placementRepository;
    private final ClientRepository clientRepository;
    private final ClientToSubjectRepository clientToSubjectRepository;
    private final Mapper mapper;

    @Transactional
    public void addNewPlacement(PlacementDto dto) {
        var client = clientRepository.getReferenceById(dto.getOwnerId());

        if (Objects.isNull(client)) {
            throw new IllegalArgumentException("Client with id=" + dto.getOwnerId() + " not found");
        }

        var placementEntity = this.mapper.toPlacementEntity(dto);
        this.placementRepository.save(placementEntity);

        var clientToSubjectEntity = new ClientToSubjectEntity();
        clientToSubjectEntity.setClient(client);
        clientToSubjectEntity.setSubject(placementEntity.getSubject());
        clientToSubjectEntity.setSubjectOwner(true);

        this.clientToSubjectRepository.save(clientToSubjectEntity);
    }

    @Transactional(
            readOnly = true
    )
    public PagedPlacementsDto findPlacements(
            Long subjectId,
            String country,
            String city,
            String postalCode,
            String street,
            String number,
            BigDecimal price,
            Long id,
            Integer floors,
            Integer rooms,
            Double area,
            Pageable pageable) {
        var cb = this.em.getCriteriaBuilder();
        var cq = cb.createQuery(PlacementEntity.class);
        var root = cq.from(PlacementEntity.class);
        var predicates = new ArrayList<Predicate>();

        addIfNotNull(id, v -> predicates.add(cb.equal(root.get(PlacementEntity_.ID), v)));
        addIfNotNull(floors, v -> predicates.add(cb.equal(root.get(PlacementEntity_.FLOORS), v)));
        addIfNotNull(rooms, v -> predicates.add(cb.equal(root.get(PlacementEntity_.ROOMS), v)));
        addIfNotNull(area, v -> predicates.add(cb.equal(root.get(PlacementEntity_.AREA), v)));

        boolean needsSubjectJoin = Objects.nonNull(subjectId) || Objects.nonNull(country) || Objects.nonNull(city) ||
                                   Objects.nonNull(postalCode) || Objects.nonNull(street) || Objects.nonNull(number) ||
                                   Objects.nonNull(price);

        Path<?> subjectJoin = null;
        if (needsSubjectJoin) {
            subjectJoin = root.join(PlacementEntity_.SUBJECT);
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
                subjectJoin = root.join(PlacementEntity_.SUBJECT);
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
                .map(mapper::toPlacementDto)
                .toList();

        return new PagedPlacementsDto(result, pageable.getPageNumber(), pageable.getPageSize());
    }

}
