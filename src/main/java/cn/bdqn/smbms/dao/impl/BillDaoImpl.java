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
    /**
     * 分页查询订单列表
     *
     * @param connection  连接对象
     * @param productName 商品名称
     * @param providerId  供应商id
     * @param isPayment   是否付款
     * @param pageNo      当前页
     * @param pageSize    页大小
     * @return java.util.List<cn.bdqn.smbms.pojo.Bill>
     * @author Matrix
     * @date 2020/4/26 22:57
     */
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

    /**
     * 查询订单总条数
     *
     * @param connection  连接对象
     * @param productName 商品名称
     * @param providerId  供应商id
     * @param isPayment   是否付款
     * @return int
     * @author Matrix
     * @date 2020/4/26 22:58
     */
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

    /**
     * 根据id查询订单
     *
     * @param connection 连接对象
     * @param id         订单id
     * @return cn.bdqn.smbms.pojo.Bill
     * @author Matrix
     * @date 2020/4/26 22:59
     */
    @Override
    public Bill findBillById(Connection connection, Integer id) throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String sql = "SELECT b.*,p.`proName`  FROM  `smbms_bill` b,`smbms_provider` p  WHERE b.`providerId`=p.`id` and b.id=?";
        Object[] params = {id};
        resultSet = BaseDao.executeQuery(connection, preparedStatement, resultSet, sql, params);
        Bill bill = null;
        while (resultSet.next()) {
            bill = new Bill();
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
        }
        BaseDao.closeResource(null, preparedStatement, resultSet);
        return bill;
    }


    /**
     * 删除订单
     *
     * @return int
     * @author Matrix
     * @date 2020/4/26 23:53
     */
    @Override
    public int delbill(Connection connection, Integer id) throws SQLException {
        PreparedStatement preparedStatement = null;
        int result;
        Object[] params = {id};
        String sql = "DELETE FROM`smbms`.`smbms_bill` WHERE `id` = ? ";
        result = BaseDao.executeUpdate(connection, preparedStatement, sql, params);
        BaseDao.closeResource(null, preparedStatement, null);
        return result;
    }

    /**
     * 修改订单
     *
     * @return int
     * @author Matrix
     * @date 2020/4/27 0:10
     */
    @Override
    public int billModify(Connection connection, Bill bill) throws SQLException {
        PreparedStatement preparedStatement = null;
        int result = 0;
        String sql = "UPDATE `smbms`.`smbms_bill` SET`billCode` = ?,`productName` = ?,`productDesc` = ?," +
                "`productUnit` =?,`productCount` = ?,`totalPrice` =?,`isPayment` = ?,`modifyBy` = ?," +
                "`modifyDate` =?,`providerId` = ? WHERE `id` = ?;";
        Object[] params = {bill.getBillCode(), bill.getProductName(), bill.getProductDesc(),
                bill.getProductUnit(), bill.getProductCount(), bill.getTotalPrice(), bill.getIsPayment(),
                bill.getCreatedBy(), bill.getCreationDate(), bill.getProviderId(), bill.getId()};
        result = BaseDao.executeUpdate(connection, preparedStatement, sql, params);
        BaseDao.closeResource(null, preparedStatement, null);
        return result;
    }

    /**
     * 添加订单
     *
     * @return int
     * @author Matrix
     * @date 2020/4/27 0:11
     */
    @Override
    public int addBill(Connection connection, Bill bill) throws SQLException {
        PreparedStatement preparedStatement = null;
        int result = 0;
        String sql = "INSERT INTO `smbms`.`smbms_bill` ( `billCode`, `productName`, `productDesc`, " +
                "`productUnit`, `productCount`,`totalPrice`,`isPayment`, `createdBy`, `creationDate`," +
                "`providerId`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ;";
        Object[] params = {bill.getBillCode(), bill.getProductName(), bill.getProductDesc(),
                bill.getProductUnit(), bill.getProductCount(), bill.getTotalPrice(), bill.getIsPayment(),
                bill.getCreatedBy(), bill.getCreationDate(), bill.getProviderId()};
        result = BaseDao.executeUpdate(connection, preparedStatement, sql, params);
        BaseDao.closeResource(null, preparedStatement, null);
        return result;
    }
}
