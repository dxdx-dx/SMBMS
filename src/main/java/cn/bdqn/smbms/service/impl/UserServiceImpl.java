package cn.bdqn.smbms.service.impl;

import cn.bdqn.smbms.dao.BaseDao;
import cn.bdqn.smbms.dao.UserDao;
import cn.bdqn.smbms.pojo.User;
import cn.bdqn.smbms.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserDao userDao;

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

    @Override
    public List<User> findByPage(String userName, Integer userRole, Integer pageNo, Integer pageSize) {
        Connection connection = null;
        List<User> userList = null;
        try {
            connection = BaseDao.getConnection();
            userList = userDao.findByPage(connection, userName, userRole, pageNo, pageSize);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            BaseDao.closeResource(connection, null, null);
        }
        return userList;
    }

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
}
