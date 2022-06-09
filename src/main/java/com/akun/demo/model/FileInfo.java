package com.akun.demo.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class FileInfo {
    private Integer id;            //主键（用户的id）

    private String groupName;      //文件所在组

    private String remoteFilePath; //文件所在路径

    private Long fileSize;         //文件大小，用于下载文件时提供下载进度

    private String fileType;       //文件类型，作用不是很大用于显示与文件类型相同的图标

    private String oldFilename;    //文件上传前的名字，用于下载文件时指定默认文件名


}