package cn.bdqn.smbms.service;

import cn.bdqn.smbms.pojo.User;
import java.util.List;

public interface UserService {
    User login(String userCode, String password);

    List<User> findByPage(String userName, Integer userRole, Integer pageNo, Integer pageSize);

    int findByPageCount(String userName, Integer userRole);

}
