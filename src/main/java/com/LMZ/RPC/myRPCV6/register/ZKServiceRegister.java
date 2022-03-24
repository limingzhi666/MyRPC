package com.LMZ.RPC.myRPCV6.register;

import com.LMZ.RPC.myRPCV6.loadbanlance.LoadBalance;
import com.LMZ.RPC.myRPCV6.loadbanlance.RandomLoadBalance;
import com.LMZ.RPC.myRPCV6.loadbanlance.RoundLoadBalance;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;

import java.net.InetSocketAddress;
import java.util.List;

public class ZKServiceRegister implements ServiceRegister {
    // curator 提供的zookeeper客户端
    private CuratorFramework client;
    //zookeeper根路径
    private static final String ROOT_PATH = "MyRPC";
    // 初始化负载均衡器， 这里用的是随机， 一般通过构造函数传入
    private LoadBalance loadBalance;

    // 这里负责zookeeper客户端的初始化，并与zookeeper服务端建立连接

    public ZKServiceRegister() {
        //指定时间重试
        RetryPolicy policy = new ExponentialBackoffRetry(1000, 3);
        // zookeeper的地址固定，不管是服务提供者还是，消费者都要与之建立连接
        // sessionTimeoutMs 与 zoo.cfg中的tickTime 有关系，
        // zk还会根据minSessionTimeout与maxSessionTimeout两个参数重新调整最后的超时值。默认分别为tickTime 的2倍和20倍
        // 使用心跳监听状态
        this.client = CuratorFrameworkFactory.builder().connectString("127.0.0.1:2181")
                .sessionTimeoutMs(40000).retryPolicy(policy).namespace(ROOT_PATH).build();

        this.client.start();
        loadBalance = new RoundLoadBalance();
        System.out.println("Zookeeper连接成功");
    }

    public ZKServiceRegister(LoadBalance loadBalance) {
        //指定时间重试
        RetryPolicy policy = new ExponentialBackoffRetry(1000, 3);
        // zookeeper的地址固定，不管是服务提供者还是，消费者都要与之建立连接
        // sessionTimeoutMs 与 zoo.cfg中的tickTime 有关系，
        // zk还会根据minSessionTimeout与maxSessionTimeout两个参数重新调整最后的超时值。默认分别为tickTime 的2倍和20倍
        // 使用心跳监听状态
        this.client = CuratorFrameworkFactory.builder().connectString("127.0.0.1:2181")
                .sessionTimeoutMs(40000).retryPolicy(policy).namespace(ROOT_PATH).build();

        this.client.start();
        this.loadBalance = loadBalance;
        System.out.println("Zookeeper连接成功");
    }

    //服务注册
    @Override
    public void register(String serviceName, InetSocketAddress serverAddress) {
        try {
            //serviceName 创建成永久节点，服务提供者下线时，不删除服务名，只删除地址
            if (client.checkExists().forPath("/" + serviceName) == null) {
                client.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT).forPath("/" + serviceName);
            }
            //路径地址，一个/代表一个节点
            String path = "/" + serviceName + "/" + getServiceAddress(serverAddress);
            //创建临时节点，服务器下线就删除
            client.create().creatingParentContainersIfNeeded().withMode(CreateMode.EPHEMERAL).forPath(path);
        } catch (Exception e) {
            System.out.println("该服务已经存在");
        }
    }

    //服务发现
    @Override
    public InetSocketAddress serviceDiscovery(String serverName) {
        try {
            List<String> strings = client.getChildren().forPath("/" + serverName);
            String choose = loadBalance.balance(strings);
            return parseAddress(choose);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //将地址转化为 ip:port 的字符串格式
    public String getServiceAddress(InetSocketAddress serverAddress) {
        return serverAddress.getHostName() + ":" + serverAddress.getPort();
    }

    // ip:port 的字符串格式转化为地址
    public InetSocketAddress parseAddress(String address) {
        String[] split = address.split(":");
        return new InetSocketAddress(split[0], Integer.parseInt(split[1]));
    }

}