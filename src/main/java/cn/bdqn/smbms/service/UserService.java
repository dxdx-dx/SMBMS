package cn.bdqn.smbms.service;

import cn.bdqn.smbms.pojo.User;

public interface UserService {
    public User login(String userCode, String password);
}
