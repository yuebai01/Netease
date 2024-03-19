package org.netease.service.Impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
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
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

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
    @Transactional(rollbackFor = Exception.class)
    public String registerUserApplication(RegistrationVo registrationVo) {
        try {
            UserApplicationEntity userApplicationEntity = new UserApplicationEntity();

            NicknameCategoryEntity nicknameCategoryEntity = nicknameCategoryMapper.selectById(registrationVo.getNicknameCategoryId());
            // 对获取的nicknameCategoryEntity进行非空校验
            if (nicknameCategoryEntity == null) {
                throw new IllegalArgumentException("Nickname category not found for ID: " + registrationVo.getNicknameCategoryId());
            }

            UserApplicationEntity entity = userApplicationMapper.selectOne(Wrappers.<UserApplicationEntity>lambdaQuery()
                    .eq(UserApplicationEntity::getUserName, nicknameCategoryEntity.getName())
                    .eq(UserApplicationEntity::getFactionName, nicknameCategoryEntity.getFactionCategoryName())
                    .eq(UserApplicationEntity::getWarTime, DateUtil.getNextSaturdayAfter1930()));

            if (entity != null) {
                entity.setStatus(1);
                entity.setUpdateTime(new Date());
                userApplicationMapper.update(entity, Wrappers.<UserApplicationEntity>lambdaQuery().eq(UserApplicationEntity::getId, entity.getId()));
            } else {
                userApplicationEntity.setFactionName(nicknameCategoryEntity.getFactionCategoryName());
                userApplicationEntity.setOccupationName(nicknameCategoryEntity.getOccupationCategoryName());
                userApplicationEntity.setUserName(nicknameCategoryEntity.getName());
                userApplicationEntity.setWarTime(DateUtil.getNextSaturdayAfter1930());
                userApplicationEntity.setStatus(registrationVo.getStatus());
                userApplicationEntity.setCreateTime(new Date());
                userApplicationEntity.setUpdateTime(new Date());
                // 使用UUID生成的ID进行校验，避免重复
                String uniqueId = UUID.randomUUID().toString();
                userApplicationEntity.setId(uniqueId);
                userApplicationMapper.insert(userApplicationEntity);
            }
            return "已完成本轮报名";
        } catch (IllegalArgumentException | MybatisPlusException e) {
            // 日志记录异常，可以使用Log4j或SLF4J等
            System.err.println("Error during user application registration: " + e.getMessage());
            // 根据实际情况返回错误信息或进行其他错误处理
            return "报名失败: " + e.getMessage();
        }
    }

    @Override
    public Map<String, List<String>> findByWarDate(WarInfoRequestVo warInfoRequestVo) {

        List<UserApplicationEntity> userApplicationEntities = userApplicationMapper.selectList(new LambdaQueryWrapper<UserApplicationEntity>()
                .eq(UserApplicationEntity::getWarTime, warInfoRequestVo.getWarDate())
                .eq(UserApplicationEntity::getStatus, 1)
                .eq(UserApplicationEntity::getFactionName, warInfoRequestVo.getFactionName()));

         Map<String, List<String>> occupationUsers = new HashMap<>();
         occupationUsers.put("神相", new ArrayList<>());
         occupationUsers.put("碎梦", new ArrayList<>());
         occupationUsers.put("龙吟", new ArrayList<>());
         occupationUsers.put("素问", new ArrayList<>());
         occupationUsers.put("铁衣", new ArrayList<>());
         occupationUsers.put("血河", new ArrayList<>());
         occupationUsers.put("九灵", new ArrayList<>());


        for (UserApplicationEntity userApplicationEntity : userApplicationEntities) {
            String occupationName = userApplicationEntity.getOccupationName();
            // 对null或空字符串进行处理，确保逻辑的完整性
            if (occupationName == null || occupationName.trim().isEmpty()) {
                System.out.println("用户职业信息为空，该用户将被忽略。");
                continue;
            }
            // 使用Map的getOrDefault方法简化逻辑，并避免直接调用多次get
            occupationUsers.getOrDefault(occupationName, new ArrayList<>()).add(userApplicationEntity.getUserName());
        }
        return occupationUsers;
    }

}
