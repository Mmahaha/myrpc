package xyz.dicky99.rpc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.dicky99.rpc.entity.RpcRequest;
import xyz.dicky99.rpc.entity.RpcResponse;
import xyz.dicky99.rpc.enumeraion.ResponseCode;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author Ysj
 * @version 1.0
 * @description 进行过程调用的处理器
 * @date 2021/5/7 23:49
 */
public class RequestHandler {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    public Object handle(RpcRequest rpcRequest, Object service) {
        Object result = null;
        try {
            result = invokeTargetMethod(rpcRequest, service);
            logger.info("服务:{} 成功调用方法:{}", rpcRequest.getInterfaceName(), rpcRequest.getMethodName());
        } catch (IllegalAccessException | InvocationTargetException e) {
            logger.error("调用或发送时有错误发生：", e);
        } return result;
    }

    private Object invokeTargetMethod(RpcRequest rpcRequest, Object service) throws IllegalAccessException, InvocationTargetException {
        Method method;
        try {
            method = service.getClass().getMethod(rpcRequest.getMethodName(), rpcRequest.getParamTypes());
        } catch (NoSuchMethodException e) {
            return RpcResponse.fail(ResponseCode.METHOD_NOT_FOUND);
        }
        return method.invoke(service, rpcRequest.getParameters());
    }
}
