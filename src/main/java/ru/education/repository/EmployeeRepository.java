package ru.education.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.education.entity.EmployeeEntity;

import java.util.Optional;

@Repository
@Transactional
public interface EmployeeRepository extends JpaRepository<EmployeeEntity, Long>, JpaSpecificationExecutor<EmployeeEntity> {

    @Query("SELECT e FROM EmployeeEntity e WHERE e.id = :id AND isDeleted = :isDeleted")
    Optional<EmployeeEntity> findByIdAndIsDeleted(long id, Boolean isDeleted);

}
