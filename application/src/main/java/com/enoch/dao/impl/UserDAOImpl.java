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
import org.springframework.stereotype.Repository;

import com.enoch.ApplicationContext;
import com.enoch.dao.UserDAO;
import com.enoch.dto.CompanyUserDTO;
import com.enoch.dto.UserDTO;
import com.enoch.dto.UserSearchDTO;
import com.enoch.exception.DataException;
import com.enoch.model.CompanyRank;
import com.enoch.model.CompanyUser;
import com.enoch.model.QCompanyRank;
import com.enoch.model.QCompanyUser;
import com.enoch.model.QUser;
import com.enoch.model.User;
import com.enoch.service.Helper;
import com.enoch.utils.CopyUtil;
import com.enoch.utils.StringUtils;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringPath;

@Repository
public class UserDAOImpl implements UserDAO {
	protected final Log logger = LogFactory.getLog(UserDAOImpl.class);
	@Autowired
	UserRepo repo;
	@Autowired
	CompanyRankRpo cRepo;

	@Override
	public UserDTO loadUserByName(String userName) throws DataException {
		try {
			repo.findAll();
			return repo.loadUserByName(userName).transform();
		} catch (Exception e) {
			throw new DataException("error while listing user", e);
		}
	}

	@Override
	public UserDTO create(UserDTO user) {
		try {
			user.setAuditTrial(Helper.getAuditTrail());
			return repo.save(user.transform()).transform();
		} catch (Exception e) {
			throw new DataException("error while create user", e);
		}
	}

	public List<CompanyUserDTO> search(Predicate predicate) {
		try {
			List<CompanyUser> result = new ArrayList<CompanyUser>();
			if (predicate == null) {
				compUserRep.findAll().forEach(result::add);
			} else {
				compUserRep.findAll(predicate).forEach(result::add);
			}

			return CopyUtil.transform(result);
		} catch (Exception e) {
			e.printStackTrace();
			throw new DataException("error while listing user", e);
		}
	}

	@Override
	/*
	 * public List<UserDTO> search(UserSearchDTO search) { User user = new User();
	 * user.setFirstName(search.getFirstName());
	 * 
	 * Example<User> exaple = Example.of(user, ExampleMatcher.matchingAny()); return
	 * transform(repo.findAll(exaple)); }
	 */
	public List<CompanyUserDTO> search(UserSearchDTO dto) {
		try {
			QCompanyUser userr = QCompanyUser.companyUser;
			
			Long qId = ApplicationContext.getContext().getCompany().getId();
			BooleanExpression builder = userr.company.id.eq(qId);
			

			if (StringUtils.isNotBlank(dto.getFirstName()))
				builder = builder.and(userr.user.firstName.containsIgnoreCase(dto.getFirstName()));
			if (StringUtils.isNotBlank(dto.getLastName()))
				builder = builder.and(userr.user.lastName.containsIgnoreCase(dto.getLastName()));
			if (StringUtils.isNotBlank(dto.getUserName()))
				builder = builder.and(userr.user.userName.containsIgnoreCase(dto.getUserName()));
			if (StringUtils.isNotBlank(dto.getRank()))
				builder = builder.and(userr.user.rank.name.equalsIgnoreCase(dto.getRank()));
			if (StringUtils.isNotBlank(dto.getPassport()))
				builder = builder.and(userr.user.passport.containsIgnoreCase(dto.getPassport()));

			return search(builder);
		} catch (Exception e) {
			throw new DataException("error while listing user", e);
		}
	}

	@Override
	public List<CompanyUserDTO> loadCompUser(String userName) {
		try {
			return CopyUtil.transform(compUserRep.loadCompUser(userName));
		} catch (Exception e) {
			throw new DataException("error while listing user", e);
		}
	}

	@Autowired
	CompanyUserRpo compUserRep;

	@Override
	public CompanyUserDTO update(CompanyUserDTO compUser) {
		try {
			return compUserRep.save(compUser.transform()).transform();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DataException("Error Associating user", e);
		}
	}

	@Override
	public List<CompanyUserDTO> search(CompanyUserDTO dto) {
		try {
			QCompanyUser compUser = QCompanyUser.companyUser;
			
			BooleanExpression builder = compUser.company.id.eq(dto.getCompany().getId());
			if (dto.getRank() != null)
				builder = builder.and(compUser.rank.eq(dto.getRank().transform()));
			if (dto.getUser() != null)
				builder = builder.and(compUser.user.eq(dto.getUser().transform()));
			if (dto.getShip() != null)
				builder = builder.and(compUser.ship.eq(dto.getShip().transform()));
			return CopyUtil.transform(compUserRep.findAll(builder));
		} catch (Exception e) {
			throw new DataException("error while searching comp user", e);
		}

	}
}

interface CompanyUserRpo extends JpaRepository<CompanyUser, Long>, QuerydslPredicateExecutor<CompanyUser>, QuerydslBinderCustomizer<QCompanyUser> {

	@Query("select compUser from com.enoch.model.CompanyUser compUser where compUser.user.userName = ?1")
	List<CompanyUser> loadCompUser(String userName);
	@Override
	default public void customize(QuerydslBindings bindings, QCompanyUser root) {
		bindings.bind(String.class)
				.first((SingleValueBinding<StringPath, String>) StringExpression::containsIgnoreCase);
		//bindings.excluding(root.id);
	}
}

interface CompanyRankRpo extends JpaRepository<CompanyRank, Long>, QuerydslPredicateExecutor<CompanyRank>,
		QuerydslBinderCustomizer<QCompanyRank> {
	@Override
	default public void customize(QuerydslBindings bindings, QCompanyRank root) {
		bindings.bind(String.class)
				.first((SingleValueBinding<StringPath, String>) StringExpression::containsIgnoreCase);
		// bindings.excluding(root.userName);
	}
}

interface UserRepo extends JpaRepository<User, Long>, QuerydslPredicateExecutor<User>, QuerydslBinderCustomizer<QUser> {
	@Query("select user from com.enoch.model.User user where user.userName=?1")
	User loadUserByName(String userName);

	@Override
	default public void customize(QuerydslBindings bindings, QUser root) {
		bindings.bind(String.class)
				.first((SingleValueBinding<StringPath, String>) StringExpression::containsIgnoreCase);
		bindings.excluding(root.userName);
	}
}