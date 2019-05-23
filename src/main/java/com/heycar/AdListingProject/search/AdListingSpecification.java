package com.heycar.AdListingProject.search;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.heycar.AdListingProject.model.AdListing;

public class AdListingSpecification implements Specification<AdListing> {

	private static final long serialVersionUID = 9173114549068310685L;

	private AdListing adListing;

	public AdListingSpecification(AdListing adListing) {
		super();
		this.adListing = adListing;
	}

	public Predicate toPredicate(Root<AdListing> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
		Predicate predicate = cb.disjunction();

		if (adListing.getMake() != null && !adListing.getMake().equals("")) {
			predicate.getExpressions().add(cb.equal(root.get("make"), adListing.getMake()));
		}

		if (adListing.getModel() != null && !adListing.getModel().equals("")) {
			predicate.getExpressions().add(cb.equal(root.get("model"), adListing.getModel()));
		}

		if (adListing.getColor() != null && !adListing.getColor().equals("")) {
			predicate.getExpressions().add(cb.equal(root.get("color"), adListing.getColor()));
		}

		if (adListing.getYear() > 0) {
			predicate.getExpressions().add(cb.equal(root.get("year"), adListing.getYear()));
		}

		return predicate;
	}

}
