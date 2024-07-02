package com.enoch.role.dto;

import org.springframework.beans.BeanUtils;

import com.enoch.role.model.Privillege;
import com.enoch.utils.StringUtils;
import com.enoch.utils.Transform;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class PrivillegeDTO implements Transform<Privillege> {

	private Long id;
	private String key;
	private String name;
	private boolean status;
	private boolean deflt;

	public PrivillegeDTO(String permissionStr) {
		int index = permissionStr.lastIndexOf(".");
		if (index == -1) {
			key = "";
			name = permissionStr.substring(index + 1);
		} else {
			key = permissionStr.substring(0, index);
			name = permissionStr.substring(index + 1);
		}
	}

	@Override
	public Privillege transform() {
		Privillege priv = new Privillege();
		BeanUtils.copyProperties(this, priv);
		return priv;
	}

	@Override
	public boolean equals(Object other) {
		if(other instanceof PrivillegeDTO) {
			PrivillegeDTO that = (PrivillegeDTO) other;
			return StringUtils.eqI(this.key,that.key) && 
					StringUtils.eqI(this.name,that.name) ;
		} else {
			return false;
		}

	}
	@Override
	public int hashCode() {
		return this.key.hashCode() * 31 + this.name.hashCode();
	}
}
