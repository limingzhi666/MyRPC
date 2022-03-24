package com.LMZ.RPC.myRPCV6.server;

import com.LMZ.RPC.myRPCV6.register.ServiceRegister;
import com.LMZ.RPC.myRPCV6.register.ZKServiceRegister;

import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;

/**
 * 存放服务接口名与服务端对应的实现类
 * 服务启动时要暴漏相关的实现类
 * 根据request中的interface调用服务端中相关的实现类
 */
public class ServiceProvider {
    /*
    一个实现类可能有多个接口
     */
    private Map<String, Object> interfaceProvider;
    private ServiceRegister serviceRegister;
    private String host;
    private int port;


    public ServiceProvider(String host, int port) {
        //需要传入服务器自身的服务的地址
        this.host = host;
        this.port = port;
        this.interfaceProvider = new HashMap<>();
        this.serviceRegister = new ZKServiceRegister();
    }

    public void provideServiceInterface(Object service) {
        Class<?>[] interfaces = service.getClass().getInterfaces();

        for (Class<?> anInterface : interfaces) {
            interfaceProvider.put(anInterface.getName(), service);
            serviceRegister.register(anInterface.getName(), new InetSocketAddress(host, port));
        }
    }

    public Object getService(String interfaceName) {
        return interfaceProvider.get(interfaceName);
    }
}