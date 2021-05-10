package xyz.dicky99.rpc.transport;

import xyz.dicky99.rpc.entity.RpcRequest;
import xyz.dicky99.rpc.serializer.CommonSerializer;

/**
 * @author Ysj
 * @version 1.0
 * @description 客户端类通用接口
 * @date 2021/5/7 18:29
 */

public interface RpcClient {

    Object sendRequest(RpcRequest rpcRequest);
    void setSerializer(CommonSerializer serializer);

}