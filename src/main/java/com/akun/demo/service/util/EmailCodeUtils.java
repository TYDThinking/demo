package com.akun.demo.service.util;

import com.akun.demo.utils.code.EmailUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.Jedis;

/**
 * @Package: com.akun.demo.service.util
 * @ClassName: CodeUtils
 * @Author: akun
 * @CreateTime: 2022/4/13 23:38
 * @Description: 发送验证码
 */
@Transactional
@Service("EmailCodeUtils")
public class EmailCodeUtils {
    @Value("${spring.redis.host}")
    private String hostaaa;
    @Value("${spring.redis.port}")
    private Integer portss;
    @Value("${spring.redis.password}")
    private String pwd;
    //验证码的校验
    public int getRedisCode(String email,String code){
        //从redis获取验证码
        Jedis jedis = new Jedis(hostaaa,portss);
        jedis.auth(pwd);
        String codeKey = "emailVerifyCode"+email+":code";
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
    public  boolean verifyCode(String email){
        //连接Jedis
        Jedis jedis = new Jedis(hostaaa,portss);
        jedis.auth(pwd);
        //拼接key
        //手机发送次数
        String countKey = "emailVerifyCode"+email+":count";
        //验证码key
        String codeKey = "emailVerifyCode"+email+":code";
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
        //发送验证码
        EmailUtil emailUtil = new EmailUtil();
        String code = emailUtil.sendAuthCodeEmail(email);
        System.out.println(code);
        if (code==null){
            //关闭redis的连接
            jedis.close();
            return false;
        }
        //发送验证码放到redis里面
//        String vcode = emailUtil.generateVerifyCodeAndLetter();//code
        jedis.setex(codeKey,60,code);//六十秒超时
        jedis.close();
        return true;
    }
    //获得redis中的code
    public  String getRedisCode(String email){
        //从redis获取验证码
        Jedis jedis = new Jedis(hostaaa,portss);
        jedis.auth(pwd);
        String codeKey = "emailVerifyCode"+email+":code";
        String redisCode = jedis.get(codeKey);
        return redisCode;
    }
}
