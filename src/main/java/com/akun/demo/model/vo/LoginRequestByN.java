package com.akun.demo.model.vo;

/**
 * @Package: com.akun.demo.model.vo
 * @ClassName: LoginRequest
 * @Author: akun
 * @CreateTime: 2022/4/13 23:07
 * @Description: 登陆接受参数的对象
 */
public class LoginRequestByN {

    private String number; //手机号
    private String password; //密码

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
