package com.gaoshu.controller.admin;

import cn.hutool.crypto.SecureUtil;
import com.gaoshu.common.Constant;
import com.gaoshu.common.GlobalException;
import com.gaoshu.entity.PO.User;
import com.gaoshu.entity.VO.Result;
import com.gaoshu.entity.VO.UserVo;
import com.gaoshu.service.IUserService;
import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

/**
 * @Title: LoginController
 * @Description: 登陆模块
 * @author: gaoshu
 * @date: 2021/11/30 16:03
 */
@RestController
@RequestMapping("/admin")
@Api(tags = "登陆模块")
public class LoginSessionController {

    @Autowired
    private IUserService userService;

    @Autowired
    private Producer producer;

    /**
     * 获取图片验证
     * @param response
     * @param session
     */
    @GetMapping("/getKaptchaImage")
    @ApiOperation("获取验证图片")
    public void getKaptchaImage(HttpServletResponse response, HttpSession session)  {
        ServletOutputStream out = null;
        try {
            response.setDateHeader("Expires", 0);
            response.setHeader("Pragma", "no-cache");
            response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
            response.addHeader("Cache-Control", "post-check=0, pre-check=0");
            response.setContentType("image/jpeg");
            //生成验证码
            String capText = producer.createText();
            session.setAttribute(Constants.KAPTCHA_SESSION_KEY, capText);
            //向客户端写出
            BufferedImage bi = producer.createImage(capText);
            out = response.getOutputStream();
            ImageIO.write(bi, "jpg", out);
            // 规定时间内清除验证码
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            try {
                if (out != null) {
                    out.close();
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    @PostMapping("/login")
    @ApiOperation("用户登录")
    public Result Login(@Valid UserVo loginVo, HttpSession session) {
        // 1.校验验证码
        String capText = (String) session.getAttribute(Constants.KAPTCHA_SESSION_KEY);
        if (!loginVo.getCaptcha().equals(capText)) {
//            throw new RuntimeException("验证码不正确");
            throw new GlobalException("验证码不正确");
        }
        session.removeAttribute(Constants.KAPTCHA_SESSION_KEY);
        // 2.校验用户是否存在
        List<User> userList = userService.getUserByName(loginVo.getUsername());
        if (userList.size() == 0) {
            throw new GlobalException("用户不存在");
        }
        User user = userList.get(0);
        // 3.校验密码是否正确
        if (!SecureUtil.md5(loginVo.getUsername()+loginVo.getPassword()).equals(user.getPassword())) {
            throw new GlobalException("密码不正确");
        }
        // 4.生成token并存储在session
        session.setAttribute(Constant.LOGIN_USER, user.getName());
        // 5.其他扩展操作
        return Result.ok();
    }



    @PostMapping("/loginOut")
    @ApiOperation("用户退出")
    public Result loginOut(HttpSession Session) {
        Session.invalidate();
        return Result.ok();
    }




}
