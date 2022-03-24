package com.LMZ.RPC.myRPCV2.server;

import com.LMZ.RPC.myRPCV2.service.BlogService;
import com.LMZ.RPC.myRPCV2.service.Impl.BlogServiceImpl;
import com.LMZ.RPC.myRPCV2.service.Impl.UserServiceImpl;
import com.LMZ.RPC.myRPCV2.service.UserService;

public class TestServer {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();
        BlogService blogService = new BlogServiceImpl();

        ServiceProvider serviceProvider = new ServiceProvider();
        serviceProvider.provideServiceInterface(userService);
        serviceProvider.provideServiceInterface(blogService);

        ThreadPoolRPCRPCServer RPCServer = new ThreadPoolRPCRPCServer(serviceProvider);

        RPCServer.start(8899);
    }
}