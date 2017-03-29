package techidea.com.pluginframework.dynamicproxy.shop;

import android.util.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created by zchao on 2017/3/29.
 */

public class ShoppingHandler implements InvocationHandler {

    Object base;

    public ShoppingHandler(Object base) {
        this.base = base;
    }

    @Override
    public Object invoke(Object o, Method method, Object[] args) throws Throwable {
        if ("doShopping".equals(method.getName())) {
            Long money = (Long) args[0];
            long readCost = (long) (money * 0.5);
            System.out.println(String.format("real %s元", readCost));

            Object[] things = (Object[]) method.invoke(base, readCost);
            if (things != null && things.length > 1) {
                things[0] = "枪";
            }
            return things;
        }
        if ("doSomething".equals(method.getName())){
            return null;
        }
        return null;
    }
}
