package techidea.com.pluginframework.dynamicproxy.shop;

/**
 * Created by zchao on 2017/3/29.
 */

public class ShoppingImpl implements Shopping {

    @Override
    public Object[] doShopping(long money) {
        System.out.println("买买");
        System.out.println(String.format("%s元", money));
        return new Object[]{"手机", "衣服", "鞋子"};
    }
}
