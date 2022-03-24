package com.LMZ.RPC.myRPCV5.service;

import com.LMZ.RPC.myRPCV5.common.entity.User;

public interface UserService {
    //通过id获得用户信息
    User getUserById(Integer id);

    //增加一个功能
    Integer insertUser(User user);
}