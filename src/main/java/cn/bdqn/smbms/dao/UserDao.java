package cn.bdqn.smbms.dao;

import cn.bdqn.smbms.pojo.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface UserDao {
    List<User> findByUserCode(Connection connection, String userCode) throws SQLException;


    List<User> findByPage(Connection connection, String userName, Integer userRole, Integer pageNo, Integer pageSize) throws SQLException;

    int findByPageCount(Connection connection, String userName, Integer userRole) throws SQLException;
}
