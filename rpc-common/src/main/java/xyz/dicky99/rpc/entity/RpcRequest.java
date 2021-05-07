package xyz.dicky99.rpc.entity;

import lombok.Builder;
import lombok.Data;
import java.io.Serializable;

/**
 * @author Ysj
 * @version 1.0
 * @description 此处定义的是客户端向服务端发送请求时的格式
 * @date 2021/5/7 16:25
 */

@Data
@Builder
public class RpcRequest implements Serializable {
    /**
     * 待调用接口的名称
     */
    private String interfaceName;

    /**
     * 待调用方法的名称
     */
    private String methodName;

    /**
     * 调用方法时所需要的参数
     */
    private Object[] parameters;

    /**
     * 调用方法时所需参数的类型
     */
    private Class<?>[] paramTypes;
}
