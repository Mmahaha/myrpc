package xyz.dicky99.server;

import xyz.dicky99.rpc.api.HelloService;
import xyz.dicky99.rpc.server.RpcServer;

/**
 * @author Ysj
 * @version 1.0
 * @description TODO
 * @date 2021/5/7 18:39
 */
public class MyServer {
    public static void main(String[] args) {
        HelloService helloService = new HelloServiceImpl();
        RpcServer rpcServer = new RpcServer();
        rpcServer.register(helloService, 9000);
    }
}
