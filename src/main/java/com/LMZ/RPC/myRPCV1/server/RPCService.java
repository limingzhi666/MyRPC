package com.LMZ.RPC.myRPCV1.server;

import com.LMZ.RPC.myRPCV1.common.RPCRequest;
import com.LMZ.RPC.myRPCV1.common.RPCResponse;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;

public class RPCService {
    public static void main(String[] args) {
        UserServiceImpl userService = new UserServiceImpl();
        try {
            ServerSocket serverSocket = new ServerSocket(8899);
            System.out.println("服务器启动了");
            while (true) {
                Socket socket = serverSocket.accept();
                new Thread(() -> {
                    try {
                        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                        //读取客户端传过来的Request
                        RPCRequest request = (RPCRequest) ois.readObject();
                        //反射调用方法
                        Method method = userService.getClass().getMethod(request.getMethodName(), request.getParamsType());
                        Object invoke = method.invoke(userService, request.getParams());
                        //封装写入response对象
                        oos.writeObject(RPCResponse.success(invoke));
                        oos.flush();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }).start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}