package com.LMZ.RPC.myRPCV3.server;

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

    public ServiceProvider() {
        this.interfaceProvider = new HashMap<>();
    }

    public void provideServiceInterface(Object service) {
        Class<?>[] interfaces = service.getClass().getInterfaces();

        for (Class<?> anInterface : interfaces) {
            interfaceProvider.put(anInterface.getName(), service);
        }
    }

    public Object getService(String interfaceName) {
        return interfaceProvider.get(interfaceName);
    }
}