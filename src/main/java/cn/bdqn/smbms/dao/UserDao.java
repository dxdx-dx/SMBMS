package cn.bdqn.smbms.dao;

import cn.bdqn.smbms.pojo.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * 用户dao层
 */
public interface UserDao {
    /**
     * 根据UserCode查询用户
     *
     * @param connection
     * @param userCode
     * @return
     * @throws SQLException
     */
    List<User> findByUserCode(Connection connection, String userCode) throws SQLException;

    /**
     * 分页查询用户列表
     *
     * @param connection
     * @param userName
     * @param userRole
     * @param pageNo
     * @param pageSize
     * @return
     * @throws SQLException
     */

    List<User> findByPage(Connection connection, String userName, Integer userRole, Integer pageNo, Integer pageSize) throws SQLException;

    /**
     *查询用户总条数
     *  @param connection
     * @param userName
     * @param userRole
     * @return
     * @throws SQLException
     */
    int findByPageCount(Connection connection, String userName, Integer userRole) throws SQLException;
}
