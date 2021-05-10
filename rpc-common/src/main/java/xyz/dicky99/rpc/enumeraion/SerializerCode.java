package xyz.dicky99.rpc.enumeraion;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Ysj
 * @version 1.0
 * @description 此处用来标识字节流中的学历恶化和反序列化器
 * @date 2021/5/8 9:19
 */

@Getter
@AllArgsConstructor
public enum SerializerCode {
    KRYO(0),
    JSON(1),
    PROTOBUF(3);

    private final int code;
}
