package ru.education.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.education.entity.GroundEntity;

@Repository
@Transactional
public interface GroundRepository extends JpaRepository<GroundEntity, Long>, JpaSpecificationExecutor<GroundEntity> {
}
