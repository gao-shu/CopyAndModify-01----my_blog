package com.gaoshu.entity.VO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotEmpty;

/**
 * @Title: LoginVo
 * @Description: 登录服务类
 * @author: gaoshu
 * @date: 2021/12/1 14:30
 */
@Data
@ApiModel
public class UserVo {

    @ApiModelProperty(value = "用户名")
    @NotEmpty(message = "用户名不能为空")
    private String username;

    @ApiModelProperty(value = "密码")
    @NotEmpty(message = "密码不能为空")
    private String password;

    @ApiModelProperty(value = "验证码")
    @NotEmpty(message = "验证码不能为空")
    private String captcha;

}
