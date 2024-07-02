package com.enoch.dao.impl;

import static com.enoch.utils.CopyUtil.transform;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.enoch.dao.MouDAO;
import com.enoch.dto.MouDTO;
import com.enoch.model.Mou;
import com.enoch.service.Helper;

@Repository
public class MouDAOImpl implements MouDAO {

	@Autowired
	MOURepo repo;
	
	@Override
	public List<MouDTO> search() {
		return transform(repo.findAll());
	}

	@Override
	public MouDTO create(MouDTO dto) {
		dto.setAuditTrail(Helper.getAuditTrail());
		return repo.save(dto.transform()).transform();
	}

}

interface MOURepo extends JpaRepository<Mou, Long> {
	
}
