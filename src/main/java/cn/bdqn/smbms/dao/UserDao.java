package cn.bdqn.smbms.dao;

import cn.bdqn.smbms.pojo.User;

import java.sql.Connection;
import java.util.List;

public interface UserDao {
    List<User> findByUserCode(Connection connection, String userCode) throws Exception;
}
