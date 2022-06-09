package com.akun.demo.service;

import com.akun.demo.model.User;

/**
 * @Package: com.akun.demo.service
 * @ClassName: UserService
 * @Author: akun
 * @CreateTime: 2022/4/11 19:10
 * @Description:
 */
public interface UserService {
    //1、查询用户：通过username 和 password
    User queryUserByCode(String username, String password);
    //2、查询用户：通过number 和 password
    String findByNumberAndPwd(String number, String password);
    //2、查询用户：通过eamil 和 password
    String findByEmailAndPwd(String email, String password);
    //3、查询呼应：通过Id
    User queryUserById(Integer id);

    //4、调用更新user对象的：业务层代码
    int updateUserInfo(User user);

    //5、添加用户：通过user对象进行添加
    int insUserByUser(User user);

    //6、删除用户：通过userId
    int delUserByUserId(Integer userId);
}
