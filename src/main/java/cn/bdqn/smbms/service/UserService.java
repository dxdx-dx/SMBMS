package cn.bdqn.smbms.service;

import cn.bdqn.smbms.pojo.User;

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

}
