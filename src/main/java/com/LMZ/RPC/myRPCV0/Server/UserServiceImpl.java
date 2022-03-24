package com.LMZ.RPC.myRPCV0.Server;

import com.LMZ.RPC.myRPCV0.common.User;
import com.LMZ.RPC.myRPCV0.service.UserService;

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
}