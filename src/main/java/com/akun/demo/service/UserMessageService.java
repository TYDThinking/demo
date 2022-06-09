package com.akun.demo.service;

import com.akun.demo.model.User;
import com.akun.demo.model.UserMessage;

/**
 * @Package: com.akun.demo.service
 * @ClassName: UserMessageService
 * @Author: akun
 * @CreateTime: 2022/4/24 9:25
 * @Description:
 */
public interface UserMessageService {
    //3、查询呼应：通过Id
    UserMessage queryUserMessageById(Long id);

    //4、调用更新UserMessage对象的：业务层代码
    int updateUserMessage(UserMessage userMessage);

    //5、添加用户：通过UserMessage对象进行添加
    int insertUserByUserMessage(UserMessage userMessage);

    //6、删除用户：通过userId
    int delUserByUserMessageId(Long userId);
}
