package org.netease.entity;

import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.annotation.TableName;
import io.github.classgraph.json.Id;
import lombok.Data;

import java.util.Date;

@Data
@TableName(value = "user_application")
public class UserApplicationEntity {
    @Id
    private String id;
    private String userName;
    private String factionName;
    private String occupationName;
    private Date createTime;
    private Date updateTime;
    private String warTime;
    private Integer status;



}
