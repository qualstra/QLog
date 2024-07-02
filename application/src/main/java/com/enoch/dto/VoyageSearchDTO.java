package com.enoch.dto;

import java.util.Date;

import com.enoch.constant.Status;
import com.enoch.constant.VoyageStatus;
import com.enoch.master.dto.PortDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VoyageSearchDTO {
	
    private Long id;
    private ShipDTO ship;
    private Long vesseilId;
    private String voyageNo;
    private String orgin;
    private String orginDesc;
	private String destination;
	private String destDesc;
    private Date start;
    private Date end;
    private CompanyDTO company;
    private VoyageStatus status;

}
