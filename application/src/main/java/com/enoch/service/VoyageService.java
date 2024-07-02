package com.enoch.service;

import java.util.List;

import com.enoch.dto.VoyageDTO;
import com.enoch.dto.VoyageSearchDTO;

public interface VoyageService {
	
	public VoyageDTO create(VoyageDTO dto);
	
	public List<VoyageDTO> search(VoyageSearchDTO search);
	
	public VoyageDTO load(String voyageNo);
	
}
