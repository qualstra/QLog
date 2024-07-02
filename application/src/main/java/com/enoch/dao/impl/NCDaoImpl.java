package com.enoch.dao.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.querydsl.binding.SingleValueBinding;
import org.springframework.stereotype.Service;


import com.enoch.dao.NCDAO;
import com.enoch.dto.CompanyDTO;
import com.enoch.dto.nc.NCDTO;
import com.enoch.exception.DataException;
import com.enoch.model.Company;
import com.enoch.model.nc.NC;
import com.enoch.model.nc.QNC;
import com.enoch.utils.CopyUtil;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringPath;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;

@Service
public class NCDaoImpl implements NCDAO {

	@Autowired
	NCRepo repo;

	@Override
	public NCDTO createNC(NCDTO ncDTO) {
		try {
			return repo.save(ncDTO.transform()).transform();
		} catch (Exception e) {
			throw new DataException("Error loading NC", e);
		}
	}

	@Override
	public List<NCDTO> loadNCSForChecklistInst(UUID chkInstId) {
		try {
			return CopyUtil.map(repo.loadNCForChkInst(chkInstId), a -> a.transform());
		} catch (Exception e) {
			throw new DataException("Error loading NC for the checklist : ", e);
		}
	}
	@Override
	public NCDTO save(NCDTO nc) {
		try {
			return repo.save(nc.transform()).transform();
		} catch (Exception e) {
			throw new DataException("Error saving NC", e);
		}
	}
	@Override
	public List<NCDTO> loadNCSForChecklistInst(CompanyDTO company) {
		try {
			return CopyUtil.map(repo.loadNCForChkInst(company.transform()), a -> a.transform());
		} catch (Exception e) {
			throw new DataException("Error loading NC for the company ", e);
		}
	}

	@Override
	public int clearNCSForChecklistInst(UUID chkInstId) {
		try {
			return repo.deleteByCheckId(chkInstId);
		} catch (Exception e) {
			throw new DataException("Error deleting NC for Checklist", e);
		}
	}

}

interface NCRepo extends JpaRepository<NC, Long>, QuerydslPredicateExecutor<NC>, QuerydslBinderCustomizer<QNC> {

	@Override
	default public void customize(QuerydslBindings bindings, QNC root) {
		bindings.bind(String.class)
				.first((SingleValueBinding<StringPath, String>) StringExpression::containsIgnoreCase);
		bindings.excluding(root.id);
	}
	@Transactional(readOnly = false, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
	public int deleteByCheckId(UUID checkId);

	@Query("select nc from NC nc where nc.ship.company = ?1")
	public List<NC> loadNCForChkInst(Company company);

	@Query("select nc from NC nc where nc.checkId = ?1")
	public List<NC> loadNCForChkInst(UUID uuid);
}
