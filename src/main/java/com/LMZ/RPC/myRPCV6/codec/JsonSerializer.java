package com.LMZ.RPC.myRPCV6.codec;

import com.LMZ.RPC.myRPCV6.common.RPCRequest;
import com.LMZ.RPC.myRPCV6.common.RPCResponse;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * 由于json序列化的方式是通过把对象转化成字符串，丢失了Data对象的类信息，
 * 所以deserialize需要了解对象对象的类信息，根据类信息把JsonObject -> 对应的对象
 */
public class JsonSerializer implements Serializer {
    @Override
    public byte[] serializer(Object obj) {
        byte[] bytes = JSONObject.toJSONBytes(obj);
        return bytes;
    }

    @Override
    public Object deserializer(byte[] bytes, int messageType) {
        Object obj;
        switch (messageType) {
            case 0:
                RPCRequest request = JSON.parseObject(bytes, RPCRequest.class);
                if (request.getParams() == null) {
                    return request;
                }
                Object[] objects = new Object[request.getParams().length];
                //把json字符串转化成对应的对象，fastjson可以读出基本数据类型不用转
                for (int i = 0; i < objects.length; i++) {
                    Class<?> paramsType = request.getParamsType()[i];
                    if (!paramsType.isAssignableFrom(request.getParams().getClass())) {
                        objects[i] = JSONObject.toJavaObject((JSONObject) request.getParams()[i], request.getParamsType()[i]);
                    } else {
                        objects[i] = request.getParams()[i];
                    }
                }
                request.setParams(objects);
                obj = request;
                break;
            case 1:
                RPCResponse response = JSON.parseObject(bytes, RPCResponse.class);
                Class<?> dataType = response.getDataType();
                if (!dataType.isAssignableFrom(response.getData().getClass())) {
                    response.setData(JSONObject.toJavaObject((JSONObject) response.getData(), dataType));
                }
                obj = response;
                break;

            default:
                System.out.println("不支持该类型消息");
                throw new RuntimeException();
        }
        return obj;
    }

    // 1 代表着json序列化方式
    @Override
    public int getType() {
        return 1;
    }
}