package cn.bdqn.smbms.dao.impl;

import cn.bdqn.smbms.dao.BaseDao;
import cn.bdqn.smbms.dao.UserDao;
import cn.bdqn.smbms.pojo.User;
import com.mysql.jdbc.StringUtils;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 用户dao层实现类
 */
@Repository
public class UserDaoImpl implements UserDao {
    /**
     * 根据userCode查询用户
     *
     * @param connection
     * @param userCode
     * @return
     * @throws SQLException
     */
    @Override
    public List<User> findByUserCode(Connection connection, String userCode) throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String sql = "SELECT * FROM `smbms_user` WHERE `userCode`=?";
        Object[] params = {userCode};
        List<User> userList = new ArrayList<>();
        resultSet = BaseDao.executeQuery(connection, preparedStatement, resultSet, sql, params);
        User user;
        while (resultSet.next()) {
            user = new User();
            user.setId(resultSet.getInt("id"));
            user.setUserCode(resultSet.getString("userCode"));
            user.setUserName(resultSet.getString("userName"));
            user.setUserPassword(resultSet.getString("userPassword"));
            user.setGender(resultSet.getInt("gender"));
            user.setBirthday(resultSet.getDate("birthday"));
            user.setPhone(resultSet.getString("phone"));
            user.setAddress(resultSet.getString("address"));
            user.setUserRole(resultSet.getInt("userRole"));
            user.setCreatedBy(resultSet.getInt("createdBy"));
            user.setCreationDate(resultSet.getDate("creationDate"));
            user.setModifyBy(resultSet.getInt("modifyBy"));
            user.setModifyDate(resultSet.getDate("modifyDate"));
            userList.add(user);
        }
        BaseDao.closeResource(null, preparedStatement, resultSet);
        return userList;
    }

    /**
     * 分页查询用户
     *
     * @param connection
     * @param userName
     * @param userRole
     * @param pageNo
     * @param pageSize
     * @return
     * @throws SQLException
     */
    @Override
    public List<User> findByPage(Connection connection, String userName, Integer userRole, Integer pageNo, Integer pageSize) throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<User> userList = new ArrayList<>();
        List<Object> paramsList = new ArrayList<>();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT u.*,r.roleName FROM `smbms_role` r,`smbms_user` u WHERE r.id=u.`userRole`");
        if (!StringUtils.isNullOrEmpty(userName)) {
            sql.append(" AND `userName` LIKE CONCAT('%',?,'%')");
            paramsList.add(userName);
        }
        if (userRole > 0) {
            sql.append(" AND `userRole`=?");
            paramsList.add(userRole);
        }
        sql.append(" ORDER BY `creationDate`DESC LIMIT ?,? ");
        paramsList.add(pageNo);
        paramsList.add(pageSize);
        System.out.println("=====================sql.toString()===========================");
        System.out.println(sql.toString());
        System.out.println("=====================userName===========================");
        System.out.println(userName + "+++" + userRole + "+++" + pageNo + "+++" + pageSize);
        System.out.println("=====================paramsList===========================");
        System.out.println(paramsList.toArray());
        resultSet = BaseDao.executeQuery(connection, preparedStatement, resultSet, sql.toString(), paramsList.toArray());
        User user;
        while (resultSet.next()) {
            user = new User();
            user.setId(resultSet.getInt("id"));
            user.setUserCode(resultSet.getString("userCode"));
            user.setUserName(resultSet.getString("userName"));
            user.setUserPassword(resultSet.getString("userPassword"));
            user.setGender(resultSet.getInt("gender"));
            user.setBirthday(resultSet.getDate("birthday"));
            user.setPhone(resultSet.getString("phone"));
            user.setAddress(resultSet.getString("address"));
            user.setUserRole(resultSet.getInt("userRole"));
            user.setCreatedBy(resultSet.getInt("createdBy"));
            user.setCreationDate(resultSet.getDate("creationDate"));
            user.setModifyBy(resultSet.getInt("modifyBy"));
            user.setModifyDate(resultSet.getDate("modifyDate"));
            user.setUserRoleName(resultSet.getString("roleName"));
            userList.add(user);
        }
        BaseDao.closeResource(null, preparedStatement, resultSet);
        return userList;
    }

    /**
     * 查询用户总条数
     *
     * @param connection
     * @param userName
     * @param userRole
     * @return
     * @throws SQLException
     */
    @Override
    public int findByPageCount(Connection connection, String userName, Integer userRole) throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        int result = 0;
        List<Object> paramsList = new ArrayList<>();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT count(1) FROM `smbms_user` u WHERE 1=1 ");
        if (!StringUtils.isNullOrEmpty(userName)) {
            sql.append("AND `userName` LIKE CONCAT('%',?,'%')");
            paramsList.add(userName);
        }
        if (userRole > 0) {
            sql.append("AND u.`userRole`=?");
            paramsList.add(userRole);
        }
        resultSet = BaseDao.executeQuery(connection, preparedStatement, resultSet, sql.toString(), paramsList.toArray());
        while (resultSet.next()) {
            result = resultSet.getInt(1);
        }
        return result;
    }
}
