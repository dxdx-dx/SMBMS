package cn.bdqn.smbms.dao.impl;

import cn.bdqn.smbms.dao.BaseDao;
import cn.bdqn.smbms.dao.RoleDao;
import cn.bdqn.smbms.pojo.Role;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class RoleDaoImpl implements RoleDao {
    @Override
    public List<Role> findAll(Connection connection) throws SQLException {
        String sql = "SELECT `id`,`roleCode`,`roleName`FROM `smbms_role`";
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        resultSet = BaseDao.executeQuery(connection, preparedStatement, resultSet, sql, null);
        List<Role> roleList = new ArrayList<>();
        Role role;
        while (resultSet.next()) {
            role = new Role();
            role.setId(resultSet.getInt("id"));
            role.setRoleCode(resultSet.getString("roleCode"));
            role.setRoleName(resultSet.getString("roleName"));
            roleList.add(role);
        }
        BaseDao.closeResource(null, preparedStatement, resultSet);
        return roleList;
    }
}
