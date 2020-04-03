package cn.bdqn.smbms.dao;

import cn.bdqn.smbms.pojo.Role;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface RoleDao {
    List<Role> findAll(Connection connection) throws SQLException;
}
