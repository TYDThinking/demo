package com.akun.demo.mapper;



import com.akun.demo.model.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;


@Repository("UserMapper")
public interface UserMapper {
    //1、查询用户：通过用户对象（账号、密码）
    User findUserByCode(User user);
    // 查询用户：检测是否注册
    User findUserByValue(User user);

    //2、根据手机号查询用户信息
    User findByNumber(@Param("number") String number);
    //3、根据手机号和密码查询用户信息
    User findByNumberAndPassword(@Param("number") String number, @Param("password") String password);
    //3、根据邮箱和密码查询用户信息
    User findByEmailAndPassword(@Param("email") String email, @Param("password") String password);
    //4、根据ID号查询用户信息
    User findByUserId(@Param("userId") Integer userId);
    //5、添加用户：通过user对象进行添加
    int  save(User user);
    //6、更新：通过user (更新用户信息)
    int  updateUser(User user);
    //7、删除用户：通过userId
    int  deleteUserByUserId(@Param("userId")Integer userId);
}
