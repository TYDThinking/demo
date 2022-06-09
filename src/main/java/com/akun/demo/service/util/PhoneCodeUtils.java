package com.akun.demo.service.util;

import com.akun.demo.utils.FastDFSUtil;
import com.akun.demo.utils.code.SendSMCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.Jedis;

import java.util.Map;

/**
 * @Package: com.kkk.demo.service.util
 * @ClassName: CodeUtils
 * @Author: akun
 * @CreateTime: 2022/4/2 20:22
 * @Description:
 */
@Transactional
@Service("PhoneCodeUtils")
public class PhoneCodeUtils {
    @Value("${spring.redis.host}")
    private String hostaaa;
    @Value("${spring.redis.port}")
    private Integer portss;
    @Value("${spring.redis.password}")
    private String pwd;
    //发送验证码
    @Autowired
    SendSMCode sendSMCode;

    //验证码的校验
    public int getRedisCode(String phone,String code){
        //从redis获取验证码
        Jedis jedis = new Jedis(hostaaa,portss);
        jedis.auth(pwd);
        String codeKey = "VerifyCode"+phone+":code";
        String redisCode = jedis.get(codeKey);
        //判断
        //判断
        if (redisCode==null){
            System.out.println("没有获得在redis中获得验证码");
            return 2;
        }
        if(redisCode.equals(code)){
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
    public  boolean verifyCode(String phone){
        //连接Jedis
        Jedis jedis = new Jedis(hostaaa,portss);
        jedis.auth(pwd);
        //拼接key
        //手机发送次数
        String countKey = "VerifyCode"+phone+":count";
        //验证码key
        String codeKey = "VerifyCode"+phone+":code";
        //每个手机每天只能发送三次
        String count = jedis.get(countKey);
        if (count == null){
        //没有发送次数，第一次发送
        //设置发送次数是1
            jedis.setex(countKey,24*60*60,"1");
        }else if (Integer.parseInt(count)<=2){
        //发送次数+1
            jedis.incr(countKey);
        }else if (Integer.parseInt(count)>2){
        //发送三次，不能在发送
            System.out.println("今天发送次数已经超过三次");
            jedis.close();
            return false;
        }

        //放开即可发送短信
        Map<String, Object> mapp = sendSMCode.SendCode(phone);
        String code = (String) mapp.get("code");
        System.out.println("code ="+code);
        if (code==null){
            //关闭redis的连接
            jedis.close();
            return false;
        }

//        //发送验证码放到redis里面
//        String vcode = sendSMCode.getCode();//code

        jedis.setex(codeKey,120,code);
        jedis.close();
        return true;
    }
    //获得redis中的code
    public  String getRedisCode(String phone){
        //从redis获取验证码
        Jedis jedis = new Jedis(hostaaa,portss);
        jedis.auth(pwd);
        String codeKey = "VerifyCode"+phone+":code";
        String redisCode = jedis.get(codeKey);
        return redisCode;
    }
}
