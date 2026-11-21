package ru.education.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.education.entity.ClientEntity;

import java.util.Optional;

@Repository
@Transactional
public interface ClientRepository extends JpaRepository<ClientEntity, Long>, JpaSpecificationExecutor<ClientEntity> {

    @Query("SELECT с FROM ClientEntity с WHERE с.id = :id AND с.isDeleted = :isDeleted")
    Optional<ClientEntity> findByIdAndIsDeleted(long id, Boolean isDeleted);
}
