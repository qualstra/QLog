package com.enoch.vo.entitlement;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class RankVO {
	private String name;
	private String code;
	private String description;
	private Boolean multiUser = false;
	private Boolean isAvailable;
}
