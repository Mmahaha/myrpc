package xyz.dicky99.rpc.exception;

import xyz.dicky99.rpc.enumeraion.RpcError;

/**
 * @author Ysj
 * @version 1.0
 * @description RPC调用异常
 * @date 2021/5/7 23:39
 */
public class RpcException extends RuntimeException {

    public RpcException(RpcError error, String detail) {
        super(error.getMessage() + ": " + detail);
    }

    public RpcException(String message, Throwable cause) {
        super(message, cause);
    }

    public RpcException(RpcError error) {
        super(error.getMessage());
    }

}