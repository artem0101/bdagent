package ru.education.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.education.entity.ClientEntity;

@Repository
@Transactional
public interface ClientRepository extends JpaRepository<ClientEntity, Long>, JpaSpecificationExecutor<ClientEntity> {
}
