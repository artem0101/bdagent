package ru.education.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.Predicate;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.education.dto.ApartmentDto;
import ru.education.entity.ClientToSubjectEntity;
import ru.education.entity.ApartmentEntity;
import ru.education.entity.ApartmentEntity_;
import ru.education.entity.SubjectEntity_;
import ru.education.repository.ApartmentRepository;
import ru.education.repository.ClientToSubjectRepository;
import ru.education.utils.Mapper;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

import static ru.education.service.Utils.addIfNotNull;

@Slf4j
@Service
@AllArgsConstructor
public class ApartmentService {

    @PersistenceContext
    private EntityManager em;
    private final ApartmentRepository apartmentRepository;
    private final ClientToSubjectRepository clientToSubjectRepository;
    private final ClientService clientService;
    private final TransactionService transactionService;
    private final Mapper mapper;

    @Transactional
    public void addNewApartment(ApartmentDto dto) {
        var client = clientService.findClientById(dto.getOwnerId());

        var apartmentEntity = this.mapper.toApartmentEntity(dto);
        this.apartmentRepository.save(apartmentEntity);

        var clientToSubjectEntity = new ClientToSubjectEntity();
        clientToSubjectEntity.setClient(client);
        clientToSubjectEntity.setSubject(apartmentEntity.getSubject());
        clientToSubjectEntity.setSubjectOwner(true);

        this.clientToSubjectRepository.save(clientToSubjectEntity);
    }

    @Transactional
    public void removeApartment(long id) {
        var cb = this.em.getCriteriaBuilder();
        var cq = cb.createCriteriaDelete(ApartmentEntity.class);
        var root = cq.from(ApartmentEntity.class);
        var subject = root.join(ApartmentEntity_.SUBJECT);

        var predicate = cb.and(
                cb.equal(root.get(ApartmentEntity_.ID), id),
                cb.not(cb.exists(
                        transactionService.findActiveTransaction(
                                cb, cq.subquery(Long.class),
                                subject.get(SubjectEntity_.ID)
                        )
                ))
        );

        cq.where(predicate);
        this.em.createQuery(cq).executeUpdate();
    }

    @Transactional
    public void updateApartment(ApartmentDto dto) {
        apartmentRepository.findById(dto.getId())
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "Apartment with id=" + dto.getId() + " not found")
                );

        var apartmentEntity = mapper.toApartmentEntity(dto);
        em.persist(apartmentEntity);
    }

    @Transactional(
            readOnly = true
    )
    public Collection<ApartmentDto> findApartments(
            Long subjectId,
            String country,
            String city,
            String postalCode,
            String street,
            String number,
            BigDecimal price,
            Long id,
            Integer floor,
            Integer room) {
        var cb = this.em.getCriteriaBuilder();
        var cq = cb.createQuery(ApartmentEntity.class);
        var root = cq.from(ApartmentEntity.class);
        var predicates = new ArrayList<Predicate>();

        addIfNotNull(id, v -> predicates.add(cb.equal(root.get(ApartmentEntity_.ID), v)));
        addIfNotNull(floor, v -> predicates.add(cb.equal(root.get(ApartmentEntity_.FLOOR), v)));
        addIfNotNull(room, v -> predicates.add(cb.equal(root.get(ApartmentEntity_.ROOMS), v)));

        if (Objects.nonNull(subjectId) || Objects.nonNull(country) || Objects.nonNull(city)
                || Objects.nonNull(postalCode) || Objects.nonNull(street) || Objects.nonNull(number)
                || Objects.nonNull(price)) {
            var subject = root.join(ApartmentEntity_.SUBJECT);

            SubjectService.addSubjectPredicates(
                    subjectId,
                    country,
                    city,
                    postalCode,
                    street,
                    number,
                    price,
                    cb,
                    predicates,
                    subject
            );
        }

        cq.select(root)
                .where(predicates.toArray(Predicate[]::new))
                .orderBy(cb.asc(root.get(ApartmentEntity_.ID)));

        return em.createQuery(cq)
                .getResultList()
                .stream()
                .map(mapper::toApartmentDto)
                .toList();
    }

}
