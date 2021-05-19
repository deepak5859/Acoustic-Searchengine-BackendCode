package com.app.model;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

public class SearchSpecification implements Specification<Customer> {
	private static final long serialVersionUID = 7465614729365169793L;
	private SearchCriteria criteria;

	public SearchSpecification(SearchCriteria criteria) {
		this.criteria = criteria;
	}

	@Override
	public Predicate toPredicate(Root<Customer> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
		if (criteria.getOperation().equalsIgnoreCase(":")) {
			if (root.get(criteria.getColumn()).getJavaType() == String.class) {
				return criteriaBuilder.like(criteriaBuilder.upper(root.<String>get(criteria.getColumn())), "%" + criteria.getValue() + "%");
			}
		}

		return null;
	}
}