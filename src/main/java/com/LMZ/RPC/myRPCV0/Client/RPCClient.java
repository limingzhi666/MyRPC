package com.LMZ.RPC.myRPCV0.Client;

import com.LMZ.RPC.myRPCV0.common.User;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Random;

public class RPCClient {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("127.0.0.1", 8899);
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            //传id给服务器
            oos.writeInt(new Random().nextInt());
            oos.flush();
            //服务器查询数据返回
            User user = (User) ois.readObject();
            System.out.println("服务器返回user对象" + user);
        } catch (Exception e) {
            System.out.println("客户端启动失败");
        }
    }
}