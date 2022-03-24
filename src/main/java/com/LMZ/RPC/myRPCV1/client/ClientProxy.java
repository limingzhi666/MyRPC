package com.LMZ.RPC.myRPCV1.client;

import com.LMZ.RPC.myRPCV1.common.RPCRequest;
import com.LMZ.RPC.myRPCV1.common.RPCResponse;
import lombok.AllArgsConstructor;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

@AllArgsConstructor
public class ClientProxy implements InvocationHandler {
    //传入参数Service接口的Class对象，反射封装成为一个Request
    private String host;
    private int port;

    // jdk 动态代理，每一次代理对象调用方法，会经过此方法增强（反射获取request对象，socket发送至客户端）
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        RPCRequest request = RPCRequest.builder().interfaceName(method.getDeclaringClass().getName())
                .methodName(method.getName())
                .params(args).paramsType(method.getParameterTypes()).build();
        //进行数据传输
        RPCResponse response = IOClient.sendRequest(host, port, request);
        return response.getData();
    }

    <T> T getProxy(Class<T> clazz) {
        T t = (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz}, (proxy, method, args) -> invoke(proxy, method, args));
        return t;
    }
}