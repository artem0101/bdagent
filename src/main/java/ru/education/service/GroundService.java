package ru.education.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.Predicate;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.education.dto.GroundDto;
import ru.education.entity.ClientToSubjectEntity;
import ru.education.entity.GroundEntity;
import ru.education.entity.GroundEntity_;
import ru.education.repository.ClientRepository;
import ru.education.repository.ClientToSubjectRepository;
import ru.education.repository.GroundRepository;
import ru.education.utils.Mapper;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
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
    public void addNewGround(GroundDto dto) {
        var client = clientRepository.getReferenceById(dto.getOwnerId());

        if (Objects.isNull(client)) {
            throw new IllegalArgumentException("Client with id=" + dto.getOwnerId() + " not found");
        }

        var groundEntity = this.mapper.toGroundEntity(dto);
        this.groundRepository.save(groundEntity);

        var clientToSubjectEntity = new ClientToSubjectEntity();
        clientToSubjectEntity.setClient(client);
        clientToSubjectEntity.setSubject(groundEntity.getSubject());
        clientToSubjectEntity.setSubjectOwner(true);

        this.clientToSubjectRepository.save(clientToSubjectEntity);
    }

    @Transactional(
            readOnly = true
    )
    public Collection<GroundDto> findGrounds(
            Long subjectId,
            String country,
            String city,
            String postalCode,
            String street,
            String number,
            BigDecimal price,
            Long id,
            Double area) {
        var cb = this.em.getCriteriaBuilder();
        var cq = cb.createQuery(GroundEntity.class);
        var root = cq.from(GroundEntity.class);
        var predicates = new ArrayList<Predicate>();

        addIfNotNull(id, v -> predicates.add(cb.equal(root.get(GroundEntity_.ID), v)));
        addIfNotNull(area, v -> predicates.add(cb.equal(root.get(GroundEntity_.AREA), v)));

        if (Objects.nonNull(subjectId) || Objects.nonNull(country) || Objects.nonNull(city) || Objects.nonNull(postalCode)
                || Objects.nonNull(street) || Objects.nonNull(number) || Objects.nonNull(price)) {
            var subject = root.join(GroundEntity_.SUBJECT);
            SubjectService.addSubjectPredicates(subjectId, country, city, postalCode, street, number, price, cb, predicates, subject);
        }

        cq.select(root)
                .where(predicates.toArray(Predicate[]::new))
                .orderBy(cb.asc(root.get(GroundEntity_.ID)));

        return em.createQuery(cq)
                .getResultList()
                .stream()
                .map(mapper::toGroundDto)
                .toList();
    }

}
