package com.akun.demo.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data                   //get、set、toString
@NoArgsConstructor      //无参构造
@AllArgsConstructor     //有参构造
public class UserLocation {
    private Integer id;

    private User user;

    private BigDecimal longitude;

    private BigDecimal latitude;

    @JsonProperty("create_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;

    private float morningTemperature;

    private float middayTemperature;

    private float nightTemperature;

    private String currentHealthStatus;

    private String instructions;

    public UserLocation(Integer id, BigDecimal longitude, BigDecimal latitude, float morningTemperature, float middayTemperature, float nightTemperature, String currentHealthStatus, String instructions) {
        this.id = id;
        this.longitude = longitude;
        this.latitude = latitude;
//        this.createTime = new Date();
        this.morningTemperature = morningTemperature;
        this.middayTemperature = middayTemperature;
        this.nightTemperature = nightTemperature;
        this.currentHealthStatus = currentHealthStatus;
        this.instructions = instructions;
    }
    public UserLocation(BigDecimal longitude, BigDecimal latitude, float morningTemperature, float middayTemperature, float nightTemperature, String currentHealthStatus, String instructions) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.createTime = new Date();
        this.morningTemperature = morningTemperature;
        this.middayTemperature = middayTemperature;
        this.nightTemperature = nightTemperature;
        this.currentHealthStatus = currentHealthStatus;
        this.instructions = instructions;
    }
}