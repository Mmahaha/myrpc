package xyz.dicky99.rpc.enumeraion;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Ysj
 * @version 1.0
 * @description RPC调用过程中的错误
 * @date 2021/5/7 23:40
 */
@AllArgsConstructor
@Getter
public enum RpcError {

    SERVICE_INVOCATION_FAILURE("服务调用出现失败"),
    SERVICE_NOT_FOUND("找不到对应的服务"),
    SERVICE_NOT_IMPLEMENT_ANY_INTERFACE("注册的服务未实现接口");

    private final String message;

}