package org.netease.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.lettuce.core.dynamic.annotation.Param;
import org.apache.ibatis.annotations.Mapper;
import org.netease.entity.NicknameCategoryEntity;

import java.util.List;

@Mapper
public interface NicknameCategoryMapper extends BaseMapper<NicknameCategoryEntity> {

    List<NicknameCategoryEntity> selectByFactionCategoryIdAndOccupationCategoryId(
        @Param("faction_category_id") Integer factionCategoryId,
        @Param("occupation_category_id") Integer occupationCategoryId
    );

}
