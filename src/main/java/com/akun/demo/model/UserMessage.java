package com.akun.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Package: com.akun.demo.model
 * @ClassName: UserMessage
 * @Author: akun
 * @CreateTime: 2022/4/23 20:23
 * @Description:
 */
@Data                   //get、set、toString
@NoArgsConstructor      //无参构造
@AllArgsConstructor     //有参构造
public class UserMessage {
   private Integer id;              //主键（用户id）
   private String realName;         //真实姓名
   private int gender;              //性别
   private int age;                 //年龄
   private String number;           //联系方式
   private String email;            //邮箱
   private int groupId ;            //部门Id
   private String homeAddress;      //籍贯（家乡）
   private String currentAddress;   //现住址
}
