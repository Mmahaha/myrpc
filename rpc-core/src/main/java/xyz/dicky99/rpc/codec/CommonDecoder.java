package xyz.dicky99.rpc.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.dicky99.rpc.entity.RpcRequest;
import xyz.dicky99.rpc.entity.RpcResponse;
import xyz.dicky99.rpc.enumeraion.PackageType;
import xyz.dicky99.rpc.enumeraion.RpcError;
import xyz.dicky99.rpc.exception.RpcException;
import xyz.dicky99.rpc.serializer.CommonSerializer;

import java.util.List;

/**
 * @author Ysj
 * @version 1.0
 * @description 通用解码器，自定义协议格式如下
   +---------------+---------------+-----------------+-------------+
   |  Magic Number |  Package Type | Serializer Type | Data Length |
   |    4 bytes    |    4 bytes    |     4 bytes     |   4 bytes   |
   +---------------+---------------+-----------------+-------------+
   |                          Data Bytes                           |
   |                   Length: ${Data Length}                      |
   +---------------------------------------------------------------+
 * @date 2021/5/8 9:28
 */
public class CommonDecoder extends ReplayingDecoder {

    private static final Logger logger = LoggerFactory.getLogger(CommonDecoder.class);
    private static final int MAGIC_NUMBER = 0xCAFEBABE;

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        int magic = in.readInt();
        if(magic != MAGIC_NUMBER) {
            logger.error("不识别的协议包: {}", magic);
            throw new RpcException(RpcError.UNKNOWN_PROTOCOL);
        }
        int packageCode = in.readInt();
        Class<?> packageClass;
        if(packageCode == PackageType.REQUEST_PACK.getCode()) {
            packageClass = RpcRequest.class;
        } else if(packageCode == PackageType.RESPONSE_PACK.getCode()) {
            packageClass = RpcResponse.class;
        } else {
            logger.error("不识别的数据包: {}", packageCode);
            throw new RpcException(RpcError.UNKNOWN_PACKAGE_TYPE);
        }
        int serializerCode = in.readInt();
        CommonSerializer serializer = CommonSerializer.getByCode(serializerCode);
        if(serializer == null) {
            logger.error("不识别的反序列化器: {}", serializerCode);
            throw new RpcException(RpcError.UNKNOWN_SERIALIZER);
        }
        int length = in.readInt();
        byte[] bytes = new byte[length];
        in.readBytes(bytes);
        Object obj = serializer.deserialize(bytes, packageClass);
        out.add(obj);
    }

}