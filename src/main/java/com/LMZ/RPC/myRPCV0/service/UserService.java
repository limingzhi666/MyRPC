package com.LMZ.RPC.myRPCV0.service;

import com.LMZ.RPC.myRPCV0.common.User;

public interface UserService {
    //客户端通过这个接口调用服务端的实现类
    User getUserById(Integer id);

}