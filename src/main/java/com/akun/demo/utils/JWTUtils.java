package com.akun.demo.utils;


import com.akun.demo.model.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Jwt工具类
 * 注意点:
 * 1、生成的token, 是可以通过base64进行解密出明文信息
 * 2、base64进行解密出明文信息，修改再进行编码，则会解密失败
 * 3、无法作废已颁布的token，除非改秘钥
 */
public class JWTUtils {

    /**
     * 过期时间，一周 = 60000 * 60 * 24 * 7
     */
    private static final long EXPIRE = 60000 * 60 * 24; //一天

    /**
     * 加密秘钥
     */
    private static final String SECRET = "xdclass.net168";

    /**
     * 令牌前缀
     */
    private static final String TOKEN_PREFIX = "xdclass";

    /**
     * subject
     */
    private static final String SUBJECT = "xdclass";

    /**
     * 根据用户信息，生成令牌
     * @param user
     * @return
     */
    public  static  String geneJsonWebToken(User user){

       String token = Jwts.builder().setSubject(SUBJECT)
                .claim("id",user.getId())
                .claim("username",user.getUsername())
//                .claim("email",user.getEmail())
                .claim("number",user.getNumber())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+EXPIRE))
                .signWith(SignatureAlgorithm.HS256,SECRET).compact();

       token = TOKEN_PREFIX +token;

//       RedisTemplate redisTemplate = new RedisTemplate();
//       redisTemplate.opsForValue().set("token:"+user.getNumber(),token,60*60*24, TimeUnit.SECONDS);
        return token;
    }

    /**
     * 校验token的方法
     * @param token
     * @return
     */
    public static Claims checkJWT(String token){
        try{
            final Claims claims =  Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token.replace(TOKEN_PREFIX,"")).getBody();

            return claims;
        }catch (Exception e){
            return null;
        }
    }



}
