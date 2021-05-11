package xyz.dicky99.rpc.loadbalancer;

import com.alibaba.nacos.api.naming.pojo.Instance;

import java.util.List;

/**
 * @author Ysj
 * @version 1.0
 * @description 负载均衡的轮转算法
 * @date 2021/5/10 23:47
 */
public class RoundRobinLoadBalancer implements LoadBalancer{
    private int index = 1;
    @Override
    public Instance select(List<Instance> instances) {
        if (index >= instances.size()){
            index %= instances.size();
        }
        return instances.get(index++);
    }
}
