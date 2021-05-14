package xyz.dicky99.rpc.transport.socket.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.dicky99.rpc.enumeraion.RpcError;
import xyz.dicky99.rpc.exception.RpcException;
import xyz.dicky99.rpc.provider.ServiceProvider;
import xyz.dicky99.rpc.provider.ServiceProviderImpl;
import xyz.dicky99.rpc.registry.NacosServiceRegistry;
import xyz.dicky99.rpc.serializer.CommonSerializer;
import xyz.dicky99.rpc.transport.RpcServer;
import xyz.dicky99.rpc.registry.ServiceRegistry;
import xyz.dicky99.rpc.handler.RequestHandler;
import xyz.dicky99.rpc.util.ThreadPoolFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.*;

/**
 * @author Ysj
 * @version 1.0
 * @description Socket方式远程方法调用的提供者
 * @date 2021/5/8 8:58
 */
public class SocketServer implements RpcServer{

    private static final Logger logger = LoggerFactory.getLogger(SocketServer.class);

    private final ExecutorService threadPool;;
    private final String host;
    private final int port;
    private CommonSerializer serializer;
    private RequestHandler requestHandler = new RequestHandler();

    private final ServiceRegistry serviceRegistry;
    private final ServiceProvider serviceProvider;

    public SocketServer(String host, int port) {
        this.host = host;
        this.port = port;
        threadPool = ThreadPoolFactory.createDefaultThreadPool("socket-rpc-server");
        this.serviceRegistry = new NacosServiceRegistry();
        this.serviceProvider = new ServiceProviderImpl();
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


    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            logger.info("服务器启动……");
            Socket socket;
            while ((socket = serverSocket.accept()) != null) {
                logger.info("消费者连接: {}:{}", socket.getInetAddress(), socket.getPort());
                threadPool.execute(new RequestHandlerThread(socket, requestHandler, serviceRegistry, serializer));
            }
            threadPool.shutdown();
        } catch (IOException e) {
            logger.error("服务器启动时有错误发生:", e);
        }
    }

    public void setSerializer(CommonSerializer serializer) {
        this.serializer = serializer;
    }


}