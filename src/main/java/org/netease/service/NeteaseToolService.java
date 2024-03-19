package org.netease.service;

import org.netease.entity.NicknameCategoryEntity;
import org.netease.util.Result;
import org.netease.vo.ConditionVo;
import org.netease.vo.NicknameCategoryVo;
import org.netease.vo.RegistrationVo;

import java.util.List;

public interface NeteaseToolService {
    List<NicknameCategoryEntity> findByCondition(RegistrationVo registrationVo);

    ConditionVo init();

    Result save(RegistrationVo registrationVo);
}
