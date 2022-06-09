package com.akun.demo.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import javax.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Base64;

/**
 * 工具类
 */
@Component
public class MD5Utils {

//    @Value("${salt}")     这里是静态变量所以@Value获取不了配置文件中的值
    private static String mySalt;
    //利用以下六行代码可以从配置文件获取值给静态变量  mySalt
    @Autowired
    private Environment env;
    @PostConstruct
    public void config() {
        mySalt = env.getProperty("salt");
    }

    /**
     * MD5加密工具类
     * @param data
     * @return
     */
    public static String MD5(String data)  {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] array = md.digest(data.getBytes("UTF-8"));
            StringBuilder sb = new StringBuilder();
            for (byte item : array) {
                sb.append(Integer.toHexString((item & 0xFF) | 0x100).substring(1, 3));
            }

            return sb.toString().toUpperCase();
        } catch (Exception exception) {
        }
        return null;

    }

    /**
     * MD5加密工具类 根据mySalt
     * @param password
     * @return
     */
    public static String MD5ByMySalt(String password){
        MessageDigest md5 = null;
        String salt = new String("");
        String newPassword = new String("");
//        mySalt="2022-04-13 23:00:34";
//         mySalt = getMySalt();
//        System.out.println("mySalt = " + mySalt);
        try {
            md5 = MessageDigest.getInstance("MD5");
            Base64.Encoder base64Encoder = Base64.getEncoder();
            newPassword = DigestUtils.md5DigestAsHex(password.getBytes());
            salt = base64Encoder.encodeToString(md5.digest(mySalt.getBytes(StandardCharsets.UTF_8)));

        } catch (Exception e) {
            e.printStackTrace();
        }
        // 加密字符串e10adc3949ba59abbe56e057f20f883eTZaFQ8muE2LtSMsC8/rPNA==
        //用户：手机号email
        String pwd =(newPassword + salt);//用户密码：经历过二次加密（创建时间）
        return pwd;
    }
}
