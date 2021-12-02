package com.gaoshu.entity.VO;

import com.gaoshu.common.ResultCode;
import com.gaoshu.entity.Enum.SystemCodeEnum;
import lombok.Data;

import java.io.Serializable;

/**
 * @Title: Result
 * @Description: 前端结果对象
 * @author: gaoshu
 * @date: 2021/11/30 16:07
 */
@Data
public class Result<T> implements Serializable {

    private Boolean status;
    private Integer code;
    private String msg;
    private T data;

    public static <T> Result ok() {
        return new Result(SystemCodeEnum.SYSTEM_OK);
    }

    public static <T> Result ok(T data) {
        Result<Object> result = new Result<>(SystemCodeEnum.SYSTEM_OK);
        result.setData(data);
        return result;
    }

    private Result(ResultCode resultCode) {
        this.code = resultCode.getCode();
        this.msg = resultCode.getMsg();
    }

}
