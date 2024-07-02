package com.enoch.service;

import java.util.List;

import com.enoch.dto.MouDTO;

public interface MOUService {

	List<MouDTO> search();

	MouDTO create(MouDTO dto);

}
