package com.techidea.androidtrain;

import com.techidea.androidtrain.abs.WxPay;

import org.junit.Test;

/**
 * Created by zchao on 2017/4/19.
 */

public class WxPayTest {

    @Test
    public void testPrint() {
        WxPay wxPay = new WxPay();
        wxPay.printData();
    }
}
