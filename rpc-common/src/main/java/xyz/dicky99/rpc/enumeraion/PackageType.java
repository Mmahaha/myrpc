package xyz.dicky99.rpc.enumeraion;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Ysj
 * @version 1.0
 * @description 此处标识编解码协议中的自定义包类型
 * @date 2021/5/8 9:17
 */

@AllArgsConstructor
@Getter
public enum PackageType {
    REQUEST_PACK(0),
    RESPONSE_PACK(1);

    private final int code;
}
