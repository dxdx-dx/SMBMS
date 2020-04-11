package cn.bdqn.smbms.dao;

import cn.bdqn.smbms.pojo.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * 用户dao层
 */
public interface UserDao {
    //根据UserCode查询用户
    List<User> findByUserCode(Connection connection, String userCode) throws SQLException;

    //分页查询用户列表

    List<User> findByPage(Connection connection, String userName, Integer userRole, Integer pageNo, Integer pageSize) throws SQLException;

    //查询用户总条数
    int findByPageCount(Connection connection, String userName, Integer userRole) throws SQLException;

    //添加用户
    int adduser(Connection connection, User user) throws SQLException;

    //根据id查询用户
    User findById(Connection connection, Integer id) throws SQLException;

}
