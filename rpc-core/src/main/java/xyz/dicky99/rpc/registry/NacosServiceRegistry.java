package xyz.dicky99.rpc.registry;

import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingFactory;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.dicky99.rpc.enumeraion.RpcError;
import xyz.dicky99.rpc.exception.RpcException;
import xyz.dicky99.rpc.loadbalancer.LoadBalancer;
import xyz.dicky99.rpc.loadbalancer.RoundRobinLoadBalancer;

import java.net.InetSocketAddress;
import java.util.List;

/**
 * @author Ysj
 * @version 1.0
 * @description TODO
 * @date 2021/5/10 11:06
 */

public class NacosServiceRegistry implements ServiceRegistry {

    private static final Logger logger = LoggerFactory.getLogger(NacosServiceRegistry.class);
    private LoadBalancer loadBalancer;
    private static final String SERVER_ADDR = "127.0.0.1:8848";
    private static final NamingService namingService;

    static {
        try {
            namingService = NamingFactory.createNamingService(SERVER_ADDR);
        } catch (NacosException e) {
            logger.error("连接到nacos时发生错误：", e);
            throw new RpcException(RpcError.FAILED_TO_CONNECT_TO_SERVICE_REGISTRY);
        }
    }

    public NacosServiceRegistry(LoadBalancer loadBalancer) {
        if(loadBalancer == null) this.loadBalancer = new RoundRobinLoadBalancer();
        else this.loadBalancer = loadBalancer;
    }

    public NacosServiceRegistry() { //无参默认实现轮转算法
        loadBalancer = new RoundRobinLoadBalancer();
    }

    @Override
    public void register(String serviceName, InetSocketAddress inetSocketAddress) {
        try {
            namingService.registerInstance(serviceName, inetSocketAddress.getHostName(), inetSocketAddress.getPort());
        } catch (NacosException e) {
            logger.error("注册服务到nacos时发生错误：", e);
            throw new RpcException(RpcError.REGISTER_SERVICE_FAILED);
        }
    }

    @Override
    public InetSocketAddress lookupService(String serviceName) {
        List<Instance> instances = null;
        try {
            instances = namingService.getAllInstances(serviceName);
            logger.info("当前服务器实例数{}",instances.size());
            Instance instance = loadBalancer.select(instances);   //负载均衡
            return new InetSocketAddress(instance.getIp(), instance.getPort());
        } catch (NacosException e) {
            logger.error("获取服务时发生错误", e);
            return null;
        }
    }
}
