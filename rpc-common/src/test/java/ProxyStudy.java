import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author Ysj
 * @version 1.0
 * @description TODO
 * @date 2021/5/7 18:06
 */
public class ProxyStudy {
    public static void main(String[] args) {
        InvocationHandler handler = new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                System.out.println(method);
                if (method.getName().equals("morning")){
                    System.out.println("hello " + args[1]);
                }
                return null;
            }
        };
        Hello hello = (Hello) Proxy.newProxyInstance(Hello.class.getClassLoader(), new Class[] {Hello.class}, handler);
        hello.evening("hhh", "222");
    }
}

interface Hello{
    void morning(String name, String age);
    void evening(String name, String age);
}
