package cn.bdqn.smbms.service.impl;

import cn.bdqn.smbms.dao.BaseDao;
import cn.bdqn.smbms.dao.ProviderDao;
import cn.bdqn.smbms.pojo.Provider;
import cn.bdqn.smbms.pojo.User;
import cn.bdqn.smbms.service.ProviderService;
import cn.bdqn.smbms.util.Constants;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * 供应商业务逻辑层实现类
 */
@Service
public class ProviderServiceImpl implements ProviderService {
    @Resource
    private ProviderDao providerDao; //供应商dao层对象

    /**
     * 分页查询供应商列表
     */
    @Override
    public List<Provider> findByPage(String proCode, String proName, Integer pageNo, Integer pageSize) {
        Connection connection = null;
        List<Provider> providerList = null;
        try {
            connection = BaseDao.getConnection();
            providerList = providerDao.findByPage(connection, proCode, proName, (pageNo - 1) * Constants.PAGE_SIZE, pageSize);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            BaseDao.closeResource(connection, null, null);
        }
        return providerList;
    }

    /**
     * 查询供应商总条数
     */
    @Override
    public int findByPageCount(String proCode, String proName) {
        Connection connection = null;
        int result = 0;
        try {
            connection = BaseDao.getConnection();
            result = providerDao.findByPageCount(connection, proCode, proName);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            BaseDao.closeResource(connection, null, null);
        }
        return result;
    }

    @Override
    public List<Provider> findName() {
        Connection connection = null;
        List<Provider> providerList = null;
        try {
            connection = BaseDao.getConnection();
            providerList = providerDao.findName(connection);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            BaseDao.closeResource(connection, null, null);
        }
        return providerList;
    }

    /**
     * 添加供应商
     *
     * @return boolean
     * @author Matrix
     * @date 2020/4/16 20:12
     */
    @Override
    public boolean addPrivider(Provider provider) {
        Connection connection = null;
        boolean result = false;
        int res = 0;
        try {
            connection = BaseDao.getConnection();
            res = providerDao.addPrivider(connection, provider);
            if (res > 0) result = true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            BaseDao.closeResource(connection, null, null);
        }
        return result;
    }

    /**
     * 根据id查询供应商
     *
     * @return cn.bdqn.smbms.pojo.Provider
     * @author Matrix
     * @date 2020/4/26 23:11
     */
    @Override
    public Provider findProviderById(Integer id) {
        Connection connection = null;
        Provider provider = null;
        connection = BaseDao.getConnection();
        try {
            provider = providerDao.findProviderById(connection, id);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            BaseDao.closeResource(connection, null, null);
        }
        return provider;
    }

    /**
     * 修改供应商
     *
     * @return boolean
     * @author Matrix
     * @date 2020/4/26 23:11
     */
    @Override
    public boolean providerModify(Provider provider) {
        Connection connection = null;
        boolean result = false;
        int res = 0;
        try {
            connection = BaseDao.getConnection();
            res = providerDao.providerModify(connection, provider);
            if (res > 0) result = true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            BaseDao.closeResource(connection, null, null);
        }
        return result;
    }

    /**
     * 删除供应商
     *
     * @return boolean
     * @author Matrix
     * @date 2020/4/26 23:42
     */
    @Override
    public boolean delprovider(Integer id) {
        Connection connection = null;
        boolean result = false;
        int res = 0;
        try {
            connection = BaseDao.getConnection();
            res = providerDao.delprovider(connection, id);
            if (res > 0) result = true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            BaseDao.closeResource(connection, null, null);
        }
        return result;
    }
}
