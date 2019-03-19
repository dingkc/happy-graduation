package com.bttc.HappyGraduation.session.service.interfaces;

import com.bttc.HappyGraduation.session.pojo.po.AuthenticationPO;

import java.util.List;

public interface IAuthenticationSV {

	List<AuthenticationPO> getAuthByCode(String code);

}
