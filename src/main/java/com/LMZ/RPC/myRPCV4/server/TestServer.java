package com.LMZ.RPC.myRPCV4.server;

import com.LMZ.RPC.myRPCV4.server.Netty.NettyRPCServer;
import com.LMZ.RPC.myRPCV4.service.BlogService;
import com.LMZ.RPC.myRPCV4.service.Impl.BlogServiceImpl;
import com.LMZ.RPC.myRPCV4.service.Impl.UserServiceImpl;
import com.LMZ.RPC.myRPCV4.service.UserService;

public class TestServer {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();
        BlogService blogService = new BlogServiceImpl();

        ServiceProvider serviceProvider = new ServiceProvider();
        serviceProvider.provideServiceInterface(userService);
        serviceProvider.provideServiceInterface(blogService);

        RPCServer RPCServer = new NettyRPCServer(serviceProvider);
        RPCServer.start(8899);

    }
}