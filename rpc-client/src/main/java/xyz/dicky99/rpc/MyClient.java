package xyz.dicky99.rpc;

import xyz.dicky99.rpc.api.HelloObject;
import xyz.dicky99.rpc.api.HelloService;
import xyz.dicky99.rpc.client.RpcClientProxy;

/**
 * @author Ysj
 * @version 1.0
 * @description TODO
 * @date 2021/5/7 18:40
 */
public class MyClient {
    public static void main(String[] args) {
        RpcClientProxy proxy = new RpcClientProxy("127.0.0.1", 9000);
        HelloService helloService = proxy.getProxy(HelloService.class);
        HelloObject object = new HelloObject(12, "This is a message");
        String res = helloService.hello(object);
        System.out.println(res);
    }
}
