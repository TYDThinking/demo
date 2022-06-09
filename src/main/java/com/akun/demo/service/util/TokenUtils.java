package com.akun.demo.service.util;

import com.akun.demo.utils.code.SendSMCode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.Jedis;

/**
 * @Package: com.kkk.demo.service.util
 * @ClassName: CodeUtils
 * @Author: akun
 * @CreateTime: 2022/4/2 20:22
 * @Description:
 */
@Transactional
@Service("TokenUtils")
public class TokenUtils {
    @Value("${spring.redis.host}")
    private String hostaaa;
    @Value("${spring.redis.port}")
    private Integer portss;
    @Value("${spring.redis.password}")
    private String pwd;

    //验证码的校验
    public int getRedisCode(String phone,String token){
        //连接Jedis
        Jedis jedis = new Jedis(hostaaa,portss);
        jedis.auth(pwd);

        String phoneKey = "token:"+phone;
        String redistoken = jedis.get(phoneKey);
        //判断
        //判断
        if (redistoken==null){
            System.out.println("没有获得在redis中获得验证码");
            return 2;
        }
        if(redistoken.equals(token)){
            System.out.println("成功");
            jedis.close();
            return 1;
        }else {
            System.out.println("失败");
            jedis.close();
            return 0;
        }
    }
    //2、每个手机每天只能发送三次，验证码放到redis中，设置过期时间
    public  boolean savetoken(String phone, String token){
        //连接Jedis
        Jedis jedis = new Jedis(hostaaa,portss);
        jedis.auth(pwd);
        //拼接key
        String phoneKey = "token:"+phone;
        jedis.setex(phoneKey,24*60*60,token);
        jedis.close();
        return true;
    }

    public boolean deletetoken(String phone){
        //连接Jedis
        Jedis jedis = new Jedis(hostaaa,portss);
        jedis.auth(pwd);
        //拼接key
        String phoneKey = "token:"+phone;
        jedis.del(phoneKey);
        jedis.close();
        return true;
    }

    //获得redis中的code
    public  String getRedisCode(String phone){
        //从redis获取验证码
        Jedis jedis = new Jedis(hostaaa,portss);
        jedis.auth(pwd);
        String phoneKey = "token:"+phone;
        String redisCode = jedis.get(phoneKey);
        return redisCode;
    }
}
