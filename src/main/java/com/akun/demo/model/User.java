package com.akun.demo.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @Package: com.kkk.demo.model.entity
 * @ClassName: User
 * @Author: akun
 * @CreateTime: 2022/4/2 17:52
 * @Description:
 */
@Data                   //get、set、toString
@NoArgsConstructor      //无参构造
@AllArgsConstructor     //有参构造
public class User {
    private Integer id;            //用户ID
    private String username;    //用户名
    private String password;    //密码
    private String avatar;      //头像
    private String number;       //手机号
    private String email;       //邮箱
    @JsonProperty("create_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;    //创建时间
    private Date updateTime;    //更新时间
    private Integer groupId;    //部门id

    public User(Integer id, String password, String email) {
        this.id = id;
        this.password = password;
        this.email = email;
    }
    public User(Integer id, String password) {
        this.id = id;
        this.password = password;
    }

}
