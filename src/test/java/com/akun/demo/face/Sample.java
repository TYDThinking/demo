package com.akun.demo.face;

import com.baidu.aip.face.AipFace;
import com.baidu.aip.util.Base64Util;
import org.json.JSONObject;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

/**
 * @Package: com.akun.demo.utils.face
 * @ClassName: Sample
 * @Author: akun
 * @CreateTime: 2022/4/13 19:14
 * @Description:
 */
public class Sample {
    //设置APPID/AK/SK
    public static final String APP_ID = "25959896";//你的 App ID
    public static final String API_KEY = "pgV8WHGK3Dz42HwTo8WyBIdC";//你的 Api Key
    public static final String SECRET_KEY = "TbMr2fuBtRHTuaEGxVLED7sLuwQ6rzkT";//你的 Secret Key

    public static void main(String[] args) throws Exception{
        // 初始化一个AipFace
        AipFace client = new AipFace(APP_ID, API_KEY, SECRET_KEY);

        sample(client);
//
//        // 可选：设置网络连接参数
//        client.setConnectionTimeoutInMillis(2000);
//        client.setSocketTimeoutInMillis(60000);
//
//        // 可选：设置代理服务器地址, http和socket二选一，或者均不设置
//        client.setHttpProxy("proxy_host", proxy_port);  // 设置http代理
//        client.setSocketProxy("proxy_host", proxy_port);  // 设置socket代理
//
//        // 调用接口
//        String image = "取决于image_type参数，传入BASE64字符串或URL字符串或FACE_TOKEN字符串";
//        String imageType = "BASE64";
//
//        // 人脸检测
//        JSONObject res = client.detect(image, imageType, options);
//        System.out.println(res.toString(2));

    }

    //人脸注册
    public static void sample(AipFace client) throws Exception {

        // 传入可选参数调用接口
        HashMap<String, String> options = new HashMap<String, String>();
        options.put("face_field", "age");
        options.put("max_face_num", "2");
        options.put("face_type", "LIVE");
        options.put("quality_control", "LOW");
        options.put("liveness_control", "LOW");

        // 调用接口
        String path = "C:\\Users\\lenovo\\Desktop\\23c96a7f06800b96.jpg"; //电脑本地的一张图片
        byte[] bytes = Files.readAllBytes(Paths.get(path));
        String image = Base64Util.encode(bytes);
        String imageType = "BASE64";
        String groupId = "1001"; //用户组id
        String userId = "user1"; //用户id

        // 人脸注册
        JSONObject res = client.addUser(image,imageType,groupId,userId,options);
        System.out.println(res.toString(2));

    }
}