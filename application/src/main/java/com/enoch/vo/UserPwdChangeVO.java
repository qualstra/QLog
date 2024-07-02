package com.enoch.vo;

import org.springframework.beans.BeanUtils;

import com.enoch.dto.UserPwdChangeDTO;
import com.enoch.utils.Transform;

import lombok.Data;
@Data
public class UserPwdChangeVO implements Transform<UserPwdChangeDTO>{
    private String userName;
    private String password;
    private String confirmpassword;
    private String firstname;
    private String oldpassword;
    private String tenantid;
    private String id;
    private String companyid;
    private String usertype;
    private boolean isActive;
	@Override
	public UserPwdChangeDTO transform() {
		UserPwdChangeDTO res = new UserPwdChangeDTO();
		BeanUtils.copyProperties(this, res);
		return res;
	}

}
