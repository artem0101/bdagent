package ru.education.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.Predicate;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.education.dto.HouseDto;
import ru.education.entity.ApartmentEntity_;
import ru.education.entity.ClientToSubjectEntity;
import ru.education.entity.HouseEntity;
import ru.education.entity.HouseEntity_;
import ru.education.repository.ClientRepository;
import ru.education.repository.ClientToSubjectRepository;
import ru.education.repository.HouseRepository;
import ru.education.utils.Mapper;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

import static ru.education.service.Utils.addIfNotNull;

@Slf4j
@Service
@AllArgsConstructor
public class HouseService {

    @PersistenceContext
    private EntityManager em;
    private final HouseRepository houseRepository;
    private final ClientRepository clientRepository;
    private final ClientToSubjectRepository clientToSubjectRepository;
    private final Mapper mapper;

    @Transactional
    public void addNewHouse(HouseDto dto) {
        var client = clientRepository.getReferenceById(dto.getOwnerId());

        if (Objects.isNull(client)) {
            throw new IllegalArgumentException("Client with id=" + dto.getOwnerId() + " not found");
        }

        var houseEntity = this.mapper.toHouseEntity(dto);
        this.houseRepository.save(houseEntity);

        var clientToSubjectEntity = new ClientToSubjectEntity();
        clientToSubjectEntity.setClient(client);
        clientToSubjectEntity.setSubject(houseEntity.getSubject());
        clientToSubjectEntity.setSubjectOwner(true);

        this.clientToSubjectRepository.save(clientToSubjectEntity);
    }

    @Transactional(
            readOnly = true
    )
    public Collection<HouseDto> findHouses(
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
            Double areaGround,
            Double areaHouse) {
        var cb = this.em.getCriteriaBuilder();
        var cq = cb.createQuery(HouseEntity.class);
        var root = cq.from(HouseEntity.class);
        var predicates = new ArrayList<Predicate>();

        addIfNotNull(id, v -> predicates.add(cb.equal(root.get(HouseEntity_.ID), v)));
        addIfNotNull(floors, v -> predicates.add(cb.equal(root.get(HouseEntity_.FLOORS), v)));
        addIfNotNull(rooms, v -> predicates.add(cb.equal(root.get(HouseEntity_.ROOMS), v)));
        addIfNotNull(areaGround, v -> predicates.add(cb.equal(root.get(HouseEntity_.AREA_GROUND), v)));
        addIfNotNull(areaHouse, v -> predicates.add(cb.equal(root.get(HouseEntity_.AREA_HOUSE), v)));

        if (Objects.nonNull(id) || Objects.nonNull(country) || Objects.nonNull(city) || Objects.nonNull(postalCode)
                || Objects.nonNull(street) || Objects.nonNull(number) || Objects.nonNull(price)) {
            var subject = root.join(HouseEntity_.SUBJECT);

            SubjectService.addSubjectPredicates(subjectId, country, city, postalCode, street, number, price, cb, predicates, subject);
        }

        cq.select(root)
                .where(predicates.toArray(Predicate[]::new))
                .orderBy(cb.asc(root.get(ApartmentEntity_.ID)));

        return em.createQuery(cq)
                .getResultList()
                .stream()
                .map(mapper::toHouseDto)
                .toList();
    }

}
