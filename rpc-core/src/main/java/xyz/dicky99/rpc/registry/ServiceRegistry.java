package xyz.dicky99.rpc.registry;

import java.net.InetSocketAddress;

/**
 * @author Ysj
 * @version 1.0
 * @description 服务注册表通用接口
 * @date 2021/5/7 23:29
 */
public interface ServiceRegistry {
    /**
     * 将一个服务注册进注册表
     * @param serviceName 待注册的服务名称
     * @param inetSocketAddress 服务地址
     */
    void register(String serviceName, InetSocketAddress inetSocketAddress);

    /**
     * 根据服务名称获取服务实体
     * @param serviceName 服务名称
     * @return 服务地址
     */
    InetSocketAddress lookupService(String serviceName);

}
