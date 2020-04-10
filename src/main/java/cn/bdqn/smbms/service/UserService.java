package cn.bdqn.smbms.service;

import cn.bdqn.smbms.pojo.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * 用户业务逻辑层
 */
public interface UserService {
    /**
     * 登陆
     *
     * @param userCode
     * @param password
     * @return
     */
    User login(String userCode, String password);

    /**
     * 分页显示用户
     *
     * @param userName
     * @param userRole
     * @param pageNo
     * @param pageSize
     * @return
     */
    List<User> findByPage(String userName, Integer userRole, Integer pageNo, Integer pageSize);

    /**
     * 查询用户总记录
     *
     * @param userName
     * @param userRole
     * @return
     */
    int findByPageCount(String userName, Integer userRole);

    /**
     * 添加用户
     *
     * @param user 用户对象
     * @return 添加结果
     */
    boolean adduser(User user);

    /**
     * 根据id查询用户
     *
     * @param id
     * @return
     */
    User findById(Integer id);
}
