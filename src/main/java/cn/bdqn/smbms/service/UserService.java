package cn.bdqn.smbms.service;

import cn.bdqn.smbms.pojo.User;

import java.util.List;

/**
 * 用户业务逻辑层
 */
public interface UserService {
    //登陆
    User login(String userCode, String password);

    //分页显示用户
    List<User> findByPage(String userName, Integer userRole, Integer pageNo, Integer pageSize);

    //查询用户总记录
    int findByPageCount(String userName, Integer userRole);

    // 添加用户
    boolean adduser(User user);

    //根据id查询用户
    User findUserById(Integer id);

    //修改用户
    boolean modifyUser(User user);

    //删除用户
    boolean deluser(Integer id);

    //根据用户编码查询用户
    User findByUserCode(String userCode);

    //根据id判断旧密码
    boolean validatePwd(Integer id, String oldPwd);

    //根据id修改密码
    boolean pwdmodifysave(Integer id, String newPwd);
}
