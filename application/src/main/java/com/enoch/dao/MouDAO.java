package com.enoch.dao;

import java.util.List;

import com.enoch.dto.MouDTO;

public interface MouDAO {

	List<MouDTO> search();

	MouDTO create(MouDTO dto);

}
