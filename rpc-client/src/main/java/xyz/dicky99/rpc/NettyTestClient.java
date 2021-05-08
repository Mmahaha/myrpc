package xyz.dicky99.rpc;

import netty.client.MyNettyClient;
import xyz.dicky99.rpc.api.HelloObject;
import xyz.dicky99.rpc.api.HelloService;

/**
 * @author Ysj
 * @version 1.0
 * @description 此处为netty的客户端
 * @date 2021/5/8 9:40
 */
public class NettyTestClient {
    public static void main(String[] args) {
        RpcClient client = new MyNettyClient("127.0.0.1", 9999);
        RpcClientProxy rpcClientProxy = new RpcClientProxy(client);
        HelloService helloService = rpcClientProxy.getProxy(HelloService.class);
        HelloObject object = new HelloObject(12, "This is a message");
        String res = helloService.hello(object);
        System.out.println(res);
    }
}
