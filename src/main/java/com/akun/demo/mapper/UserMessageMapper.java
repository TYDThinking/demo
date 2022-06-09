package com.akun.demo.mapper;


import com.akun.demo.model.UserMessage;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @Package: com.akun.demo.mapper
 * @ClassName: UserMessageMapper
 * @Author: akun
 * @CreateTime: 2022/4/23 20:56
 * @Description: 用户详细信息
 */
@Repository("UserMessageMapper")
public interface UserMessageMapper {
    //4、根据ID号查询用户信息
    UserMessage findByUserMessageId(@Param("userId") Long userId);
    //5、添加用户：通过UserMessage对象进行添加
    int  save(UserMessage userMessage);
    //6、更新：通过UserMessage (更新用户信息)
    int  updateUserMessage(UserMessage userMessage);
    //7、删除用户：通过userId
    int  deleteUserByUserMessageId(@Param("userId")Long userId);
}
