package com.akun.demo.controller;

import com.akun.demo.service.util.EmailCodeUtils;
import com.akun.demo.service.util.PhoneCodeUtils;
import com.akun.demo.utils.JsonData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @Package: com.akun.demo.controller
 * @ClassName: CodeController
 * @Author: akun
 * @CreateTime: 2022/4/14 0:02
 * @Description:
 */
@RestController
@RequestMapping("api/vakun/pri/utils")
public class CodeController {

    @Autowired
    private EmailCodeUtils emailCodeUtils;

    @Autowired
    private PhoneCodeUtils phoneCodeUtils;

    /**
     * 发送验证码good
     * @param number
     * @return
     */
    @RequestMapping(value = "sendPhoneCode", method = RequestMethod.POST)
    public JsonData sendPhoneCode (@RequestBody Map<String,Object> number){

        String phone = (String) number.get("number");
        System.out.println("number = " + number);
        System.out.println("phone = " + phone);
        if (phone==null){
            return JsonData.buildError("手机号为空");
        }
        //发送验证码
        boolean i = phoneCodeUtils.verifyCode(phone);
        if (!i){
            return JsonData.buildError("失败");
        }
        return JsonData.buildSuccess(phoneCodeUtils.getRedisCode(phone));
    }

    /**
     * 发送邮箱验证码good
     * @param ee
     * @return
     */
    @PostMapping("sendEmailCode")
    public JsonData sendEmailCode (@RequestBody Map<String,Object> ee){
        String email = (String) ee.get("email");
        //发送验证码
        boolean i = emailCodeUtils.verifyCode(email);
        if (!i){
            return JsonData.buildError("失败");
        }
        return JsonData.buildSuccess(emailCodeUtils.getRedisCode(email));
    }
}
