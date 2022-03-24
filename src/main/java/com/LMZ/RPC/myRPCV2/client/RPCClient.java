package com.LMZ.RPC.myRPCV2.client;

import com.LMZ.RPC.myRPCV0.common.User;
import com.LMZ.RPC.myRPCV2.common.entity.Blog;
import com.LMZ.RPC.myRPCV2.service.BlogService;
import com.LMZ.RPC.myRPCV2.service.UserService;

public class RPCClient {
    public static void main(String[] args) {
        ClientProxy clientProxy = new ClientProxy("127.0.0.1", 8899);
        UserService proxy = clientProxy.getProxy(UserService.class);

        //调用服务方法一
        User userById = proxy.getUserById(10);
        System.out.println("从服务端得到的user为：" + userById);

        //调用方法二
        User user = User.builder().userName("tom").id(100).sex(true).build();
        Integer integer = proxy.insertUser(user);
        System.out.println("向服务端插入数据：" + integer);

        //调用其他接口中的方法
        BlogService blogService = clientProxy.getProxy(BlogService.class);
        Blog blogById = blogService.getBlogById(1000);
        System.out.println("从服务端得到的blog为：" + blogById);
    }
}