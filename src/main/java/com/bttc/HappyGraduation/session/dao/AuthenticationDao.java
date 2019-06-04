package com.bttc.HappyGraduation.session.dao;

import com.bttc.HappyGraduation.session.pojo.po.AuthenticationPO;
import com.bttc.HappyGraduation.utils.base.BaseRepository;

import java.util.List;

public interface AuthenticationDao extends BaseRepository<AuthenticationPO, Long> {

	List<AuthenticationPO> findByCode(String code);

}
