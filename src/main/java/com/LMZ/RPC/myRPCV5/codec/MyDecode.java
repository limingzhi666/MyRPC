package com.LMZ.RPC.myRPCV5.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import lombok.AllArgsConstructor;

import java.util.List;

/**
 * 按照自定义的消息格式解码数据
 */
@AllArgsConstructor
public class MyDecode extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        //读取消息类型
        short messageType = in.readShort();
        if (messageType != MessageType.REQUEST.getCode() && messageType != MessageType.RESPONSE.getCode()) {
            System.out.println("暂时不支持该类型");
            return;
        }
        //读取序列化类型
        short serializerType = in.readShort();
        //根据类型得到对应的序列化器
        Serializer serializer = Serializer.getSerializerByCode(serializerType);
        if (serializer == null) {
            throw new RuntimeException("不存在该序列化器");
        }
        //读取数据序列化后的字节长度
        int len = in.readInt();
        //读取序列化数组
        byte[] bytes = new byte[len];
        in.readBytes(bytes);
        //用对应的序列化解码器解码字节数组
        Object deserializer = serializer.deserializer(bytes, messageType);
        out.add(deserializer);
    }
}