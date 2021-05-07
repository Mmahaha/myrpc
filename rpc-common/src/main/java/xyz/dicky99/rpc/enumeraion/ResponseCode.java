package xyz.dicky99.rpc.enumeraion;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Ysj
 * @version 1.0
 * @description 此处用来定义服务器返回的状态码
 * @date 2021/5/7 16:46
 */
@AllArgsConstructor
@Getter
public enum ResponseCode {
    SUCCESS(200,"调用成功"),
    FAIL(500,"调用失败"),
    METHOD_NOT_FOUND(404, "未找到指定方法"),
    CLASS_NOT_FOUND(500,"未找到指定类");
    
    private final int code;
    private final String message;
}
