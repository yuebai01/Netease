package org.netease.vo;

import lombok.Data;

@Data
public class NicknameCategoryVo {

    private Integer id;

    private String name;  //用户名称

    private Integer factionCategoryId;  //帮派ID

    private Integer occupationCategoryId;  ////职业ID
}
