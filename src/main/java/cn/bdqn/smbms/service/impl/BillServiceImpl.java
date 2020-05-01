package cn.bdqn.smbms.service.impl;

import cn.bdqn.smbms.dao.BaseDao;
import cn.bdqn.smbms.dao.BillDao;
import cn.bdqn.smbms.pojo.Bill;
import cn.bdqn.smbms.service.BillService;
import cn.bdqn.smbms.util.Constants;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * 订单业务逻辑层实现类
 */
@Service
public class BillServiceImpl implements BillService {
    @Resource
    private BillDao billDao;//订单dao层对象

    /**
     * 分页查询订单列表
     *
     * @return java.util.List<cn.bdqn.smbms.pojo.Bill>
     * @author Matrix
     * @date 2020/4/26 23:08
     */
    @Override
    public List<Bill> findByPage(String productName, Integer providerId, Integer isPayment, Integer pageNo, Integer pageSize) {
        Connection connection = null;
        List<Bill> billList = null;
        try {
            connection = BaseDao.getConnection();
            billList = billDao.findByPage(connection, productName, providerId, isPayment, (pageNo - 1) * Constants.PAGE_SIZE, pageSize);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            BaseDao.closeResource(connection, null, null);
        }
        return billList;
    }

    /**
     * 查询订单总条数
     *
     * @return int
     * @author Matrix
     * @date 2020/4/26 23:09
     */
    @Override
    public int findByPageCount(String productName, Integer providerId, Integer isPayment) {
        Connection connection = null;
        int result = 0;
        try {
            connection = BaseDao.getConnection();
            result = billDao.findByPageCount(connection, productName, providerId, isPayment);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            BaseDao.closeResource(connection, null, null);
        }
        return result;
    }

    /**
     * 根据id查询订单
     *
     * @return cn.bdqn.smbms.pojo.Bill
     * @author Matrix
     * @date 2020/4/26 23:10
     */
    @Override
    public Bill findBillById(Integer id) {
        Connection connection = null;
        Bill bill = null;
        connection = BaseDao.getConnection();
        try {
            bill = billDao.findBillById(connection, id);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            BaseDao.closeResource(connection, null, null);
        }
        return bill;
    }

    /**
     * 删除订单
     *
     * @return boolean
     * @author Matrix
     * @date 2020/4/26 23:55
     */
    @Override
    public boolean delbill(Integer id) {
        Connection connection = null;
        boolean result = false;
        int res = 0;
        try {
            connection = BaseDao.getConnection();
            res = billDao.delbill(connection, id);
            if (res > 0) result = true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            BaseDao.closeResource(connection, null, null);
        }
        return result;
    }

    /**
     * 修改订单
     *
     * @return boolean
     * @author Matrix
     * @date 2020/4/27 0:12
     */
    @Override
    public boolean billModify(Bill bill) {
        Connection connection = null;
        boolean result = false;
        int res = 0;
        try {
            connection = BaseDao.getConnection();
            res = billDao.billModify(connection, bill);
            if (res > 0) result = true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            BaseDao.closeResource(connection, null, null);
        }
        return result;
    }

    /**
     * 添加订单
     *
     * @return boolean
     * @author Matrix
     * @date 2020/4/27 0:13
     */
    @Override
    public boolean addBill(Bill bill) {
        Connection connection = null;
        boolean result = false;
        int res = 0;
        try {
            connection = BaseDao.getConnection();
            res = billDao.addBill(connection, bill);
            if (res > 0) result = true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            BaseDao.closeResource(connection, null, null);
        }
        return result;
    }

}
