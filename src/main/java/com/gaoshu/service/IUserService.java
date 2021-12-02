package com.gaoshu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gaoshu.entity.PO.User;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author gaoshu
 * @since 2021-11-30
 */
public interface IUserService extends IService<User> {

    /**
     * @Description: 注册用户
     * @Param: [user]
     * @return: void
     * @Author: gaoshu
     * @Date: 2021/11/30
     */
    void register(User user);

    /**
     * @Description: 根据id获取客户
     * @Param: []
     * @return: com.gaoshu.entity.PO.User
     * @Author: gaoshu
     * @Date: 2021/11/30
    */
    User getUserById(Long id);

    /**
     * @Description: 根据名称获取客户
     * @Param: []
     * @return: com.gaoshu.entity.PO.User
     * @Author: gaoshu
     * @Date: 2021/11/30
     * @param username
    */
    List<User> getUserByName(String username);

    /**
     * @Description: 根据邮箱获取客户
     * @Param: []
     * @return: com.gaoshu.entity.PO.User
     * @Author: gaoshu
     * @Date: 2021/11/30
    */
    User getUserByEmail();

    /**
     * @Description: 新增客户
     * @Param: []
     * @return: java.lang.Long
     * @Author: gaoshu
     * @Date: 2021/11/30
    */
    Long saveUser();

    /**
     * @Description: 修改客户
     * @Param: []
     * @return: java.lang.Long
     * @Author: gaoshu
     * @Date: 2021/11/30
    */
    void updateUser(User user);

    /**
     * @Description: 删除客户
     * @Param: [id]
     * @return: void
     * @Author: gaoshu
     * @Date: 2021/11/30
    */
    void deleteUserById(Long id);

    /**
     * @Description: 修改密码
     * @Param: [user]
     * @return: void
     * @Author: gaoshu
     * @Date: 2021/11/30
    */
    void updatePassword(String userName, String password);

    /**
     * @Description: 用户注销
     * @Param: [user]
     * @return: void
     * @Author: gaoshu
     * @Date: 2021/11/30
     */
    void cancellation(User user);
}
