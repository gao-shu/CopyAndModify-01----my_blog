package com.gaoshu.controller.admin;

import com.gaoshu.common.Constant;
import com.gaoshu.entity.PO.User;
import com.gaoshu.entity.VO.Result;
import com.gaoshu.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * @Title: BackUserController
 * @Description: 后台用户
 * @author: gaoshu
 * @date: 2021/11/30 17:23
 */
@RestController
@RequestMapping("/backUser")
@Api(tags = "后台用户管理")
public class BackUserController {

    @Autowired
    private IUserService userService;

    @PostMapping("/register")
    @ApiOperation("注册用户")
    public Result register(@RequestBody User user) {
        userService.register(user);
        return Result.ok();
    }

    @PostMapping("/getUserById")
    @ApiOperation("根据id查询用户")
    public Result getUserById(@RequestParam Long id) {
        userService.getUserById(id);
        return Result.ok();
    }

    @PostMapping("/updateUser")
    @ApiOperation("修改用户信息")
    public Result updateUser(User user) {
        userService.updateUser(user);
        return Result.ok();
    }

    @PostMapping("/updatePassword")
    @ApiOperation("修改密码")
    public Result updatePassword(String oldPassword, String newPassword, HttpSession session) {
        String userName = (String) session.getAttribute(Constant.LOGIN_USER);
        userService.updatePassword(userName, newPassword);
        // 用户退出
        session.invalidate();
        return Result.ok();
    }

    @PostMapping("/cancellation")
    public Result cancellation(User user) {
        userService.cancellation(user);
        return Result.ok();
    }

}
