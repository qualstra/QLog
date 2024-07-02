/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.enoch.master.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 *
 * @author Qualstra
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CurrencyDTO implements Serializable {

    private int id;
    private String code;
    private String name;
    private String description;
    private BigDecimal minLimit;
    private BigDecimal maxLimit;
    private BigDecimal exchangeRate;
    private BigDecimal customsexchangeRate;
    private Date fromDate;
    private Date toDate;
    private Integer tenantId;

}
