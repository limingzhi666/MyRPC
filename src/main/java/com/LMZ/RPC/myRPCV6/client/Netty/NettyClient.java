package com.LMZ.RPC.myRPCV6.client.Netty;

import com.LMZ.RPC.myRPCV6.client.RPCClient;
import com.LMZ.RPC.myRPCV6.common.RPCRequest;
import com.LMZ.RPC.myRPCV6.common.RPCResponse;
import com.LMZ.RPC.myRPCV6.loadbanlance.LoadBalance;
import com.LMZ.RPC.myRPCV6.register.ServiceRegister;
import com.LMZ.RPC.myRPCV6.register.ZKServiceRegister;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.AttributeKey;

import java.net.InetSocketAddress;

public class NettyClient implements RPCClient {

    private static final EventLoopGroup eventLoopGroup;
    private static final Bootstrap bootstrap;
    private ServiceRegister serviceRegister;
    private String host;
    private int port;

    public NettyClient() {
        serviceRegister = new ZKServiceRegister();
    }

    public NettyClient(LoadBalance loadBalance) {
        serviceRegister = new ZKServiceRegister(loadBalance);
    }
    //初始化
    static {
        eventLoopGroup = new NioEventLoopGroup();
        bootstrap = new Bootstrap();
        bootstrap.group(eventLoopGroup)
                .channel(NioSocketChannel.class)
                .handler(new NettyClientInitializer());
    }

    /**
     * 这里需要操作一下，因为netty的传输都是异步的，你发送request，会立刻返回一个值， 而不是想要的相应的response
     */
    @Override
    public RPCResponse sendRequest(RPCRequest request) {
        InetSocketAddress address = serviceRegister.serviceDiscovery(request.getInterfaceName());
        host = address.getHostName();
        port = address.getPort();
        try {
            ChannelFuture channelFuture = bootstrap.connect(host, port).sync();
            Channel channel = channelFuture.channel();

            //发送数据
            channel.writeAndFlush(request);
            channel.closeFuture().sync();
            //阻塞获得的结果，通过给channel设计别名，获取特定名字下的channel下的内容(在handler中进行设置)
            //AttributeKey是线程隔离的，不会存在线程安全问题
            //实际上不应该通过阻塞，而是回调函数
            AttributeKey<RPCResponse> key = AttributeKey.valueOf("RPCResponse");
            RPCResponse response = channel.attr(key).get();
            System.out.println(response);
            return response;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}