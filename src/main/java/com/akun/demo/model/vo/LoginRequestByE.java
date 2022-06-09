package com.akun.demo.model.vo;

/**
 * @Package: com.akun.demo.model.vo
 * @ClassName: LoginRequestByE
 * @Author: akun
 * @CreateTime: 2022/4/13 23:10
 * @Description:
 */
public class LoginRequestByE {
    private String email; //手机号
    private String password; //密码

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
