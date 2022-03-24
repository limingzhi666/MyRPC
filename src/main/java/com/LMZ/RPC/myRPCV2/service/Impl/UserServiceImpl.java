package com.LMZ.RPC.myRPCV2.service.Impl;

import com.LMZ.RPC.myRPCV0.common.User;
import com.LMZ.RPC.myRPCV2.service.UserService;

import java.util.Random;
import java.util.UUID;

public class UserServiceImpl implements UserService {
    @Override
    public User getUserById(Integer id) {
        System.out.println("客户端查询了" + id + "的用户");
        Random random = new Random();
        User user = User.builder().userName(UUID.randomUUID().toString())
                .id(id)
                .sex(random.nextBoolean()).build();
        return user;
    }

    @Override
    public Integer insertUser(User user) {
        System.out.println("插入数据成功" + user);
        return 1;
    }
}