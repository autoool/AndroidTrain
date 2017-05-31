package com.techidea.demand.pay.scanpay;

import com.techidea.demand.pay.LklPay;
import com.techidea.demand.pay.channel.PayChannelEnum;

/**
 * Created by zchao on 2017/5/31.
 */

public class UnionQrPayFactory {

    public static ScanPay getPay(PayChannelEnum payChannelEnum) throws Exception {
        switch (payChannelEnum) {
            case LKL:
                return new LklPay();
            default:
                throw new Exception("不支持的扫码支付渠道");
        }
    }
}
