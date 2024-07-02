package com.enoch.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.querydsl.binding.SingleValueBinding;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import com.enoch.dao.CompanyDAO;
import com.enoch.dto.CompanyDTO;
import com.enoch.dto.CompanySearchDTO;
import com.enoch.exception.DataException;
import com.enoch.model.Company;
import com.enoch.model.QCompany;
import com.enoch.service.Helper;
import com.enoch.utils.CopyUtil;
import com.enoch.utils.StringUtils;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringPath;

@Repository("CompanyDAO")
public class CompanyDAOImpl implements CompanyDAO {

	@Autowired
	CompanyRepo repo;

	@Override
	public CompanyDTO create(CompanyDTO company) throws DataException {
		try {
			company.setAuditTrail(Helper.getAuditTrail());
			return repo.save(company.transform()).transform();
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "error while Creating Company", e);
		}

	}

	@Override
	public List<CompanyDTO> search(CompanySearchDTO dto) {
		try {
			QCompany comp = QCompany.company;
			BooleanExpression builder = comp.UUID.isNotNull();

			if (StringUtils.isNotBlank(dto.getName()))
				builder = builder.and(comp.name.containsIgnoreCase(dto.getName()));

			return search(builder);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "error while search ship", e);
		}
	}

	@Override
	public CompanyDTO load(UUID id) {
		Optional<Company> optComp = repo.findByIdUUID(id);
		if (optComp.isPresent()) {
			return optComp.get().transform();
		} else {
			throw new DataException("Company with id dosent exist " + id);
		}

	}

	@Override
	public List<CompanyDTO> search(Predicate predicate) {
		try {
			List<Company> result = new ArrayList<Company>();
			if (predicate == null) {
				repo.findAll().forEach(result::add);
			} else {
				repo.findAll(predicate).forEach(result::add);
			}
			return CopyUtil.transform(result);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "error while listing company", e);
		}
	}

}

interface CompanyRepo
		extends JpaRepository<Company, Long>, QuerydslPredicateExecutor<Company>, QuerydslBinderCustomizer<QCompany> {

	@Query("select comp from com.enoch.model.Company comp where comp.UUID = ?1")
	Optional<Company> findByIdUUID(UUID id);

	@Override
	default public void customize(QuerydslBindings bindings, QCompany root) {
		bindings.bind(String.class)
				.first((SingleValueBinding<StringPath, String>) StringExpression::containsIgnoreCase);
		bindings.excluding(root.id);
	}
}