package xyz.dicky99.server;

import xyz.dicky99.rpc.serializer.ProtobufSerializer;
import xyz.dicky99.rpc.transport.netty.server.MyNettyServer;
import xyz.dicky99.rpc.api.HelloService;

/**
 * @author Ysj
 * @version 1.0
 * @description TODO
 * @date 2021/5/8 9:39
 */
public class NettyTestServer {
    public static void main(String[] args) {
        HelloService helloService = new HelloServiceImpl();
        MyNettyServer server = new MyNettyServer("127.0.0.1", 9999);
        server.setSerializer(new ProtobufSerializer());
        server.publishService(helloService, HelloService.class);
    }
}
