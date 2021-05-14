package xyz.dicky99.server;

import xyz.dicky99.rpc.api.HelloService;
import xyz.dicky99.rpc.api.AddService;
import xyz.dicky99.rpc.serializer.ProtostuffSerializer;
import xyz.dicky99.rpc.transport.netty.server.MyNettyServer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Ysj
 * @version 1.0
 * @description Netty服务端2，绑定9998
 * @date 2021/5/10 23:38
 */
public class NettyTestServer9998 {
    public static void main(String[] args) {
        ConcurrentHashMap<Object, Class> serviceMap = new ConcurrentHashMap<>();
        serviceMap.put(new HelloServiceImpl(), HelloService.class);
        serviceMap.put(new AddServiceImpl(), AddService.class);
        MyNettyServer server = new MyNettyServer("127.0.0.1", 9998);
        server.setSerializer(new ProtostuffSerializer());
        server.publishService(serviceMap);
    }
}
