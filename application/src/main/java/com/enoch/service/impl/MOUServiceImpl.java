package com.enoch.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.enoch.dao.MouDAO;
import com.enoch.dto.MouDTO;
import com.enoch.service.MOUService;

@Service
public class MOUServiceImpl implements MOUService{

	@Autowired
	MouDAO dao;
	
	@Override
	public List<MouDTO> search() {
		return dao.search();
	}

	@Override
	public MouDTO create(MouDTO dto) {
		return dao.create(dto);
		
	}


}
