package com.akun.demo.model.dto;


import java.util.Date;

public class FaceUserInfo {

    private int id;                //主键（用户的id）
    private Integer groupId;       //部门名字或组的名字
    private Date createTime;       //创建时间

    private String faceId;         //人脸唯一Id
    private String name;           //名字
    private Integer similarValue;  //相似度
    private byte[] faceFeature;    //人脸特征

    public String getFaceId() {
        return faceId;
    }

    public void setFaceId(String faceId) {
        this.faceId = faceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSimilarValue() {
        return similarValue;
    }

    public void setSimilarValue(Integer similarValue) {
        this.similarValue = similarValue;
    }

    public byte[] getFaceFeature() {
        return faceFeature;
    }

    public void setFaceFeature(byte[] faceFeature) {
        this.faceFeature = faceFeature;
    }

}
