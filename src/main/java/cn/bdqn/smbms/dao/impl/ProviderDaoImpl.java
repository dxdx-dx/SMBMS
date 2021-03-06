package cn.bdqn.smbms.dao.impl;

import cn.bdqn.smbms.dao.BaseDao;
import cn.bdqn.smbms.dao.ProviderDao;
import cn.bdqn.smbms.pojo.Provider;
import com.mysql.jdbc.StringUtils;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 供应商Dao层实现类
 */
@Repository
public class ProviderDaoImpl implements ProviderDao {

    /**
     * 分页查询供应商列表
     *
     * @param connection 连接对象
     * @param proCode    供应商编码
     * @param proName    供应商名称
     * @param pageNo     当前页
     * @param pageSize   页大小
     * @return java.util.List<cn.bdqn.smbms.pojo.Provider>
     * @author Matrix
     * @date 2020/4/26 23:04
     */
    @Override
    public List<Provider> findByPage(Connection connection, String proCode, String proName, Integer pageNo, Integer pageSize) throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Provider> providerList = new ArrayList<>();
        List<Object> paramsList = new ArrayList<>();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT *  FROM `smbms_provider` WHERE  1=1");
        if (!StringUtils.isNullOrEmpty(proCode)) {
            sql.append(" AND `proCode` LIKE CONCAT('%',?,'%')");
            paramsList.add(proCode);
        }
        if (!StringUtils.isNullOrEmpty(proName)) {
            sql.append(" AND `proName` LIKE CONCAT('%',?,'%')");
            paramsList.add(proName);
        }
        sql.append(" ORDER BY `creationDate` DESC LIMIT ?,? ");
        paramsList.add(pageNo);
        paramsList.add(pageSize);
        resultSet = BaseDao.executeQuery(connection, preparedStatement, resultSet, sql.toString(), paramsList.toArray());
        while (resultSet.next()) {
            Provider provider = new Provider();
            provider.setId(resultSet.getInt("id"));
            provider.setProCode(resultSet.getString("proCode"));
            provider.setProName(resultSet.getString("proName"));
            provider.setProDesc(resultSet.getString("proDesc"));
            provider.setProContact(resultSet.getString("proContact"));
            provider.setProPhone(resultSet.getString("proPhone"));
            provider.setProAddress(resultSet.getString("proAddress"));
            provider.setProFax(resultSet.getString("proFax"));
            provider.setCreatedBy(resultSet.getInt("createdBy"));
            provider.setCreationDate(resultSet.getDate("creationDate"));
            provider.setModifyBy(resultSet.getInt("modifyBy"));
            provider.setModifyDate(resultSet.getDate("modifyDate"));
            providerList.add(provider);
        }
        BaseDao.closeResource(null, preparedStatement, resultSet);
        return providerList;
    }

    /**
     * 查询供应商总条数
     *
     * @param connection 连接对象
     * @param proCode    供应商编码
     * @param proName    供应商名称
     * @return int
     * @author Matrix
     * @date 2020/4/26 23:03
     */
    @Override
    public int findByPageCount(Connection connection, String proCode, String proName) throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        int result = 0;
        List<Object> paramsList = new ArrayList<>();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT count(1) FROM `smbms_provider` WHERE 1=1 ");
        if (!StringUtils.isNullOrEmpty(proCode)) {
            sql.append("AND `proCode` LIKE CONCAT('%',?,'%')");
            paramsList.add(proCode);
        }
        if (!StringUtils.isNullOrEmpty(proName)) {
            sql.append("AND `proName` LIKE CONCAT('%',?,'%')");
            paramsList.add(proName);
        }
        resultSet = BaseDao.executeQuery(connection, preparedStatement, resultSet, sql.toString(), paramsList.toArray());
        while (resultSet.next()) {
            result = resultSet.getInt(1);
        }
        return result;
    }

    /**
     * 查询所有供应商名称
     *
     * @param connection 连接对象
     * @return java.util.List<cn.bdqn.smbms.pojo.Provider>
     * @author Matrix
     * @date 2020/4/26 23:02
     */
    @Override
    public List<Provider> findName(Connection connection) throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Provider> providerList = new ArrayList<>();
        String sql = "SELECT `id`,`proName`FROM `smbms_provider`";
        resultSet = BaseDao.executeQuery(connection, preparedStatement, resultSet, sql, null);
        while (resultSet.next()) {
            Provider provider = new Provider();
            provider.setId(resultSet.getInt("id"));
            provider.setProName(resultSet.getString("proName"));
            providerList.add(provider);
        }
        BaseDao.closeResource(null, preparedStatement, resultSet);
        return providerList;
    }

    /**
     * 添加供应商
     *
     * @param connection 连接对象
     * @param provider   供应商对象
     * @return int
     * @author Matrix
     * @date 2020/4/26 23:02
     */
    @Override
    public int addPrivider(Connection connection, Provider provider) throws SQLException {
        PreparedStatement preparedStatement = null;
        int result = 0;
        String sql = "INSERT INTO `smbms`.`smbms_provider` (`proCode`, `proName`, `proDesc`, " +
                "`proContact`, `proPhone`,`proAddress`,`proFax`,`createdBy`,`creationDate`) " +
                "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
        Object[] params = {provider.getProCode(), provider.getProName(), provider.getProDesc(),
                provider.getProContact(), provider.getProPhone(), provider.getProAddress(),
                provider.getProFax(), provider.getCreatedBy(), provider.getCreationDate()};
        result = BaseDao.executeUpdate(connection, preparedStatement, sql, params);
        BaseDao.closeResource(null, preparedStatement, null);
        return result;
    }

    /**
     * 根据偶读查询供应商
     *
     * @param connection 连接对象
     * @param id         供应商id
     * @return cn.bdqn.smbms.pojo.Provider
     * @author Matrix
     * @date 2020/4/26 23:01
     */
    @Override
    public Provider findProviderById(Connection connection, Integer id) throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String sql = "SELECT * FROM `smbms_provider` WHERE id=?";
        Object[] params = {id};
        resultSet = BaseDao.executeQuery(connection, preparedStatement, resultSet, sql, params);
        Provider provider = null;
        while (resultSet.next()) {
            provider = new Provider();
            provider.setId(resultSet.getInt("id"));
            provider.setProCode(resultSet.getString("proCode"));
            provider.setProName(resultSet.getString("proName"));
            provider.setProDesc(resultSet.getString("proDesc"));
            provider.setProContact(resultSet.getString("proContact"));
            provider.setProPhone(resultSet.getString("proPhone"));
            provider.setProAddress(resultSet.getString("proAddress"));
            provider.setProFax(resultSet.getString("proFax"));
            provider.setCreatedBy(resultSet.getInt("createdBy"));
            provider.setCreationDate(resultSet.getDate("creationDate"));
            provider.setModifyBy(resultSet.getInt("modifyBy"));
            provider.setModifyDate(resultSet.getDate("modifyDate"));
        }
        BaseDao.closeResource(null, preparedStatement, resultSet);
        return provider;
    }

    /**
     * 修改供应商
     *
     * @param connection 连接对象
     * @param provider   供应商对象
     * @return int
     * @author Matrix
     * @date 2020/4/26 23:00
     */
    @Override
    public int providerModify(Connection connection, Provider provider) throws SQLException {
        PreparedStatement preparedStatement = null;
        int result;
        String sql = "UPDATE  `smbms`.`smbms_provider` SET`proCode` = ?," +
                "`proName` = ?,`proDesc` = ?,`proContact` = ?, `proPhone` = ?," +
                "`proAddress` = ?, `proFax` = ?,`modifyDate` = ?,`modifyBy` = ? " +
                "WHERE `id` = ? ";
        Object[] params = {provider.getProCode(), provider.getProName(), provider.getProDesc(),
                provider.getProContact(), provider.getProPhone(), provider.getProAddress(),
                provider.getProFax(), provider.getModifyDate(), provider.getModifyBy(), provider.getId()};
        result = BaseDao.executeUpdate(connection, preparedStatement, sql, params);
        BaseDao.closeResource(null, preparedStatement, null);
        return result;
    }

    /**
     * 删除供应商
     *
     * @return int
     * @author Matrix
     * @date 2020/4/26 23:41
     */
    @Override
    public int delprovider(Connection connection, Integer id) throws SQLException {
        PreparedStatement preparedStatement = null;
        int result;
        Object[] params = {id};
        String sql = "DELETE FROM`smbms`.`smbms_provider` WHERE `id` = ? ";
        result = BaseDao.executeUpdate(connection, preparedStatement, sql, params);
        BaseDao.closeResource(null, preparedStatement, null);
        return result;
    }
}
