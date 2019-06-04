package com.bttc.HappyGraduation.session.service.impl;

import com.bttc.HappyGraduation.session.dao.AuthenticationDao;
import com.bttc.HappyGraduation.session.pojo.po.AuthenticationPO;
import com.bttc.HappyGraduation.session.service.interfaces.IAuthenticationSV;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class AuthenticationSVImpl implements IAuthenticationSV {
	
	@Autowired
	private AuthenticationDao authenticationDao;

	@Override
	public List<AuthenticationPO> getAuthByCode(String code) {
		return authenticationDao.findByCode(code);
	}

}
