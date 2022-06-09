package com.akun.demo.interceptor;

import com.akun.demo.service.util.TokenUtils;
import com.akun.demo.utils.JWTUtils;
import com.akun.demo.utils.JsonData;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * @Package: com.akun.demo.interceptor
 * @ClassName: LoginInterceptor
 * @Author: akun
 * @CreateTime: 2022/4/14 8:37
 * @Description:
 */
public class LoginInterceptor  implements HandlerInterceptor {
   @Autowired
    private TokenUtils tokenUtils;
    /**
     * 进入到controller之前的方法
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        try{
            String accessToken = request.getHeader("token");
            if (accessToken==null){
                accessToken = request.getParameter("token");
            }

            if (StringUtils.isNotBlank(accessToken)){
                Claims claims = JWTUtils.checkJWT(accessToken);
                if (claims==null){
                    //告诉登录过期（登陆失败）
                    sendJsonMessage(response, JsonData.buildError("登录过期，请重新登陆"));
                    return false;
                }
                System.out.println("success,token 已收到");
                Integer id = (Integer) claims.get("id");
                String username = (String) claims.get("username");
                String number = (String) claims.get("number");


                int i = tokenUtils.getRedisCode(number,accessToken);
                if (i!=1){
                    //告诉登录过期（登陆失败）
                    sendJsonMessage(response, JsonData.buildError("登录过期，请重新登陆"));
                    return false;
                }
                request.setAttribute("user_id",id);
                request.setAttribute("username",username);
                request.setAttribute("number",number);
//                request.setAttribute("email",email);
                return true;
            }

        }catch (Exception e){}

        //提示登陆失败
        sendJsonMessage(response, JsonData.buildError("登录过期，请重新登陆"));
        return false;
    }

    /**
     * 响应json数据给前端
     * @param response
     * @param obj
     */
    public static void sendJsonMessage(HttpServletResponse response,Object obj){
        try{
            ObjectMapper objectMapper = new ObjectMapper();
            response.setContentType("application/json;charset=utf-8");
            PrintWriter writer = response.getWriter();
            writer.print(objectMapper.writeValueAsString(obj));
            response.flushBuffer();
        }catch (Exception e){

        }
    }
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
