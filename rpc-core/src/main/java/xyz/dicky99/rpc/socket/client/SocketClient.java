package xyz.dicky99.rpc.socket.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.dicky99.rpc.RpcClient;
import xyz.dicky99.rpc.entity.RpcRequest;
import xyz.dicky99.rpc.entity.RpcResponse;
import xyz.dicky99.rpc.enumeraion.ResponseCode;
import xyz.dicky99.rpc.enumeraion.RpcError;
import xyz.dicky99.rpc.exception.RpcException;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * @author Ysj
 * @version 1.0
 * @description TODO
 * @date 2021/5/8 8:57
 */

public class SocketClient implements RpcClient {

    private static final Logger logger = LoggerFactory.getLogger(SocketClient.class);

    private final String host;
    private final int port;

    public SocketClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    @Override
    public Object sendRequest(RpcRequest rpcRequest) {
        try (Socket socket = new Socket(host, port)) {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            objectOutputStream.writeObject(rpcRequest);
            objectOutputStream.flush();
            RpcResponse rpcResponse = (RpcResponse) objectInputStream.readObject();
            if(rpcResponse == null) {
                logger.error("服务调用失败，service：{}", rpcRequest.getInterfaceName());
                throw new RpcException(RpcError.SERVICE_INVOCATION_FAILURE, " service:" + rpcRequest.getInterfaceName());
            }
            if(rpcResponse.getStatusCode() == null || rpcResponse.getStatusCode() != ResponseCode.SUCCESS.getCode()) {
                logger.error("调用服务失败, service: {}, response:{}", rpcRequest.getInterfaceName(), rpcResponse);
                throw new RpcException(RpcError.SERVICE_INVOCATION_FAILURE, " service:" + rpcRequest.getInterfaceName());
            }
            return rpcResponse.getData();
        } catch (IOException | ClassNotFoundException e) {
            logger.error("调用时有错误发生：", e);
            throw new RpcException("服务调用失败: ", e);
        }
    }

}

