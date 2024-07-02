package com.enoch.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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

import com.enoch.dao.RankDAO;
import com.enoch.dto.CompanyDTO;
import com.enoch.dto.RankDTO;
import com.enoch.exception.DataException;
import com.enoch.model.Company;
import com.enoch.model.CompanyRank;
import com.enoch.model.QCompanyRank;
import com.enoch.model.QRank;
import com.enoch.model.Rank;
import com.enoch.service.Helper;
import com.enoch.utils.CopyUtil;
import com.enoch.utils.StringUtils;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringPath;

@Repository
public class RankDAOImpl implements RankDAO {
	protected final Log logger = LogFactory.getLog(RankDAOImpl.class);

	@Autowired
	CompanyRankRpo cRepo;

	@Autowired
	RankRepo repo;
	@Autowired
	CompanyRankRpo CRrepo;
	@Override
	public List<RankDTO> getRanks(CompanyDTO company) {
		try {
			return CopyUtil.transform(repo.getRanks(company.transform()));
		} catch (Exception e) {
			logger.error("Error loading ranks", e);
			throw new DataException("Error loading ranks", e);
		}
	}

	private List<RankDTO> search(Predicate predicate) {
		try {
			List<Rank> result = new ArrayList<Rank>();
			if (predicate == null) {
				repo.findAll().forEach(result::add);
			} else {
				CRrepo.findAll(predicate).forEach(a -> result.add(a.getRank()));
			}
			return CopyUtil.transform(result);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "error while listing user", e);
		}
	}

	@Override
	public List<RankDTO> searchRanks(CompanyDTO company, RankDTO dto) {
		try {
			QCompanyRank qCompanyRank = QCompanyRank.companyRank;
			QRank qRank = qCompanyRank.rank;
			
			Long qId = company.getId();
			BooleanExpression builder = qCompanyRank.company.id.eq(qId);
			if (StringUtils.isNotBlank(dto.getName()))
				builder = builder.and(qRank.name.containsIgnoreCase(dto.getName()));
			if (StringUtils.isNotBlank(dto.getCode()))
				builder = builder.and(qRank.code.containsIgnoreCase(dto.getCode()));
			if (StringUtils.isNotBlank(dto.getDescription()))
				builder = builder.and(qRank.description.containsIgnoreCase(dto.getDescription()));
			return search(builder);
		} catch (Exception e) {
			throw new DataException("error while searching Ranks", e);
		}

	}

	@Override
	public List<RankDTO> addRanks(CompanyDTO company, RankDTO... dtos) {
		try {
			List<RankDTO> result = new ArrayList<RankDTO>();
			List<RankDTO> failures = new ArrayList<RankDTO>();
			for (RankDTO rankDTO : dtos) {
				try {
					rankDTO.setAuditTrail(Helper.getAuditTrail());
					repo.save(rankDTO.transform());
					result.add(cRepo.save(new CompanyRank(null, company.transform(), rankDTO.transform())).getRank()
							.transform());
				} catch (Exception e) {
					failures.add(rankDTO);
				}
			}
			if (!failures.isEmpty()) {
				logger.error("Error saving Rank");
				failures.forEach(action -> logger.error(action));
			}
			return result;
		} catch (Exception e) {
			logger.error("Error loading ranks", e);
			throw new DataException("Error adding Ranks to Company", e);
		}
	}

}

interface RankRepo
		extends JpaRepository<Rank, String>, QuerydslPredicateExecutor<Rank>, QuerydslBinderCustomizer<QRank> {

	@Query("select comp_rank.rank from com.enoch.model.CompanyRank comp_rank where comp_rank.company=?1")
	List<Rank> getRanks(Company company);

	@Override
	default public void customize(QuerydslBindings bindings, QRank root) {
		bindings.bind(String.class)
				.first((SingleValueBinding<StringPath, String>) StringExpression::containsIgnoreCase);
		//bindings.excluding(root.userName);
	}

}