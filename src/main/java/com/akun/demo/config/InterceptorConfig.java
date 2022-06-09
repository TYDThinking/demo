package com.akun.demo.config;

import com.akun.demo.interceptor.CorsInterceptor;
import com.akun.demo.interceptor.LoginInterceptor;
import com.akun.demo.utils.MD5Utils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Package: com.akun.demo.config
 * @ClassName: InterceptorConfig
 * @Author: akun
 * @CreateTime: 2022/4/14 8:41
 * @Description:
 */

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    @Bean
    LoginInterceptor loginInterceptor() {
        return new LoginInterceptor();
    }

    @Bean
    CorsInterceptor corsInterceptor() {
        return new CorsInterceptor();
    }

//    @Bean
//    MD5Utils md5Utils(){
//        return new MD5Utils();
//    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        /**
         * 拦截全部路径，这个跨域需要放在最上面
         */
        registry.addInterceptor(corsInterceptor()).addPathPatterns("/**");

        //拦截全部
        registry.addInterceptor(loginInterceptor()).addPathPatterns("/api/vakun/pri/*/*/**")
                //不拦截那些路径       *    /斜杠一定要加
                .excludePathPatterns("/api/vakun/pri/user/loginByN",
                        "/api/vakun/pri/user/loginByE",
                        "/api/vakun/pri/utils/sendPhoneCode",
//                        "/api/vakun/pri/file/upload",
//                        "/api/vakun/pri/face/faceAdd",
//                        "/api/vakun/pri/face/faceSearch",
//                        "/api/vakun/pri/face/detectFaces",
//                        "/api/vakun/pri/face/**",
                        "/api/vakun/pri/user/register");


        WebMvcConfigurer.super.addInterceptors(registry);
    }
}