package xyz.dicky99.rpc;

import xyz.dicky99.rpc.serializer.ProtostuffSerializer;
import xyz.dicky99.rpc.transport.netty.client.MyNettyClient;
import xyz.dicky99.rpc.transport.RpcClient;
import xyz.dicky99.rpc.transport.RpcClientProxy;
import xyz.dicky99.rpc.api.HelloObject;
import xyz.dicky99.rpc.api.HelloService;

/**
 * @author Ysj
 * @version 1.0
 * @description 此处为netty的客户端
 * @date 2021/5/8 9:40
 */
public class NettyTestClient {
    public static void main(String[] args) throws Exception {
        RpcClient client = new MyNettyClient();
        client.setSerializer(new ProtostuffSerializer());
        RpcClientProxy rpcClientProxy = new RpcClientProxy(client);
        HelloService helloService = rpcClientProxy.getProxy(HelloService.class);
        for (int i = 0; i < 5; i++) {
            HelloObject object = new HelloObject(i, "This is a message" + i);
            String res = helloService.hello(object);
            System.out.println(i + " " + res + "\n");
            Thread.sleep(3 * 1000);
        }

    }
}
