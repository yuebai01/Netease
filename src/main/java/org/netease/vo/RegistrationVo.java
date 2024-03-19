package org.netease.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.models.auth.In;
import lombok.Data;

@ApiModel("报名页面vo")
@Data
public class RegistrationVo {

    private Integer factionCategoryId = 1;   //帮派ID

    private Integer occupationCategoryId =1;  //职业ID

    private Integer nicknameCategoryId;  //用户ID

    private Integer status; //0 参加  1 替补  2 请假  3 未报名

}
