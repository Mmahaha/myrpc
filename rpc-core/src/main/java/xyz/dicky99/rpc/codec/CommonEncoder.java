package xyz.dicky99.rpc.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import xyz.dicky99.rpc.entity.RpcRequest;
import xyz.dicky99.rpc.enumeraion.PackageType;
import xyz.dicky99.rpc.serializer.CommonSerializer;

/**
 * @author Ysj
 * @version 1.0
 * @description 此处为自定义编码器，协议包格式如下
   +---------------+---------------+-----------------+-------------+
   |  Magic Number |  Package Type | Serializer Type | Data Length |
   |    4 bytes    |    4 bytes    |     4 bytes     |   4 bytes   |
   +---------------+---------------+-----------------+-------------+
   |                          Data Bytes                           |
   |                   Length: ${Data Length}                      |
   +---------------------------------------------------------------+
 * @date 2021/5/8 9:21
 */
public class CommonEncoder extends MessageToByteEncoder {
    private static final int MAGIC_NUMBER = 0xCAFEBABE;

    private final CommonSerializer serializer;

    public CommonEncoder(CommonSerializer serializer){
        this.serializer = serializer;
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {
        out.writeInt(MAGIC_NUMBER);
        if(msg instanceof RpcRequest) {
            out.writeInt(PackageType.REQUEST_PACK.getCode());
        } else {
            out.writeInt(PackageType.RESPONSE_PACK.getCode());
        }
        out.writeInt(serializer.getCode());
        byte[] bytes = serializer.serialize(msg);
        out.writeInt(bytes.length);
        out.writeBytes(bytes);
    }
}
