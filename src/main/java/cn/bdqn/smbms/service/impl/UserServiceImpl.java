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
     * @param userCode
     * @param password
     * @return
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
     * @param userName
     * @param userRole
     * @param pageNo
     * @param pageSize
     * @return
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
     * 查询用后记录数
     *
     * @param userName
     * @param userRole
     * @return
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
}
