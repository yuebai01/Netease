package org.netease.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.github.classgraph.json.Id;
import lombok.Data;

@Data
@TableName(value = "nickname_category")
public class NicknameCategoryEntity {

    @Id
    private Integer id;

    private String name;  //用户名称

    private Integer factionCategoryId;  //帮派ID

    private Integer occupationCategoryId;  ////职业ID


    private String factionCategoryName;

    private String occupationCategoryName;
    // ...

}