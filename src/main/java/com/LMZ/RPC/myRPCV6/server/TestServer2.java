package com.LMZ.RPC.myRPCV6.server;

import com.LMZ.RPC.myRPCV6.server.Netty.NettyRPCServer;
import com.LMZ.RPC.myRPCV6.service.BlogService;
import com.LMZ.RPC.myRPCV6.service.Impl.BlogServiceImpl;
import com.LMZ.RPC.myRPCV6.service.Impl.UserServiceImpl;
import com.LMZ.RPC.myRPCV6.service.UserService;

/*
测试负载均衡
 */
public class TestServer2 {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();
        BlogService blogService = new BlogServiceImpl();

        // 这里重用了服务暴露类，顺便在注册中心注册，实际上应分开，每个类做各自独立的事
        ServiceProvider serviceProvider = new ServiceProvider("127.0.0.1", 8900);
        serviceProvider.provideServiceInterface(userService);
        serviceProvider.provideServiceInterface(blogService);

        RPCServer RPCServer = new NettyRPCServer(serviceProvider);
        RPCServer.start(8900);
    }
}