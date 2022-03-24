package com.LMZ.RPC.myRPCV1.client;

import com.LMZ.RPC.myRPCV1.common.RPCRequest;
import com.LMZ.RPC.myRPCV1.common.RPCResponse;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * 负责底层与服务端的通信，发送的是Request，接收的是Response对象
 * 客户端发起一次请求调用，Socket建立连接，发起请求Request，得到响应Response
 * 这里的Request是封装好的，上层进行了封装，不同的service需要进行不同的封装，客户端只需要直到service接口，需要使用动态代理
 */
public class IOClient {
    public static RPCResponse sendRequest(String host, int port, RPCRequest request) {
        try {
            Socket socket = new Socket(host, port);

            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());

            System.out.println(request);
            oos.writeObject(request);
            oos.flush();

            RPCResponse response = (RPCResponse) ois.readObject();

            return response;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}