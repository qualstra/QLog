package com.enoch.service;

import java.util.List;

import com.enoch.ApplicationContext;
import com.enoch.dto.CompanyDTO;
import com.enoch.dto.RankDTO;
import com.enoch.model.Rank;

public interface RankService {

	public static RankDTO ITADMIN = new RankDTO("ITADMIN","ITADMIN","ITADMIN",false,true,null);
	
	
	default  public List<RankDTO> getRanks(){
		return getRanks(ApplicationContext.getContext().getCompany());
	}
	default List<RankDTO> addRanks(RankDTO... dtos){
		return addRanks(ApplicationContext.getContext().getCompany(),dtos);
	}
	public List<RankDTO> getRanks(CompanyDTO comp);
	
	List<RankDTO> addRanks(CompanyDTO comp, RankDTO... dtos);
	public List<RankDTO> searchRanks(RankDTO dto);


}
