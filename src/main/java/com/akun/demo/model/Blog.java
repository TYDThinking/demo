package com.akun.demo.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data                   //get、set、toString
@NoArgsConstructor      //无参构造
@AllArgsConstructor     //有参构造
public class Blog {
    private Long id; //ID

    private String title;//标题

    private String firstPicture; //首图

    private Manager manager;
    @JsonProperty("create_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;  //创建时间

    private Date updateTime;  //更新时间

    private Integer views;    //游览次数

    private String content;   //内容

    public Blog(String title, String content, String firstPicture) {
        this.title = title;
        this.content = content;
        this.firstPicture = firstPicture;
        this.views = 0;
        this.createTime = new Date();
        this.updateTime = new Date();
    }
}