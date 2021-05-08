package xyz.dicky99.rpc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.dicky99.rpc.entity.RpcRequest;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * @author Ysj
 * @version 1.0
 * @description 客户端类通用接口
 * @date 2021/5/7 18:29
 */

public interface RpcClient {

    Object sendRequest(RpcRequest rpcRequest);

}