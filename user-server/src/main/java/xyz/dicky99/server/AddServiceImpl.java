package xyz.dicky99.server;

import xyz.dicky99.rpc.api.AddService;

/**
 * @author Ysj
 * @version 1.0
 * @description TODO
 * @date 2021/5/14 21:23
 */
public class AddServiceImpl implements AddService {
    @Override
    public int add(int a, int b) {
        return a + b;
    }
}
