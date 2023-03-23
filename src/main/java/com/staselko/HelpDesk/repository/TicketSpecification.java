package com.staselko.HelpDesk.repository;

import com.staselko.HelpDesk.model.entity.Ticket;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;


public class TicketSpecification implements Specification<Ticket> {
    private final List<SearchCriteria> list;

    public TicketSpecification() {
        this.list = new ArrayList<>();
    }

    public void add(SearchCriteria criteria) {
        list.add(criteria);
    }

    @Override
    public Predicate toPredicate(Root<Ticket> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        List<Predicate> predicates = new ArrayList<>();
        for (SearchCriteria criteria : list) {
            if (criteria.getOperation().equals(Operation.EQUAL)) {
                predicates.add(builder.equal(root.get(criteria.getField()), criteria.getValue()));

            } else if (criteria.getOperation().equals(Operation.LIKE)) {
                predicates.add(builder.like(root.get(criteria.getField()),
                        criteria.getValue().toString() + "%"));

            } else if (criteria.getOperation().equals(Operation.GTE)) {
                predicates.add(builder.greaterThanOrEqualTo(root.get(criteria.getField()), criteria.getValue().toString()));
            }
        }
        return builder.or(predicates.toArray(new Predicate[0]));
    }
}
