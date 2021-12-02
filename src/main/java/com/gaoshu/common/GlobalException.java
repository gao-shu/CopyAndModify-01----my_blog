package com.gaoshu.common;

/**
 * @Title: GlobalException
 * @Description: 全局异常
 * @author: gaoshu
 * @date: 2021/11/30 16:27
 */
public class GlobalException extends RuntimeException implements ResultCode{


    private String msg;
    private int code;

    public GlobalException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public GlobalException(int code, String msg) {
        super(code + ":" + msg, null, true, true);
        this.code = code;
        this.msg = msg;
    }

    public GlobalException(ResultCode resultCode) {
        this(resultCode.getCode(), resultCode.getMsg());
    }

    public GlobalException(ResultCode resultCode, Object... args) {
        this(resultCode.getCode(), String.format(resultCode.getMsg(), args));
    }

    public GlobalException(ResultCode resultCode, String str, Boolean flag) {
        this(resultCode.getCode(), resultCode.getMsg() + ":" + str);
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getMsg() {
        return msg;
    }

}
