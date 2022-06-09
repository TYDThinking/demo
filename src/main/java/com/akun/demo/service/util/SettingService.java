package com.akun.demo.service.util;


import com.akun.demo.model.User;
import com.akun.demo.service.UserService;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Random;

/**
 * @Package: com.akun.demo.service.utils
 * @ClassName: SettingService
 * @Author: akun
 * @CreateTime: 2021/9/25 19:25
 * @Description:  设置层
 */
@Transactional
@Service("SettingService")
public class SettingService {
    @Autowired
    private UserService userService;
//    @Value("${user.file.updatePath}")
    private String path;


    public String Upload(MultipartFile file, String filepath){
        File imageFolder = new File(filepath);
        String filename = getRandomString(6) + file.getOriginalFilename()
                .substring(file.getOriginalFilename().length() - 4);

        File f = new File(imageFolder, filename);
//        File f = new File(imageFolder, filename+".jpg");

        if (!f.getParentFile().exists())
            f.getParentFile().mkdirs();
        try {
            file.transferTo(f);

            String imgURL = path + f.getName();
            return imgURL;
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    public int saveImg(Integer id, String path){
        if (path==null||"".equals(path)){
            return 0; }

        if (id==0){
            return 0;
        }
        User user = userService.queryUserById(id);
        if(user!=null){
            user.setAvatar(path);
            int i = userService.updateUserInfo(user);
            return i;
        }
        return 0;
    }


    public String getRandomString(int length) {
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }
}
