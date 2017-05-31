package com.techidea.demand.pay.scanpay;

import com.sun.jna.Union;
import com.techidea.demand.pay.FgPay;
import com.techidea.demand.pay.FgProviderPay;
import com.techidea.demand.pay.LandiPay;
import com.techidea.demand.pay.LklPay;
import com.techidea.demand.pay.SwiftpassPay;
import com.techidea.demand.pay.WasaiScanPay;
import com.techidea.demand.pay.WizarHandPay;
import com.techidea.demand.pay.channel.PayChannelEnum;
import com.techidea.demand.pay.unionpay.UnionPay;

/**
 * Created by zchao on 2017/5/31.
 */

public class UnionFactory {

    public static UnionPay getPay(PayChannelEnum payChannelEnum) throws Exception {
        switch (payChannelEnum) {
            case SWIFTPASS:
                return new SwiftpassPay();
            case WIZAR:
                return new WizarHandPay();
            case LKL:
                return new LklPay();
            case LANDI_PAY:
                return new LandiPay();
            default:
                throw new Exception("不支持的银联支付渠道");
        }
    }
}
