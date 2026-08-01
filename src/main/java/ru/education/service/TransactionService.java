package ru.education.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Subquery;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.education.dto.TransactionDto;
import ru.education.entity.EmployeeEntity_;
import ru.education.entity.SubjectEntity_;
import ru.education.entity.TransactionEntity;
import ru.education.entity.TransactionEntity_;
import ru.education.repository.TransactionRepository;
import ru.education.utils.Mapper;

import java.util.ArrayList;
import java.util.Collection;

import static ru.education.service.Utils.addIfNotNull;

@Slf4j
@Service
@AllArgsConstructor
public class TransactionService {

    @PersistenceContext
    private EntityManager em;
    private final Mapper mapper;
    private final TransactionRepository transactionRepository;

    @Transactional(
            readOnly = true
    )
    public Collection<TransactionDto> findTransactions(
            Long transactionId,
            Long employeeId) {
        var cb = this.em.getCriteriaBuilder();
        var cq = cb.createQuery(TransactionEntity.class);
        var transactionRoot = cq.from(TransactionEntity.class);
        var predicates = new ArrayList<Predicate>();

        addIfNotNull(transactionId, v -> predicates.add(cb.equal(transactionRoot.get(TransactionEntity_.ID), v)));
        addIfNotNull(employeeId, v -> {
            var employee = transactionRoot.join(TransactionEntity_.employee);
            predicates.add(cb.equal(employee.get(EmployeeEntity_.ID), v));
        });

        cq.select(transactionRoot)
                .where(predicates.toArray(Predicate[]::new))
                .orderBy(cb.asc(transactionRoot.get(TransactionEntity_.ID)));

        return em.createQuery(cq)
                .getResultList()
                .stream()
                .map(mapper::toTransactionDto)
                .toList();
    }

    public Subquery<Long> findActiveTransaction(CriteriaBuilder cb, Subquery<Long> sq, Path<Long> subjectId) {
        var root = sq.from(TransactionEntity.class);
        var subject = root.join(TransactionEntity_.SUBJECT);

        var predicate = cb.equal(subject.get(SubjectEntity_.ID), subjectId);

        return sq.select(root.get(TransactionEntity_.ID)).distinct(true).where(predicate);
    }

}
