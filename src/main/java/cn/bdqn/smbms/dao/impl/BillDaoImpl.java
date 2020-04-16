package cn.bdqn.smbms.dao.impl;

import cn.bdqn.smbms.dao.BaseDao;
import cn.bdqn.smbms.dao.BillDao;
import cn.bdqn.smbms.pojo.Bill;
import cn.bdqn.smbms.pojo.Provider;
import com.mysql.jdbc.StringUtils;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 订单Dao层实现类
 */
@Repository
public class BillDaoImpl implements BillDao {

    @Override
    public List<Bill> findByPage(Connection connection, String productName, Integer providerId, Integer isPayment, Integer pageNo, Integer pageSize) throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Bill> billList = new ArrayList<>();
        List<Object> paramsList = new ArrayList<>();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT b.*,p.`proName`  FROM  `smbms_bill` b,`smbms_provider` p WHERE b.`providerId`=p.`id`  ");
        if (!StringUtils.isNullOrEmpty(productName)) {
            sql.append(" AND b.productName LIKE CONCAT('%',?,'%')");
            paramsList.add(productName);
        }
        if (providerId > 0) {
            sql.append(" AND b.`providerId`=?");
            paramsList.add(providerId);
        }
        if (isPayment > 0) {
            sql.append(" AND b.`isPayment`=?");
            paramsList.add(isPayment);
        }
        sql.append(" ORDER BY b.`creationDate` DESC LIMIT ?,? ");
        paramsList.add(pageNo);
        paramsList.add(pageSize);
        resultSet = BaseDao.executeQuery(connection, preparedStatement, resultSet, sql.toString(), paramsList.toArray());
        while (resultSet.next()) {
            Bill bill = new Bill();
            bill.setId(resultSet.getInt("id"));
            bill.setBillCode(resultSet.getString("billCode"));
            bill.setProductName(resultSet.getString("productName"));
            bill.setProductDesc(resultSet.getString("productDesc"));
            bill.setProductUnit(resultSet.getString("productUnit"));
            bill.setProductCount(resultSet.getInt("productCount"));
            bill.setTotalPrice(resultSet.getDouble("totalPrice"));
            bill.setIsPayment(resultSet.getInt("isPayment"));
            bill.setCreatedBy(resultSet.getInt("createdBy"));
            bill.setCreationDate(resultSet.getDate("creationDate"));
            bill.setModifyBy(resultSet.getInt("modifyBy"));
            bill.setModifyDate(resultSet.getDate("modifyDate"));
            bill.setProviderId(resultSet.getInt("providerId"));
            bill.setProviderName(resultSet.getString("proName"));
            billList.add(bill);
        }
        BaseDao.closeResource(null, preparedStatement, resultSet);
        return billList;
    }

    @Override
    public int findByPageCount(Connection connection, String productName, Integer providerId, Integer isPayment) throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        int result = 0;
        List<Object> paramsList = new ArrayList<>();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT count(1) FROM `smbms_bill` WHERE 1=1 ");
        if (!StringUtils.isNullOrEmpty(productName)) {
            sql.append(" AND `productName` LIKE CONCAT('%',?,'%')");
            paramsList.add(productName);
        }
        if (providerId > 0) {
            sql.append(" AND `providerId`=?");
            paramsList.add(providerId);
        }
        if (isPayment > 0) {
            sql.append(" AND `isPayment`=?");
            paramsList.add(isPayment);
        }
        resultSet = BaseDao.executeQuery(connection, preparedStatement, resultSet, sql.toString(), paramsList.toArray());
        while (resultSet.next()) {
            result = resultSet.getInt(1);
        }
        return result;
    }
}
