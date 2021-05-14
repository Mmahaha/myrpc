package xyz.dicky99.rpc;

import xyz.dicky99.rpc.api.AddService;
import xyz.dicky99.rpc.api.HelloObject;
import xyz.dicky99.rpc.api.HelloService;
import xyz.dicky99.rpc.serializer.ProtostuffSerializer;
import xyz.dicky99.rpc.transport.RpcClient;
import xyz.dicky99.rpc.transport.RpcClientProxy;
import xyz.dicky99.rpc.transport.netty.client.MyNettyClient;

/**
 * @author Ysj
 * @version 1.0
 * @description TODO
 * @date 2021/5/14 21:41
 */
public class NettyTestClient2 {
    public static void main(String[] args) throws Exception {
        RpcClient client = new MyNettyClient();
        client.setSerializer(new ProtostuffSerializer());
        RpcClientProxy rpcClientProxy = new RpcClientProxy(client);
        AddService addService = rpcClientProxy.getProxy(AddService.class);
        System.out.println(addService.add(1,2));

    }
}
