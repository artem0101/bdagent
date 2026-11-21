package ru.education.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.Predicate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Objects;

import lombok.AllArgsConstructor;

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

@Service
@AllArgsConstructor
public class ClientService {

    @PersistenceContext
    private EntityManager em;
    private final ClientRepository repository;
    private final Mapper mapper;

    @Transactional
    public void addNewClient(ClientDto dto) {
        var birthday = dto.getBirthday().atZone(ZoneOffset.UTC).toLocalDate();
        dto.setBirthday(birthday.atStartOfDay(ZoneOffset.UTC).toInstant());

        this.repository.save(this.mapper.toClientEntity(dto));
    }

    @Transactional
    public void deleteClient(Long id) {
        var existingEntity = this.repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Client not found with id: " + id));
        
        if (existingEntity.isDeleted()) {
            throw new RuntimeException("Client already deleted with id: " + id);
        }
        
        existingEntity.setDeleted(true);
        this.repository.save(existingEntity);
    }

    @Transactional
    public void updateClient(Long id, ClientDto dto) {
        var existingEntity = this.repository.findByIdAndIsDeleted(id,false)
                .orElse(null);
        
        if (existingEntity == null || existingEntity.isDeleted()) {
            throw new RuntimeException("Client not found with id: " + id);
        }

        if (dto.getSurname() != null) {
            existingEntity.setSurname(dto.getSurname());
        }
        if (dto.getName() != null) {
            existingEntity.setName(dto.getName());
        }
        if (dto.getPatronymic() != null) {
            existingEntity.setPatronymic(dto.getPatronymic());
        }
        if (dto.getEmail() != null) {
            existingEntity.setEmail(dto.getEmail());
        }
        if (dto.getPhoneNumber() != null) {
            existingEntity.setPhoneNumber(dto.getPhoneNumber());
        }
        
        var updatedEntity = this.repository.save(existingEntity);
    }

    @Transactional(readOnly = true)
    public PagedClientsDto findClients(
            Long id,
            String surname,
            String name,
            String patronymic,
            String email,
            String phoneNumber,
            Boolean isDeleted,
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

        if (Objects.nonNull(isDeleted)) {
            predicates.add(cb.equal(root.get(ClientEntity_.IS_DELETED), isDeleted));
        } else {
            predicates.add(cb.equal(root.get(ClientEntity_.IS_DELETED), false));
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
                .map(mapper::toClientDto)
                .toList();

        return new PagedClientsDto(result, pageable.getPageNumber(), pageable.getPageSize());
    }

    private String getProperty(String property) {
        return switch (property) {
            case ClientEntity_.ID -> ClientEntity_.ID;
            case ClientEntity_.SURNAME -> ClientEntity_.SURNAME;
            case ClientEntity_.NAME -> ClientEntity_.NAME;
            case ClientEntity_.PATRONYMIC -> ClientEntity_.PATRONYMIC;
            case ClientEntity_.EMAIL -> ClientEntity_.EMAIL;
            case ClientEntity_.PHONE_NUMBER -> ClientEntity_.PHONE_NUMBER;
            case ClientEntity_.IS_DELETED -> ClientEntity_.IS_DELETED;
            default -> throw new RuntimeException("incorrectProperty");
        };
    }
}
