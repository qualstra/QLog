package com.enoch.model;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Embeddable
@Data

public class AuditTrail{

    @Column(name = "CREATEDBY", nullable = false, length = 50, updatable = false)
    private String createdBy = "DEFUSER";
    @Column(name = "CREATEDDATE", nullable = false, updatable = false)
    private Date createdDate = new Date();
    @Column(name = "LASTUPDATEDBY", nullable = true, length = 50,insertable = false)
    private String updatedBy;
    @Column(name = "LASTUPDATEDDATE", nullable = true,insertable = false)
    private Date updatedDate;
    
    public AuditTrail(String loggeInUser){
    	this.createdBy = loggeInUser;
    	this.updatedBy = loggeInUser;
    	createdDate = new Date();
    	updatedDate = new Date();
    }

    public AuditTrail() {
	}
    
}
