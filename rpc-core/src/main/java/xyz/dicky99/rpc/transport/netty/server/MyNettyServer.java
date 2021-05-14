package xyz.dicky99.rpc.transport.netty.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.dicky99.rpc.provider.ServiceProvider;
import xyz.dicky99.rpc.provider.ServiceProviderImpl;
import xyz.dicky99.rpc.registry.NacosServiceRegistry;
import xyz.dicky99.rpc.serializer.ProtostuffSerializer;
import xyz.dicky99.rpc.transport.RpcServer;
import xyz.dicky99.rpc.codec.CommonDecoder;
import xyz.dicky99.rpc.codec.CommonEncoder;
import xyz.dicky99.rpc.enumeraion.RpcError;
import xyz.dicky99.rpc.exception.RpcException;
import xyz.dicky99.rpc.registry.ServiceRegistry;
import xyz.dicky99.rpc.serializer.CommonSerializer;

import java.net.InetSocketAddress;
import java.util.List;
import java.util.Map;

/**
 * @author Ysj
 * @version 1.0
 * @description TODO
 * @date 2021/5/8 9:07
 */
public class MyNettyServer implements RpcServer {
    private static final Logger logger = LoggerFactory.getLogger(MyNettyServer.class);
    private final String host;
    private final int port;
    private final ServiceRegistry serviceRegistry;
    private final ServiceProvider serviceProvider;
    private CommonSerializer serializer;

    public MyNettyServer(String host, int port) {
        this.host = host;
        this.port = port;
        serviceRegistry = new NacosServiceRegistry();
        serviceProvider = new ServiceProviderImpl();
    }

    public <T> void publishService(Map<Object, Class> serviceMap) {
        if(serializer == null) {
            logger.error("未设置序列化器");
            throw new RpcException(RpcError.SERIALIZER_NOT_FOUND);
        }

        for(Map.Entry<Object, Class> entry : serviceMap.entrySet() ){
            serviceProvider.addServiceProvider(entry.getKey());
            serviceRegistry.register(entry.getValue().getCanonicalName(), new InetSocketAddress(host, port));
        }
        start();
    }


    @Override
    public void start() {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {

            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .option(ChannelOption.SO_BACKLOG, 256)
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    .childOption(ChannelOption.TCP_NODELAY, true)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast(new CommonEncoder(new ProtostuffSerializer()));
                            pipeline.addLast(new CommonDecoder());
                            pipeline.addLast(new NettyServerHandler());
                        }
                    });
            ChannelFuture future = serverBootstrap.bind(port).sync();
            future.channel().closeFuture().sync();

        } catch (InterruptedException e) {
            logger.error("启动服务器时有错误发生: ", e);
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }


    @Override
    public void setSerializer(CommonSerializer serializer) {
        this.serializer = serializer;
    }
}
