package com.LMZ.RPC.myRPCV4.codec;

public interface Serializer {
    //把java对象序列化成byte数字
    byte[] serializer(Object obj);

    // 从字节数组反序列化成消息, 使用java自带序列化方式不用messageType也能得到相应的对象（序列化字节数组里包含类信息）
    // 其它方式需指定消息格式，再根据message转化成相应的对象
    Object deserializer(byte[] bytes, int messageType);

    //返回使用的序列化容器 -- 0表示java自带---1表示json序列化方式
    int getType();

    //根据序号取出序列化器，暂时有两种实现方式
    static Serializer getSerializerByCode(int code) {
        switch (code) {
            case 0:
                return new ObjectSerializer();
            case 1:
                return new JsonSerializer();
            default:
                return null;
        }
    }
}