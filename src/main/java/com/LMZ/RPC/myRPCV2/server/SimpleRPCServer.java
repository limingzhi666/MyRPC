package com.LMZ.RPC.myRPCV2.server;

import lombok.AllArgsConstructor;

import java.net.ServerSocket;
import java.net.Socket;

/**
 * 这个实现类代表java原生的BIO监听模式，来一个任务，new一个线程处理
 */
public class SimpleRPCServer implements RPCServer {

    private ServiceProvider serviceProvider;

    public SimpleRPCServer(ServiceProvider serviceProvider) {
        this.serviceProvider = serviceProvider;
    }

    @Override
    public void start(int port) {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("服务器启动了");
            //循环监听
            while (true) {
                Socket socket = serverSocket.accept();
                new Thread(new WorkThread(socket, serviceProvider)).start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void stop() {
    }
}