package com.akun.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Package: com.akun.demo.model
 * @ClassName: group
 * @Author: akun
 * @CreateTime: 2022/4/24 11:32
 * @Description:
 */
@Data                   //get、set、toString
@NoArgsConstructor      //无参构造
@AllArgsConstructor     //有参构造
public class group {
    private Integer id;        //主键（部门、组别id）
    private String groupName;  //部门名字或组的名字
}
