package com.techidea.androidtrain.abs;

/**
 * Created by zchao on 2017/4/19.
 */

public class WxPay extends AbsPayBase {

    public WxPay() {
    }

    public void printData() {
        System.out.println(TAG);
        if (!check(true))
            return;
        System.out.println(baseData);

    }
}
