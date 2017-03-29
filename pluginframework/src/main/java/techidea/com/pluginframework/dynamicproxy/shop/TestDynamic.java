package techidea.com.pluginframework.dynamicproxy.shop;

import java.lang.reflect.Proxy;
import java.util.Arrays;

/**
 * Created by zchao on 2017/3/29.
 */

public class TestDynamic {

    public static void main(String[] args) {
        Shopping women = new ShoppingImpl();
        System.out.println(Arrays.toString(women.doShopping(100)));

        women = (Shopping) Proxy.newProxyInstance(Shopping.class.getClassLoader(),
                women.getClass().getInterfaces(), new ShoppingHandler(women));
        System.out.println(women.doShopping(100));

    }
}
