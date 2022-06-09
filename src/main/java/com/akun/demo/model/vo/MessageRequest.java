package com.akun.demo.model.vo;

import lombok.Getter;
import lombok.Setter;

/**
 * @Package: com.akun.demo.model.vo
 * @ClassName: MessageRequest
 * @Author: akun
 * @CreateTime: 2022/4/24 16:01
 * @Description:
 */
@Getter
@Setter
public class MessageRequest {
    private Integer id;              //主键，用户的id
    private String realName;         //真实姓名
    private Integer gender;              //性别
    private Integer age;                 //年龄
    private String email;            //邮箱
    private Integer groupId ;            //部门Id
    private String homeAddress;      //籍贯（家乡）
    private String currentAddress;   //现住址
}
