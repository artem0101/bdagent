package ru.education.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.Predicate;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalUnit;
import java.util.ArrayList;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.education.dto.ClientDto;
import ru.education.dto.paged.PagedClientsDto;
import ru.education.entity.ClientEntity;
import ru.education.entity.ClientEntity_;
import ru.education.repository.ClientRepository;
import ru.education.utils.Mapper;

import static ru.education.service.Utils.addIfNotNull;
import static ru.education.service.Utils.addLikeIgnoreCase;

@Slf4j
@Service
@AllArgsConstructor
public class ClientService {

    @PersistenceContext
    private EntityManager em;
    private final ClientRepository clientRepository;
    private final Mapper mapper;

    @Transactional
    public void addNewClient(ClientDto dto) {
        var birthday = dto.getBirthday().atZone(ZoneOffset.UTC).toLocalDate();
        dto.setBirthday(birthday.atStartOfDay(ZoneOffset.UTC).toInstant());

        this.clientRepository.save(this.mapper.toClientEntity(dto));
    }

    @Transactional(readOnly = true)
    public PagedClientsDto findClients(
            Long id,
            String surname,
            String name,
            String patronymic,
            String email,
            String phoneNumber,
            Instant birthday,
            Pageable pageable) {
        var cb = this.em.getCriteriaBuilder();
        var cq = cb.createQuery(ClientEntity.class);
        var root = cq.from(ClientEntity.class);
        var predicates = new ArrayList<Predicate>();

        addIfNotNull(id, v -> predicates.add(cb.equal(root.get(ClientEntity_.ID), v)));
        addLikeIgnoreCase(surname, root.get(ClientEntity_.SURNAME), cb, predicates);
        addLikeIgnoreCase(name, root.get(ClientEntity_.NAME), cb, predicates);
        addLikeIgnoreCase(patronymic, root.get(ClientEntity_.PATRONYMIC), cb, predicates);
        addLikeIgnoreCase(email, root.get(ClientEntity_.EMAIL), cb, predicates);
        addLikeIgnoreCase(phoneNumber, root.get(ClientEntity_.PHONE_NUMBER), cb, predicates);

        if (birthday != null) {
            var birthdayTemp = birthday.atZone(ZoneOffset.UTC).toLocalDate();
            var newBirthdayTemp = birthdayTemp.atStartOfDay(ZoneOffset.UTC).toInstant();
            predicates.add(cb.equal(root.get(ClientEntity_.BIRTHDAY), newBirthdayTemp));
        }

        var order = pageable.getSort().get().findFirst().get();

        var sortedDirection = order.getDirection();
        var sortedProperty = order.getProperty();

        cq.select(root)
                .where(predicates.toArray(Predicate[]::new));

        if (sortedDirection == Sort.Direction.DESC) {
            cq.orderBy(cb.desc(root.get(getProperty(sortedProperty))));
        } else {
            cq.orderBy(cb.asc(root.get(getProperty(sortedProperty))));
        }

        var query = em.createQuery(cq);
        query.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
        query.setMaxResults(pageable.getPageSize());

        var result = query.getResultList()
                .stream()
                .map(mapper::toClientDto)
                .toList();

        return new PagedClientsDto(result, pageable.getPageNumber(), pageable.getPageSize());
    }

    public ClientEntity findClientById(long clientId) {
        return clientRepository.getReferenceById(clientId);
    }

    private String getProperty(String property) {
        return switch (property) {
            case ClientEntity_.ID -> ClientEntity_.ID;
            case ClientEntity_.SURNAME -> ClientEntity_.SURNAME;
            case ClientEntity_.NAME -> ClientEntity_.NAME;
            case ClientEntity_.PATRONYMIC -> ClientEntity_.PATRONYMIC;
            case ClientEntity_.EMAIL -> ClientEntity_.EMAIL;
            case ClientEntity_.PHONE_NUMBER -> ClientEntity_.PHONE_NUMBER;
            default -> throw new RuntimeException("incorrectPropery");
        };
    }
}

