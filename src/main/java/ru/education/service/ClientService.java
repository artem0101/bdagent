package ru.education.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.Predicate;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.education.dto.ClientDto;
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
        this.clientRepository.save(this.mapper.toClientEntity(dto));
    }

    @Transactional(
            readOnly = true
    )
    public Collection<ClientDto> findClients(
            Long id,
            String surname,
            String name,
            String patronymic,
            Instant birthday) {
        var cb = this.em.getCriteriaBuilder();
        var cq = cb.createQuery(ClientEntity.class);
        var root = cq.from(ClientEntity.class);
        var predicates = new ArrayList<Predicate>();

        addIfNotNull(id, v -> predicates.add(cb.equal(root.get(ClientEntity_.ID), v)));
        addLikeIgnoreCase(surname, root.get(ClientEntity_.SURNAME), cb, predicates);
        addLikeIgnoreCase(name, root.get(ClientEntity_.NAME), cb, predicates);
        addLikeIgnoreCase(patronymic, root.get(ClientEntity_.PATRONYMIC), cb, predicates);
        addIfNotNull(birthday, v -> predicates.add(cb.equal(root.get(ClientEntity_.BIRTHDAY), v)));

        cq.select(root)
                .where(predicates.toArray(Predicate[]::new))
                .orderBy(cb.asc(root.get(ClientEntity_.ID)));

        return em.createQuery(cq)
                .getResultList()
                .stream()
                .map(mapper::toClientDto)
                .toList();
    }

    public ClientEntity findClientById(long clientId) {
        return clientRepository.getReferenceById(clientId);
    }

}
