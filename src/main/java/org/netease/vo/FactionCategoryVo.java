package org.netease.vo;

import com.baomidou.mybatisplus.annotation.TableName;
import io.github.classgraph.json.Id;
import lombok.Data;

@Data
public class FactionCategoryVo {

    @Id
    private Integer id;

    private String name; //帮派名称

}