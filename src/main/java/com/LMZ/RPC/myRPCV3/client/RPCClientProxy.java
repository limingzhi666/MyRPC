package com.LMZ.RPC.myRPCV3.client;

import com.LMZ.RPC.myRPCV3.common.RPCRequest;
import com.LMZ.RPC.myRPCV3.common.RPCResponse;
import lombok.AllArgsConstructor;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

@AllArgsConstructor
public class RPCClientProxy implements InvocationHandler {
    private RPCClient client;

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //request的构建
        RPCRequest request = RPCRequest.builder().interfaceName(method.getDeclaringClass().getName())
                .methodName(method.getName())
                .params(args).paramsType(method.getParameterTypes()).build();
        //数据传输
        RPCResponse response = client.sendRequest(request);
        return response.getData();
    }

    <T> T getProxy(Class<T> clazz) {
        Object o = Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz}, this::invoke);
        return (T) o;
    }
}