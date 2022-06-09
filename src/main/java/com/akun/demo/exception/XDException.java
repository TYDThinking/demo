package com.akun.demo.exception;

/**
 * @Package: com.akun.demo.exception
 * @ClassName: XDException
 * @Author: akun
 * @CreateTime: 2022/4/14 8:35
 * @Description:  自定义异常类
 */
public class XDException extends RuntimeException {
    private Integer code;

    private String msg;

    public XDException(Integer code, String msg){
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;

    }
}
