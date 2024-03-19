package org.netease.web;

import com.alibaba.fastjson2.JSON;
import lombok.extern.slf4j.Slf4j;
import org.netease.entity.NicknameCategoryEntity;
import org.netease.service.NeteaseToolService;
import org.netease.vo.NicknameCategoryVo;
import org.netease.vo.RegistrationVo;
import org.netease.vo.WarInfoRequestVo;
import org.netease.vo.WarInfoResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/management")
@Slf4j
public class ManagementController {
    @Autowired
    private NeteaseToolService neteaseToolService;


    /**
     * 查询对应轮次报名名单
     * @param
     * @return
     */
    @PostMapping("findByWarDate")
    public Map<String, List<String>> findByWarDate(@RequestBody @Validated WarInfoRequestVo warInfoRequestVo) {
        return neteaseToolService.findByWarDate(warInfoRequestVo);
    }


}
