package com.enoch.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

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

import com.enoch.dao.VoyageDAO;
import com.enoch.dto.VoyageDTO;
import com.enoch.dto.VoyageSearchDTO;
import com.enoch.exception.DataException;
import com.enoch.model.QVoyage;
import com.enoch.model.Voyage;
import com.enoch.service.Helper;
import com.enoch.utils.CopyUtil;
import com.enoch.utils.DateUtils;
import com.enoch.utils.StringUtils;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringPath;

@Repository
public class VoyageDAOImpl implements VoyageDAO {

	@Autowired
	VoyageRepo repo;
	@Autowired
	EntityManager em;

	@Override
	public VoyageDTO create(VoyageDTO voyage) {
		try {
			voyage.setAuditTrail(Helper.getAuditTrail());
			return repo.save(voyage.transform()).transform();
		} catch (Exception e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "error while create voyage", e);
		}
	}

	public List<VoyageDTO> search(Predicate predicate) {
		try {
			List<Voyage> result = new ArrayList<Voyage>();
			if (predicate == null) {
				repo.findAll().forEach(result::add);
			} else {
				repo.findAll(predicate).forEach(result::add);
			}
			return CopyUtil.transform(result);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "error while listing voyage", e);
		}
	}

	@Override
	public List<VoyageDTO> search(VoyageSearchDTO dto) {
		try {
			QVoyage voyage = QVoyage.voyage;
			BooleanExpression builder = voyage.company.UUID.isNotNull();

			if (StringUtils.isNotBlank(dto.getVoyageNo()))
				builder = builder.and(voyage.voyageNo.containsIgnoreCase(dto.getVoyageNo()));
			if (StringUtils.isNotBlank(dto.getOrgin()))
				builder = builder.and(voyage.orgin.description.containsIgnoreCase(dto.getOrgin()));
			if (StringUtils.isNotBlank(dto.getDestination()))
				builder = builder.and(voyage.destination.description.containsIgnoreCase(dto.getDestination()));
			if (DateUtils.isNotBlank(dto.getStart()))
				builder = builder.and(voyage.atd.eq(dto.getStart()));
			if (DateUtils.isNotBlank(dto.getEnd()))
				builder = builder.and(voyage.eta.eq(dto.getEnd()));

			return search(builder);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "error while search voyage", e);
		}
	}

	@Override
	public VoyageDTO loadVoyageByName(String voyageNo) throws DataException {
		try {
			repo.findAll();
			return repo.loadVoyageByName(voyageNo).transform();
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "error while load voyage", e);
		}
	}

}

interface VoyageRepo
		extends JpaRepository<Voyage, Long>, QuerydslPredicateExecutor<Voyage>, QuerydslBinderCustomizer<QVoyage> {
	@Query("select voyage from com.enoch.model.Voyage voyage where voyage.voyageNo=?1")
	Voyage loadVoyageByName(String voyageNo);

	@Override
	default public void customize(QuerydslBindings bindings, QVoyage root) {
		bindings.bind(String.class)
				.first((SingleValueBinding<StringPath, String>) StringExpression::containsIgnoreCase);
		bindings.excluding(root.voyageNo);
	}
}