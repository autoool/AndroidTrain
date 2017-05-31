package com.techidea.demand.pay.fenqipay;

import com.techidea.demand.pay.LefenqiPay;
import com.techidea.demand.pay.channel.PayChannelEnum;

/**
 * Created by zchao on 2017/5/31.
 */

public class FenqiPayFactory {

    public static FenqiPay getPay(PayChannelEnum payChannelEnum) {
        switch (payChannelEnum) {
            case LEFENQI:
            default:
                return new LefenqiPay();
        }
    }
}
