package com.enoch.dao;


import java.util.List;

import com.enoch.dto.CompanyDTO;
import com.enoch.dto.RankDTO;
import com.enoch.dto.UserDTO;
import com.enoch.dto.UserSearchDTO;
import com.enoch.exception.DataException;
import com.enoch.model.Rank;
import com.querydsl.core.types.Predicate;

public interface RankDAO {
	
	List<RankDTO> getRanks(CompanyDTO company);
	List<RankDTO> addRanks(CompanyDTO company, RankDTO... dtos);
	List<RankDTO> searchRanks(CompanyDTO company, RankDTO dto);

}
