package xyz.dicky99.server;

import xyz.dicky99.rpc.api.HelloService;
import xyz.dicky99.rpc.serializer.ProtobufSerializer;
import xyz.dicky99.rpc.transport.netty.server.MyNettyServer;

/**
 * @author Ysj
 * @version 1.0
 * @description Netty服务端2，绑定9998
 * @date 2021/5/10 23:38
 */
public class NettyTestServer9998 {
    public static void main(String[] args) {
        HelloService helloService = new HelloServiceImpl();
        MyNettyServer server = new MyNettyServer("127.0.0.1", 9998);
        server.setSerializer(new ProtobufSerializer());
        server.publishService(helloService, HelloService.class);
    }
}
