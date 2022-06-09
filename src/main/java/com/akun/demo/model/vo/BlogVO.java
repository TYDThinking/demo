package com.akun.demo.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @Package: com.akun.demo.vo
 * @ClassName: BlogVo
 * @Author: akun
 * @CreateTime: 2022/3/27 18:23
 * @Description: 用于查询：blog信息显示所用
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BlogVO {
    private Long id;
    private String title;
    private Date createTime;
    private String firstPicture;
    private Integer views;    //游览次数
}
