package org.netease.service.Impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.netease.entity.FactionCategoryEntity;
import org.netease.entity.NicknameCategoryEntity;
import org.netease.entity.OccupationCategoryEntity;
import org.netease.entity.UserApplicationEntity;
import org.netease.mapper.FactionCategoryMapper;
import org.netease.mapper.NicknameCategoryMapper;
import org.netease.mapper.OccupationCategoryMapper;
import org.netease.mapper.UserApplicationMapper;
import org.netease.service.NeteaseToolService;
import org.netease.util.DateUtil;
import org.netease.util.Result;
import org.netease.vo.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class NeteaseToolServiceImpl implements NeteaseToolService {

    @Autowired
    private NicknameCategoryMapper nicknameCategoryMapper;
    @Autowired
    private FactionCategoryMapper factionCategoryMapper;
    @Autowired
    private OccupationCategoryMapper occupationCategoryMapper;
    @Autowired
    private UserApplicationMapper userApplicationMapper;
    @Override
    public List<NicknameCategoryEntity> findByCondition(RegistrationVo registrationVo) {
        List<NicknameCategoryEntity> nicknameCategoryEntities = nicknameCategoryMapper.selectList(new LambdaQueryWrapper<NicknameCategoryEntity>()
                .eq(NicknameCategoryEntity::getFactionCategoryId,registrationVo.getFactionCategoryId())
                .eq(NicknameCategoryEntity::getOccupationCategoryId,registrationVo.getOccupationCategoryId()));
        log.info("查询结果为：{}", JSON.toJSON(nicknameCategoryEntities));
        return nicknameCategoryEntities;
    }

    @Override
    public ConditionVo init() {
        ConditionVo conditionVo = new ConditionVo();
        List<FactionCategoryVo> factionCategoryVoList = new ArrayList<>();
        List<OccupationCategoryVo> occupationCategoryVoList = new ArrayList<>();
        String nextSaturdayAfter1930 = DateUtil.getNextSaturdayAfter1930();

        List<FactionCategoryEntity> factionCategoryEntities = factionCategoryMapper.selectList(null);
        List<OccupationCategoryEntity> occupationCategoryEntities = occupationCategoryMapper.selectList(null);
        //转换对象
        for (FactionCategoryEntity factionCategoryEntity : factionCategoryEntities) {
            FactionCategoryVo factionCategoryVo = new FactionCategoryVo();
            BeanUtils.copyProperties(factionCategoryEntity, factionCategoryVo);
            factionCategoryVoList.add(factionCategoryVo);
        }
        for (OccupationCategoryEntity occupationCategoryEntity : occupationCategoryEntities) {
            OccupationCategoryVo occupationCategoryVo = new OccupationCategoryVo();
            BeanUtils.copyProperties(occupationCategoryEntity, occupationCategoryVo);
            occupationCategoryVoList.add(occupationCategoryVo);
        }

        conditionVo.setFactionCategoryVos(factionCategoryVoList);
        conditionVo.setOccupationCategoryVos(occupationCategoryVoList);
        conditionVo.setDate(nextSaturdayAfter1930);
        return  conditionVo;
    }

    @Override
    public Result save(RegistrationVo registrationVo) {
        UserApplicationEntity userApplicationEntity = new UserApplicationEntity();

        NicknameCategoryEntity nicknameCategoryEntity = nicknameCategoryMapper.selectById(registrationVo.getNicknameCategoryId());
        UserApplicationEntity entity = userApplicationMapper.selectOne(new LambdaQueryWrapper<UserApplicationEntity>()
                .eq(UserApplicationEntity::getUserName, nicknameCategoryEntity.getName())
                .eq(UserApplicationEntity::getFactionName, nicknameCategoryEntity.getFactionCategoryName())
                .eq(UserApplicationEntity::getWarTime, DateUtil.getNextSaturdayAfter1930()));

        if (entity!= null){
            entity.setStatus(1);
            entity.setUpdateTime(new Date());
            userApplicationMapper.update(entity, new LambdaQueryWrapper<UserApplicationEntity>()
                    .eq(UserApplicationEntity::getId, entity.getId()));
        }else {
            userApplicationEntity.setFactionName(nicknameCategoryEntity.getFactionCategoryName());
            userApplicationEntity.setOccupationName(nicknameCategoryEntity.getOccupationCategoryName());
            userApplicationEntity.setUserName(nicknameCategoryEntity.getName());
            userApplicationEntity.setWarTime(DateUtil.getNextSaturdayAfter1930());
            userApplicationEntity.setStatus(registrationVo.getStatus());
            userApplicationEntity.setCreateTime(new Date());
            userApplicationEntity.setUpdateTime(new Date());
            userApplicationEntity.setId(UUID.randomUUID().toString());
            userApplicationMapper.insert(userApplicationEntity);
        }
        return Result.success("已完成本轮报名");
        }

}
