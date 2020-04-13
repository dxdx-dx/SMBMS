package cn.bdqn.smbms.service.impl;

import cn.bdqn.smbms.dao.BaseDao;
import cn.bdqn.smbms.dao.UserDao;
import cn.bdqn.smbms.pojo.User;
import cn.bdqn.smbms.service.UserService;
import cn.bdqn.smbms.util.Constants;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * 用户业务逻辑层实现类
 */
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserDao userDao;

    /**
     * 登陆
     *
     * @param userCode 用户编码
     * @param password 用户密码
     * @return 用户对象
     */
    @Override
    public User login(String userCode, String password) {
        Connection connection = null;
        User user = null;
        connection = BaseDao.getConnection();
        try {
            List<User> userList = userDao.findByUserCode(connection, userCode);
            if (userList == null || userList.size() == 0) {
                return null;
            }
            user = userList.get(0);
            if (user.getUserPassword().equals(password)) {
                return user;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            BaseDao.closeResource(connection, null, null);
        }
        return null;
    }

    /**
     * 分页显示用户列表
     *
     * @param userName 用户名
     * @param userRole 用户角色
     * @param pageNo   当前页
     * @param pageSize 页大小
     * @return 用户集合
     */
    @Override
    public List<User> findByPage(String userName, Integer userRole, Integer pageNo, Integer pageSize) {
        Connection connection = null;
        List<User> userList = null;
        try {
            connection = BaseDao.getConnection();
            userList = userDao.findByPage(connection, userName, userRole, (pageNo - 1) * Constants.PAGE_SIZE, pageSize);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            BaseDao.closeResource(connection, null, null);
        }
        return userList;
    }

    /**
     * 查询用户记录数
     *
     * @param userName 用户名
     * @param userRole 用户角色
     * @return 记录数
     */
    @Override
    public int findByPageCount(String userName, Integer userRole) {
        Connection connection = null;
        int result = 0;
        try {
            connection = BaseDao.getConnection();
            result = userDao.findByPageCount(connection, userName, userRole);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            BaseDao.closeResource(connection, null, null);
        }
        return result;
    }

    /**
     * 添加用户
     *
     * @param user 用户对象
     * @return 添加状态
     */
    @Override
    public boolean adduser(User user) {
        Connection connection = null;
        boolean result = false;
        int res = 0;
        try {
            connection = BaseDao.getConnection();
            res = userDao.adduser(connection, user);
            if (res > 0) result = true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            BaseDao.closeResource(connection, null, null);
        }
        return result;
    }

    /**
     * 根据id查询用户
     *
     * @param id 用户id
     * @return 用户对象
     */
    @Override
    public User findUserById(Integer id) {
        Connection connection = null;
        User user = null;
        connection = BaseDao.getConnection();
        try {
            user = userDao.findUserById(connection, id);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            BaseDao.closeResource(connection, null, null);
        }
        return user;
    }

    /**
     * @param user 用户对象
     * @return 跳转页面
     */
    @Override
    public boolean modifyUser(User user) {
        Connection connection = null;
        boolean result = false;
        int res = 0;
        try {
            connection = BaseDao.getConnection();
            res = userDao.modifyUser(connection, user);
            if (res > 0) result = true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            BaseDao.closeResource(connection, null, null);
        }
        return result;
    }

    /**
     * 删除用户
     *
     * @param id
     * @return
     */
    @Override
    public boolean deluser(Integer id) {
        Connection connection = null;
        boolean result = false;
        int res = 0;
        try {
            connection = BaseDao.getConnection();
            res = userDao.deluser(connection, id);
            if (res > 0) result = true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            BaseDao.closeResource(connection, null, null);
        }
        return result;
    }
}
