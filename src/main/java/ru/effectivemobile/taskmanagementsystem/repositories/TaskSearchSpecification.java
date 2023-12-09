package ru.effectivemobile.taskmanagementsystem.repositories;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;
import ru.effectivemobile.taskmanagementsystem.dto.SearchParamsDto;
import ru.effectivemobile.taskmanagementsystem.entities.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TaskSearchSpecification implements Specification<Task> {
    private final SearchParamsDto searchParams;

    public TaskSearchSpecification(SearchParamsDto searchParams) {
        this.searchParams = searchParams;
    }

    @Override
    public Predicate toPredicate(Root<Task> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicate = new ArrayList<>();
        if (Optional.ofNullable(searchParams.getTitle()).isPresent() && !searchParams.getTitle().isBlank()) {
            predicate.add(criteriaBuilder.like(root.get("title"), "%" + searchParams.getTitle() + "%"));
        }
        if (Optional.ofNullable(searchParams.getDescription()).isPresent() && !searchParams.getDescription().isBlank()) {
            predicate.add(criteriaBuilder.like(root.get("description"), "%" + searchParams.getDescription() + "%"));
        }
        if (Optional.ofNullable(searchParams.getPriority()).isPresent() && !searchParams.getPriority().isBlank()) {
            predicate.add(criteriaBuilder.like(root.get("priority"), "%" + searchParams.getPriority() + "%"));
        }
        if (Optional.ofNullable(searchParams.getStatus()).isPresent() && !searchParams.getStatus().isBlank()) {
            predicate.add(criteriaBuilder.like(root.get("status"), "%" + searchParams.getStatus() + "%"));
        }
        return criteriaBuilder.and(predicate.toArray(new Predicate[0]));
    }
}
