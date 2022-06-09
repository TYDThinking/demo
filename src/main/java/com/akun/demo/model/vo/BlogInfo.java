package com.akun.demo.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Package: com.akun.demo.vo
 * @ClassName: BlogInfo
 * @Author: akun
 * @CreateTime: 2022/3/27 18:22
 * @Description: 用于：添加博客、更新博客（存放blog信息）
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BlogInfo {
    private Long bid;
    private String title;
    private String content;
    private String firstPicture;
    private Integer uid;
}
