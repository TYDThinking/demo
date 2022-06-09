package com.akun.demo.model.vo;

import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Package: com.akun.demo.model.vo
 * @ClassName: faceRequest
 * @Author: akun
 * @CreateTime: 2022/4/18 21:29
 * @Description:
 */
public class FaceRequest {

   private String file;     //照片文件做base64加密后的字符串
   private Integer groupId; //组别id
   private String name;     //名字

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
