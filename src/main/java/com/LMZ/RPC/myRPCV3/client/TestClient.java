package com.LMZ.RPC.myRPCV3.client;

import com.LMZ.RPC.myRPCV3.client.Netty.NettyClient;
import com.LMZ.RPC.myRPCV3.common.entity.Blog;
import com.LMZ.RPC.myRPCV3.common.entity.User;
import com.LMZ.RPC.myRPCV3.service.BlogService;
import com.LMZ.RPC.myRPCV3.service.UserService;

public class TestClient {
    public static void main(String[] args) {
        RPCClient client = new NettyClient("127.0.0.1", 8899);
        RPCClientProxy rpcClientProxy = new RPCClientProxy(client);
        UserService userService = rpcClientProxy.getProxy(UserService.class);
        // 调用方法
        User userByUserId = userService.getUserById(10);
        System.out.println("从服务端得到的user为：" + userByUserId);
        User user = User.builder().userName("张三").id(100).sex(true).build();
        Integer integer = userService.insertUser(user);
        System.out.println("向服务端插入数据：" + integer);

        BlogService blogService = rpcClientProxy.getProxy(BlogService.class);

        Blog blogById = blogService.getBlogById(10000);
        System.out.println("从服务端得到的blog为：" + blogById);
    }
}