package org.netease.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.github.classgraph.json.Id;
import lombok.Data;

@Data
@TableName(value = "occupation_category")
public class OccupationCategoryEntity {

    @Id
    private Integer id;

    private String name;  //职业名称

    // ...

}