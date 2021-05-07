package xyz.dicky99.rpc.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.*;

/**
 * @author Ysj
 * @version 1.0
 * @description TODO
 * @date 2021/5/7 18:33
 */
public class RpcServer {

    private final ExecutorService threadPool;
    private static final Logger logger = LoggerFactory.getLogger(RpcServer.class);

    public RpcServer(){
        int corePoolSize = 5;
        int maximumPoolSize = 50;
        long keepAliveTime = 60;
        BlockingQueue<Runnable> workingQueue = new ArrayBlockingQueue<>(100);
        ThreadFactory threadFactory = Executors.defaultThreadFactory();
        threadPool = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, TimeUnit.SECONDS, workingQueue, threadFactory);
    }

    public void register(Object service, int port){
        try(ServerSocket serverSocket = new ServerSocket(port)){
            System.out.println("服务器正在启动");
            logger.info("服务器正在启动...");
            Socket socket;
            while((socket = serverSocket.accept()) != null) {
                logger.info("检测到客户端连接！ip：" + socket.getInetAddress());
                threadPool.execute(new WorkerThread(socket, service));
            }
        }catch (IOException e){
            logger.error("连接时出现错误", e);
        }
    }
}
