package techidea.com.pluginframework.dynamicproxy.shop;

/**
 * Created by zchao on 2017/3/29.
 */

public class TestStatic {

    public static void main(String[] args) {
        Shopping women = new ShoppingImpl();
        System.out.println(women.doShopping(100));
        women = new ProxyShopping(women);
        System.out.println(women.doShopping(100));
    }

}
