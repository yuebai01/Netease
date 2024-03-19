package org.netease.vo;

import io.github.classgraph.json.Id;
import lombok.Data;

@Data
public class OccupationCategoryVo {

    @Id
    private Integer id;

    private String name;  //职业名称

    // ...

}