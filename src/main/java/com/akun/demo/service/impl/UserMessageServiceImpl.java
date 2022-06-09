package com.akun.demo.service.impl;

import com.akun.demo.mapper.UserMessageMapper;
import com.akun.demo.model.UserMessage;
import com.akun.demo.service.UserMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Package: com.akun.demo.service.impl
 * @ClassName: UserMessageServiceImpl
 * @Author: akun
 * @CreateTime: 2022/4/24 9:29
 * @Description:
 */
@Service("UserMessageServiceImpl")
public class UserMessageServiceImpl implements UserMessageService {

    @Autowired
    private UserMessageMapper userMessageMapper;

    @Override
    public UserMessage queryUserMessageById(Long id) {
        return userMessageMapper.findByUserMessageId(id);
    }

    @Override
    public int updateUserMessage(UserMessage userMessage) {
        return userMessageMapper.updateUserMessage(userMessage);
    }

    @Override
    public int insertUserByUserMessage(UserMessage userMessage) {
        return userMessageMapper.save(userMessage);
    }

    @Override
    public int delUserByUserMessageId(Long userId) {
        return userMessageMapper.deleteUserByUserMessageId(userId);
    }
}
