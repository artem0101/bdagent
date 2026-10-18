package ru.education.service;

import io.micrometer.common.util.StringUtils;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Predicate;
import java.util.Collection;
import java.util.function.Consumer;

public final class Utils {

    private Utils() {
    }

    public static <T> void addIfNotNull(T value, Consumer<T> consumer) {
        if (value != null) {
            consumer.accept(value);
        }
    }

    public static void addLikeIgnoreCase(String pattern, Expression<String> expr, CriteriaBuilder cb, Collection<Predicate> predicates) {
        if (!StringUtils.isBlank(pattern)) {
            // Check if pattern contains SQL LIKE special characters (% or _)
            var hasSpecialChars = pattern.contains("%") || pattern.contains("_");
            
            if (hasSpecialChars) {
                // Escape special characters for SQL LIKE
                var escapedPattern = pattern.replace("\\", "\\\\")
                                            .replace("%", "\\%")
                                            .replace("_", "\\_");
                var sqlPattern = "%" + escapedPattern.toLowerCase() + "%";
                predicates.add(cb.like(cb.lower(expr), sqlPattern));
            } else {
                // Exact match for patterns without special characters
                predicates.add(cb.equal(cb.lower(expr), pattern.toLowerCase()));
            }
        }
    }

}