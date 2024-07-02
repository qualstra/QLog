package com.enoch.vo;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.enoch.attachment.dto.AttachmentDTO;
import com.enoch.dto.RankDTO;
import com.enoch.model.AuditTrail;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserVO {

	public Long id;
	private UUID UUID;
	private String userName;
    
    public String firstName;
    public String lastName;
    public String password;
    public String  decryptpassword;
    public String  rank;
    private String code;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "IST")
    private Date dob;
	private String cdc;
	private String passport;
	private String name;
	
    public Long companyId;
    public String shipcompany;

    public Long vesselId;
    public String vesselName;

	
	
	private Integer reportingTo;
	private Date dataExportDate;
	private Boolean isActive;
	private AuditTrail auditTrial;
    
    private AttachmentDTO prolileAttachment;
    private AttachmentDTO signature;

}
