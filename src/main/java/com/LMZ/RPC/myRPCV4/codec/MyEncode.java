package com.LMZ.RPC.myRPCV4.codec;

import com.LMZ.RPC.myRPCV3.common.RPCRequest;
import com.LMZ.RPC.myRPCV3.common.RPCResponse;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class MyEncode extends MessageToByteEncoder {
    private Serializer serializer;

    @Override
    protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {
        //写入消息类型
        if (msg instanceof RPCRequest) {
            out.writeShort(MessageType.REQUEST.getCode());
        } else if (msg instanceof RPCResponse) {
            out.writeShort(MessageType.RESPONSE.getCode());
        }
        //写入序列化方式
        out.writeShort(serializer.getType());
        //得到序列化数组
        byte[] bytes = this.serializer.serializer(msg);
        //写入长度
        out.writeInt(bytes.length);
        //写入序列化字节数组
        out.writeBytes(bytes);
    }
}