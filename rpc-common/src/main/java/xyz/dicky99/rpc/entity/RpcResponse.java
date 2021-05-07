package xyz.dicky99.rpc.entity;

import lombok.Data;
import xyz.dicky99.rpc.enumeraion.ResponseCode;

import java.io.Serializable;

/**
 * @author Ysj
 * @version 1.0
 * @description 此处定义的是服务端向客户端回复时的格式
 * @date 2021/5/7 16:39
 */
@Data
public class RpcResponse<T> implements Serializable {
    /**
     * 响应状态码
     */
    private Integer statusCode;

    /**
     * 响应状态的补充信息
     */
    private String message;

    /**
     * 响应数据
     */
    private T data;

    //定义调用成功返回的状态码及返回的数据
    public static <T> RpcResponse<T> success(T data){
        RpcResponse<T> response = new RpcResponse<>();
        response.setStatusCode(ResponseCode.SUCCESS.getCode());
        response.setData(data);
        return response;
    }

    //定义调用失败后返回的状态码及状态信息
    public static <T> RpcResponse<T> fail(ResponseCode code){
        RpcResponse<T> response = new RpcResponse<>();
        response.setStatusCode(code.getCode());
        response.setMessage(code.getMessage());
        return response;
    }
}
