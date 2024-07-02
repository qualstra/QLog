package com.enoch.dto;

import org.springframework.beans.BeanUtils;

import com.enoch.utils.Transform;
import com.enoch.vo.UserPwdChangeVO;

import lombok.Data;

@Data
public class UserPwdChangeDTO implements Transform<UserPwdChangeVO> {
    private String username;
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
	public UserPwdChangeVO transform() {
		UserPwdChangeVO res = new UserPwdChangeVO();
		BeanUtils.copyProperties(this, res);
		return res;
	}

}
