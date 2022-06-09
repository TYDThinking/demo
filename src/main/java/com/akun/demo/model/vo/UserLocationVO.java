package com.akun.demo.model.vo;

import com.akun.demo.model.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Package: com.akun.demo.model.vo
 * @ClassName: UserLocationVO
 * @Author: akun
 * @CreateTime: 2022/5/6 21:51
 * @Description:
 */

@Data                   //get、set、toString
public class UserLocationVO {

    private Integer id;

    private Integer userId;

    private BigDecimal longitude;

    private BigDecimal latitude;

    private float morningTemperature;

    private float middayTemperature;

    private float nightTemperature;

    private String currentHealthStatus;

    private String instructions;

}
