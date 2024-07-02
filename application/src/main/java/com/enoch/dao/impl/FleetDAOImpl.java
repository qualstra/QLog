package com.enoch.dao.impl;

import java.util.List;
import java.util.UUID;

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

import com.enoch.dao.FleetDAO;
import com.enoch.dto.CompanyDTO;
import com.enoch.dto.ShipDTO;
import com.enoch.dto.fleet.FleetDTO;
import com.enoch.dto.fleet.FleetShipDTO;
import com.enoch.exception.DataException;
import com.enoch.model.Company;
import com.enoch.model.Ship;
import com.enoch.model.fleet.Fleet;
import com.enoch.model.fleet.FleetShip;
import com.enoch.model.fleet.QFleetShip;
import com.enoch.utils.CopyUtil;
import com.enoch.utils.Transform;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringPath;

@Repository
public class FleetDAOImpl implements FleetDAO {
	protected final Log logger = LogFactory.getLog(FleetDAOImpl.class);
	@Autowired
	FleetRepo repo;
	@Autowired
	FleetShipRepo fsRepo;

	@Override
	public FleetDTO create(FleetDTO dto) {
		try {
			return repo.save(dto.transform()).transform();
		} catch (Exception e) {
			logger.error("Error Creating Fleets", e);
			throw new DataException("Error Creating fleets", e);
		}
	}

	@Override
	public FleetShipDTO addVessel(FleetDTO fleet, ShipDTO ship) {
		try {
			FleetShipDTO fleetShipDTO = new FleetShipDTO();
			fleetShipDTO.setFleet(fleet);
			fleetShipDTO.setShip(ship);
			return fsRepo.save(fleetShipDTO.transform()).transform();
		} catch (Exception e) {
			logger.error("Error Creating Fleets", e);
			throw new DataException("Error Creating fleets", e);
		}
	}

	@Override
	public FleetShipDTO removeVessel(FleetDTO fleet, ShipDTO ship) {
		try {
			FleetShip toDelete = fsRepo.loadFS(fleet.transform(), ship.transform());
			fsRepo.delete(toDelete);
			return toDelete.transform();
		} catch (Exception e) {
			logger.error("Error Creating Fleets", e);
			throw new DataException("Error Creating fleets", e);
		}
	}

	@Override
	public List<FleetShipDTO> getFleetShips(FleetDTO fleet) {
		try {
			return CopyUtil.transform(fsRepo.getFleetShips(fleet.transform()));
		} catch (Exception e) {
			logger.error("Error Creating Fleets", e);
			throw new DataException("Error Creating fleets", e);
		}
	}

	@Override
	public FleetDTO loadByName(CompanyDTO comp ,String fleetName) {
		try {
			return repo.loadByName(comp.transform(), fleetName).transform();
		} catch (Exception e) {
			logger.error("Error Creating Fleets", e);
			throw new DataException("Error Creating fleets", e);
		}
	}

	@Override
	public List<ShipDTO> loadUnassignedShips(CompanyDTO company) {
		try {
			return CopyUtil.transform(repo.loadUnassignedShips(company.transform()));
		} catch (Exception e) {
			logger.error("Error Creating Fleets", e);
			throw new DataException("Error Creating fleets", e);
		}
	}

	@Override
	public List<ShipDTO> listVessels(CompanyDTO company, UUID uid) {
		try {
			return CopyUtil.transform(repo.listVessels(company.transform(), uid));
		} catch (Exception e) {
			logger.error("Error Creating Fleets", e);
			throw new DataException("Error Creating fleets", e);
		}

	}

	@Override
	public List<FleetDTO> listAll(CompanyDTO company) {
		try {
			return CopyUtil.transform(repo.listAll(company.transform()));
		} catch (Exception e) {
			logger.error("Error listing Fleets", e);
			throw new DataException("Error listing fleets", e);
		}

	}

	@Override
	public FleetDTO loadByID(CompanyDTO comp,UUID uid) {
		try {
			return repo.findByUId(comp.transform(),uid).transform();
		} catch (Exception e) {
			logger.error("Error Creating Fleets", e);
			throw new DataException("Error Creating fleets", e);
		}
	}

}

interface FleetRepo extends JpaRepository<Fleet, Long> {
	@Query("select f from com.enoch.model.fleet.Fleet f where f.name = ?2 and f.company = ?1")
	Fleet loadByName(Company transform, String fleetName);

	@Query("select f from com.enoch.model.fleet.Fleet f where f.uuid = ?2 and f.company = ?1")
	Fleet findByUId(Company transform,UUID uid);

	@Query("select fs.ship from com.enoch.model.fleet.FleetShip fs,com.enoch.model.fleet.Fleet f where f.company = ?1 and fs.fleet = f and f.uuid = ?2")
	List<Ship> listVessels(Company transform, UUID uid);

	@Query("select f from com.enoch.model.fleet.Fleet f where f.company = ?1")
	List<Fleet> listAll(Company transform);

	@Query("select s from com.enoch.model.Ship s where s not in (select fs.ship from com.enoch.model.fleet.FleetShip fs  where fs.fleet.company = ?1 ) and s.company = ?1")
	List<Ship> loadUnassignedShips(Company company);

}

interface FleetShipRepo extends JpaRepository<FleetShip, Long>, QuerydslPredicateExecutor<FleetShip>,
		QuerydslBinderCustomizer<QFleetShip> {
	@Override
	default public void customize(QuerydslBindings bindings, QFleetShip root) {
		bindings.bind(String.class)
				.first((SingleValueBinding<StringPath, String>) StringExpression::containsIgnoreCase);
		bindings.excluding(root.id);
	}

	@Query("select f from com.enoch.model.fleet.FleetShip f where f.fleet = ?1")
	public List<FleetShip> getFleetShips(Fleet fleet);

	@Query("select f from com.enoch.model.fleet.FleetShip f where f.fleet = ?1 and f.ship = ?2")
	public FleetShip loadFS(Fleet fleet, Ship ship);
}