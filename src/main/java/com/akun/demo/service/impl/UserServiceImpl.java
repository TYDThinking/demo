package com.akun.demo.service.impl;

import com.akun.demo.mapper.UserMapper;
import com.akun.demo.model.User;
import com.akun.demo.service.UserService;
import com.akun.demo.utils.JWTUtils;
import com.akun.demo.utils.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
//import sun.java2d.pipe.SpanShapeRenderer;

import java.util.Date;

/**
 * @Package: com.akun.demo.service.impl
 * @ClassName: UserServiceImpl
 * @Author: akun
 * @CreateTime: 2022/4/11 19:13
 * @Description:
 */
@Service("UserServiceImpl")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    //1、查询用户：通过username 和 password
    @Override
    public User queryUserByCode(String username, String password) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(MD5Utils.MD5ByMySalt(password)); //用户密码：经历过加密（创建时间）

        return userMapper.findUserByCode(user);
    }
    //2、查询用户：通过number 和 password
    @Override
    public String findByNumberAndPwd(String number, String password) {
        User user = userMapper.findByNumberAndPassword(number,MD5Utils.MD5ByMySalt(password)); //用户密码：经历过加密（创建时间）
        if (user==null){
            return null;
        }else{
//            System.out.println("userId = " + user.getId());
            String token = JWTUtils.geneJsonWebToken(user);
//            redisTemplate.opsForValue().set("token:"+number+"login",token);
            return token;
        }

    }
    //2、查询用户：通过email 和 password
    @Override
    public String findByEmailAndPwd(String email, String password) {
        User user = userMapper.findByEmailAndPassword(email,MD5Utils.MD5ByMySalt(password)); //用户密码：经历过加密（创建时间）
        if (user==null){
            return null;
        }else{
            String token = JWTUtils.geneJsonWebToken(user);
            return token;
        }
    }
    //3、查询呼应：通过Id
    @Override
    public User queryUserById(Integer id) {
        return userMapper.findByUserId(id);
    }
    //4、调用更新user对象的：业务层代码
    @Override
    public int updateUserInfo(User user) {
        //实现方法：
        //4-1：通过userId获取到（原始user对象）
        //4-2：逐一判断：user对象中的newNickname,newEmail, newAvatar 是否为空，为空使用之前的值
        //4-3：对传过来的密码（加密处理），根据service层的方法
        int state = 0;              //state：作为返回值（若 state > 0 代表修改成功！）
        User oldUser = userMapper.findByUserId(user.getId());
        //判断：newEmail 是否为（null || 空）
        if(null == user.getEmail() || "".equals(user.getEmail()))
            user.setEmail(oldUser.getEmail());
        //判断：newNumber 是否为（null || 空）
        if(null == user.getNumber() || "".equals(user.getNumber()))
            user.setNumber(oldUser.getNumber());
        //判断：newAvatar 是否为（null || 空）
        if(null == user.getAvatar() || "".equals(user.getAvatar()))
            user.setAvatar(oldUser.getAvatar());
        // 加密字符串
        user.setPassword(MD5Utils.MD5ByMySalt(user.getPassword()));     //用户密码：经历过加密（创建时间）
        state = userMapper.updateUser(user);   //更新用户信息
        return state;
    }
    //5、添加用户：通过user对象进行添加
    @Override
    public int insUserByUser(User user) {
        user.setCreateTime(new Date());
        user.setUpdateTime(new Date());
//        User u = userMapper.findUserByValue(user);
//        if (u!=null){
//            return 2;
//        }
        User un = userMapper.findByNumber(user.getNumber());
        if (un!=null){
            return 0;
        }
        String password = MD5Utils.MD5ByMySalt(user.getPassword()); //用户密码：经历过二次加密（创建时间）
        user.setPassword(password);
        return userMapper.save(user);
    }
    //6、删除用户：通过userId
    @Override
    public int delUserByUserId(Integer userId) {
        return userMapper.deleteUserByUserId(userId);
    }
}
