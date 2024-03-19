package org.netease.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.github.classgraph.json.Id;
import lombok.Data;

@Data
@TableName(value = "faction_category")
public class FactionCategoryEntity {

    @Id
    private Integer id;

    private String name; //帮派名称

}