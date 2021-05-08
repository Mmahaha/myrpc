package xyz.dicky99.rpc.serializer;

/**
 * @author Ysj
 * @version 1.0
 * @description 通用的序列化反序列化接口
 * @date 2021/5/8 9:22
 */
public interface CommonSerializer {
    byte[] serialize(Object obj);

    Object deserialize(byte[] bytes, Class<?> clazz);

    int getCode();

    static CommonSerializer getByCode(int code) {
        switch (code) {
            case 1:
                return new JsonSerializer();
            default:
                return null;
        }
    }
}
