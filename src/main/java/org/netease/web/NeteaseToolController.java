package org.netease.web;

import com.alibaba.fastjson2.JSON;
import lombok.extern.slf4j.Slf4j;
import org.netease.entity.NicknameCategoryEntity;
import org.netease.service.NeteaseToolService;
import org.netease.util.Result;
import org.netease.vo.ConditionVo;
import org.netease.vo.NicknameCategoryVo;
import org.netease.vo.RegistrationVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/Registration")
@Slf4j
public class NeteaseToolController {
    @Autowired
    private NeteaseToolService neteaseToolService;


    /**
     * 查询  根据帮派和职业ID查找对应角色
     * @param registrationVo
     * @return
     */
    @PostMapping("find")
    public List<NicknameCategoryEntity> findByCondition(@Validated @RequestBody RegistrationVo registrationVo) {
        log.info("查询条件为：{}", JSON.toJSON(registrationVo));
        return neteaseToolService.findByCondition(registrationVo);
    }

    /**
     * 初始化 加载时直接给出帮派和职业列表
     * @return
     */
    @PostMapping("init")
    public ConditionVo init() {
        return neteaseToolService.init();
    }


    @PostMapping("save")
    public Result save(@Validated @RequestBody RegistrationVo registrationVo) {
        return neteaseToolService.save(registrationVo);
    }
}
