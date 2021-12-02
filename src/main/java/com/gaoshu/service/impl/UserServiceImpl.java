package com.gaoshu.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gaoshu.entity.PO.User;
import com.gaoshu.mapper.UserMapper;
import com.gaoshu.service.IUserService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author gaoshu
 * @since 2021-11-30
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Override
    public User getUserById(Long id) {
        User user = getUserById(id);
        if (user != null) {
            throw new RuntimeException("用户不存在");
        }
        return user;
    }

    @Override
    public List<User> getUserByName(String username) {
        return lambdaQuery().eq(User::getName, username).list();
    }

    @Override
    public User getUserByEmail() {
        return null;
    }

    @Override
    public Long saveUser() {
        return null;
    }

    @Override
    public void updateUser(User user) {
        saveOrUpdate(user);
    }

    @Override
    public void deleteUserById(Long id) {

    }

    @Override
    public void register(User user) {
        if (StrUtil.isEmpty(user.getName()) || StrUtil.isEmpty(user.getPassword())) {
            throw new RuntimeException("用户名密码不可为空");
        }
        String md5Password = SecureUtil.md5(user.getName() + user.getPassword());
        user.setPassword(md5Password);
        save(user);
    }

    @Override
    public void updatePassword(String userName, String password) {
        String md5Password = SecureUtil.md5(userName+password);
        lambdaUpdate().set(User::getPassword, md5Password).eq(User::getName, userName).update();
    }

    @Override
    public void cancellation(User user) {

    }
}
