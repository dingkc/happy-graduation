package com.bttc.HappyGraduation.business.friend.service.impl;

import com.bttc.HappyGraduation.business.friend.service.interfaces.IRequestApproveHistorySV;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * @author Dk
 * @date 17:54 2019/4/22.
 */
@Service
@Transactional(rollbackOn = Exception.class)
public class RequestApproveHistorySVImpl implements IRequestApproveHistorySV {
}