package com.LMZ.RPC.myRPCV0.Server;

import com.LMZ.RPC.myRPCV0.common.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class RPCServer {
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
                        //读取客户端传入的id
                        Integer id = ois.readInt();
                        User userById = userService.getUserById(id);
                        //写入User返回客户端
                        oos.writeObject(userById);
                        oos.flush();
                    } catch (IOException e) {
                        System.out.println("从IO中读取数据错误");
                    }
                }).start();
            }
        } catch (IOException e) {
            System.out.println("服务器启动失败");
        }
    }
}