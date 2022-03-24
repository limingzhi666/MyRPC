package com.LMZ.RPC.myRPCV6.codec;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ObjectSerializer implements Serializer {

    // 利用java IO 对象 -> 字节数组
    @Override
    public byte[] serializer(Object obj) {
        byte[] bytes = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(obj);
            oos.flush();
            bytes = bos.toByteArray();
            oos.close();
            bos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bytes;
    }

    // 利用 字节数组 --> java IO 对象
    @Override
    public Object deserializer(byte[] bytes, int messageType) {
        Object obj = null;
        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        try {
            ObjectInputStream ois = new ObjectInputStream(bis);
            obj = ois.readObject();
            ois.close();
            bis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }

    // 0 代表java原生序列化器
    @Override
    public int getType() {
        return 0;
    }
}