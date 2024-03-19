package org.netease.service;

import org.netease.entity.NicknameCategoryEntity;
import org.netease.util.Result;
import org.netease.vo.*;

import java.util.List;
import java.util.Map;

public interface NeteaseToolService {
    List<NicknameCategoryEntity> findByCondition(RegistrationVo registrationVo);

    ConditionVo init();

    String registerUserApplication(RegistrationVo registrationVo);

    Map<String, List<String>> findByWarDate(WarInfoRequestVo warInfoRequestVo);
}
