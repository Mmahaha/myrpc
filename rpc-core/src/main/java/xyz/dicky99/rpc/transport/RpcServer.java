package xyz.dicky99.rpc.transport;


import xyz.dicky99.rpc.serializer.CommonSerializer;

/**
 * @author Ysj
 * @version 1.0
 * @description 服务器类通用接口
 * @date 2021/5/7 18:33
 */
public interface RpcServer {
    void start();
    <T> void publishService(Object service, Class<T> serviceClass);
    void setSerializer(CommonSerializer serializer);
}
