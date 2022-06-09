package com.akun.demo.utils.code;

import com.alibaba.fastjson.JSONObject;
import com.zhenzi.sms.ZhenziSmsClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * @Package: com.wlk.utils
 * @ClassName: SendSMCode
 * @Author: lenovo
 * @CreateTime: 2021/4/22 12:05
 * @Description:
 */
@Component
public class SendSMCode {

    //这个不用改
    @Value("${zhenziyun.config.apiUrl}")
    private String apiUrl;
    //榛子云系统上获取
    @Value("${zhenziyun.config.appId}")
    private String appId;
    @Value("${zhenziyun.config.appSecret}")
    private String appSecret;
    @Value("${zhenziyun.config.templateId}")
    private String templateId;

//    @Autowired
//    private Environment env;
//    @PostConstruct
//    public void config() {
//        apiUrl = env.getProperty("zhenziyun.config.apiUrl");
//        appId = env.getProperty("zhenziyun.config.appId");
//        appSecret = env.getProperty("zhenziyun.config.appSecret");
//        templateId = env.getProperty("zhenziyun.config.templateId");
//    }

    public Map<String, Object> SendCode(String number) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            JSONObject json = null;
            //随机生成验证码六位
            String code = getCode();
            //将验证码通过榛子云接口发送至手机
            ZhenziSmsClient client = new ZhenziSmsClient(apiUrl, appId, appSecret);
            //
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("number", number);
            params.put("templateId",templateId);
            //这个参数就是短信模板上那两个参数
            String[] templateParams = {code, "验证码,5"};
            params.put("templateParams", templateParams);
            String result = client.send(params);

            System.out.println("code = "+code);
            System.out.println("result = "+result);
            json = JSONObject.parseObject(result);
            if (json.getIntValue("code") != 0) {//发送短信失败
                map.put("1", "发送失败");
                return map;
            }
            /*
            //将验证码存到session中,同时存入创建时间
            //以json存放，这里使用的是阿里的fastjson
            json = new JSONObject();
            json.put("number", number);
            json.put("code", code);
            json.put("createTime", System.currentTimeMillis());
            // 将认证码存入SESSION
            httpSession.setAttribute("code", json);
            */
            map.put("0", "发送成功");
            map.put("code", code);
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            map.put("1", "发送失败");
            return map;
        }
    }
    //需要改的问题，首位验证码不能为零

    //1、生成6位数字验证码
    public  String getCode(){
        Random random = new Random();
        String code = "";

        for(int i=0;i<6;i++){
            int rand = random.nextInt(10);
            if (i==0&&rand==0){
                rand=1;
            }
            code += rand;
        }
        System.out.println(code);
        return code;
    }
}
