package org.netease.vo;

import lombok.Data;

import java.util.List;
@Data
public class ConditionVo {

    List<NicknameCategoryVo> nicknameCategoryVos;

    List<OccupationCategoryVo> occupationCategoryVos; //职业

    List<FactionCategoryVo> factionCategoryVos; //帮派

    private String date; //帮战时间
}
