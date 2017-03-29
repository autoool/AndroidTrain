package techidea.com.pluginframework.dynamicproxy.shop;

/**
 * Created by zchao on 2017/3/29.
 */

public class ProxyShopping implements Shopping {

    Shopping base;

    public ProxyShopping(Shopping base) {
        this.base = base;
    }

    @Override
    public Object[] doShopping(long money) {
        long readCost = (long) (money * 0.5);
        System.out.println(readCost);
        Object[] things = base.doShopping(readCost);

        if (things != null && things.length > 1) {
            things[0] = "碉堡了";
        }

        return things;
    }
}
