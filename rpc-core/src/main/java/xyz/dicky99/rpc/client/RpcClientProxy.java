package xyz.dicky99.rpc.client;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import xyz.dicky99.rpc.entity.RpcRequest;
import xyz.dicky99.rpc.entity.RpcResponse;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author Ysj
 * @version 1.0
 * @description 实现rpc客户端的动态代理
 * @date 2021/5/7 17:14
 */

@AllArgsConstructor
public class RpcClientProxy implements InvocationHandler {
    private final String host;
    private final int port;

    @SuppressWarnings("unchecked")
    public <T> T getProxy(Class<T> clazz) {
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class<?>[]{clazz}, this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        RpcRequest rpcRequest = RpcRequest.builder()
                .interfaceName(method.getDeclaringClass().getName())
                .methodName(method.getName())
                .parameters(args)
                .paramTypes(method.getParameterTypes())
                .build();
        RpcClient rpcClient = new RpcClient();
        return ((RpcResponse) rpcClient.sendRequest(rpcRequest, host, port)).getData();
    }
}
