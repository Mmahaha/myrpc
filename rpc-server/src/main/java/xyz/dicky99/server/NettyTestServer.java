package xyz.dicky99.server;

import netty.server.MyNettyServer;
import xyz.dicky99.rpc.api.HelloService;
import xyz.dicky99.rpc.registry.DefaultServiceRegistry;
import xyz.dicky99.rpc.registry.ServiceRegistry;

/**
 * @author Ysj
 * @version 1.0
 * @description TODO
 * @date 2021/5/8 9:39
 */
public class NettyTestServer {
    public static void main(String[] args) {
        HelloService helloService = new HelloServiceImpl();
        ServiceRegistry registry = new DefaultServiceRegistry();
        registry.register(helloService);
        MyNettyServer server = new MyNettyServer();
        server.start(9999);
    }
}
