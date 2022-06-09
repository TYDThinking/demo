package com.akun.demo.model.domain;


import lombok.Data;

import java.util.Date;

@Data

public class UserFaceInfo {

    private Integer id;

    private Integer groupId;

    private String faceId;

    private String name;

    private Integer age;

    private Short gender;

    private Date createTime;

    private Date updateTime;

    private byte[] faceFeature;


}

