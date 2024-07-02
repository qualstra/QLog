package com.enoch.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
import org.springframework.data.domain.Sort;
import com.enoch.ApplicationContext;
import com.enoch.constant.PermissionConstant;
import com.enoch.dao.ShipDAO;
import com.enoch.dto.ShipDTO;
import com.enoch.dto.ShipSearchDTO;
import com.enoch.dto.UserDTO;
import com.enoch.exception.DataException;
import com.enoch.model.QShip;
import com.enoch.model.Ship;
import com.enoch.service.EntitlementService;
import com.enoch.service.Helper;
import com.enoch.utils.CopyUtil;
import com.enoch.utils.StringUtils;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringPath;

@Repository
public class ShipDAOimpl implements ShipDAO {

	@Autowired
	ShipRepo repo;
	@Autowired
	EntityManager em;
	@Autowired
	EntitlementService entitlementService;

	@Override
	public ShipDTO create(ShipDTO ship) {
		try {
			ship.setAuditTrial(Helper.getAuditTrail());
			return repo.save(ship.transform()).transform();
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "error while create ship", e);
		}
	}

	public List<ShipDTO> search(Predicate predicate) {
		try {
			List<Ship> result = new ArrayList<Ship>();
			if (predicate == null) {
				repo.findAll().forEach(result::add);
			} else {
				repo.findAll(predicate,Sort.by(Sort.Direction.ASC, "id")).forEach(result::add);
			}
			return CopyUtil.transform(result);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "error while listing ship", e);
		}
	}

	@Override
	public List<ShipDTO> search(ShipSearchDTO dto) {
		try {
			QShip shipp = QShip.ship;
			BooleanExpression builder = shipp.company.UUID.isNotNull();
			UserDTO loggedUser = ApplicationContext.getContext().getUser();
			if (!entitlementService.hasPermission(loggedUser.getUUID().toString(), PermissionConstant.ADMIN_ALL_COMP)) {
				UUID compId = dto.getCompany() == null ? ApplicationContext.getContext().getCompany().getUUID()
						: dto.getCompany().getUUID();
				builder = builder.and(shipp.company.UUID.eq(compId));
			}
			if (StringUtils.isNotBlank(dto.getName()))
				builder = builder.and(shipp.name.containsIgnoreCase(dto.getName()));
			if (StringUtils.isNotBlank(dto.getVesselType()))
				builder = builder.and(shipp.vesselType.matches(dto.getVesselType()));
			if (StringUtils.isNotBlank(dto.getImo()))
				builder = builder.and(shipp.imo.containsIgnoreCase(dto.getImo()));
			if (StringUtils.isNotBlank(dto.getStatus()))
				builder = builder.and(shipp.status.matches(dto.getStatus()));

			return search(builder);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "error while search ship", e);
		}
	}

	@Override
	public Optional<ShipDTO> loadShipByName(String name) throws DataException {
		try {
			return repo.loadVesselByName(name).map(a -> a.transform());
		} catch (Exception e) {
			throw new DataException("error while load ship by name", e);
		}
	}

}

interface ShipRepo extends JpaRepository<Ship, Long>, QuerydslPredicateExecutor<Ship>, QuerydslBinderCustomizer<QShip> {
	@Query("select ship from com.enoch.model.Ship ship where ship.name=?1")
	Optional<Ship> loadVesselByName(String name);

	@Override
	default public void customize(QuerydslBindings bindings, QShip root) {
		bindings.bind(String.class)
				.first((SingleValueBinding<StringPath, String>) StringExpression::containsIgnoreCase);
		bindings.excluding(root.name);
	}
}
