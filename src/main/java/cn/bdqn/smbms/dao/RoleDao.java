package cn.bdqn.smbms.dao;

import cn.bdqn.smbms.pojo.Role;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * 角色dao层
 */
public interface RoleDao {
    /**
     * 查询所有哦角色列表
     *
     * @param connection 连接对象
     * @return 角色列表
     * @throws SQLException 一异常
     */
    List<Role> findAll(Connection connection) throws SQLException;
}
