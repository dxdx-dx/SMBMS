package cn.bdqn.smbms.service.impl;

import cn.bdqn.smbms.dao.BaseDao;
import cn.bdqn.smbms.dao.BillDao;
import cn.bdqn.smbms.pojo.Bill;
import cn.bdqn.smbms.pojo.Provider;
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
    private BillDao billDao;

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
}
