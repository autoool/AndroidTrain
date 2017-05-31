package com.techidea.demand.pay.scanpay;

import com.techidea.demand.pay.FgPay;
import com.techidea.demand.pay.FgProviderPay;
import com.techidea.demand.pay.LandiPay;
import com.techidea.demand.pay.LklPay;
import com.techidea.demand.pay.SwiftpassPay;
import com.techidea.demand.pay.WasaiScanPay;
import com.techidea.demand.pay.WizarHandPay;
import com.techidea.demand.pay.channel.PayChannelEnum;

/**
 * Created by zchao on 2017/5/31.
 */

public class WxFactory {

    public static ScanPay getScanWxPay(PayChannelEnum payChannelEnum) throws Exception {
        switch (payChannelEnum) {
            case WASAI:
                return new WasaiScanPay();
            case SWIFTPASS:
                return new SwiftpassPay();
            case WIZAR:
                return new WizarHandPay();
            case LKL:
                return new LklPay();
            case FGPAY:
                return new FgPay();
            case FGPAY_PROVIDER:
                return new FgProviderPay();
            case LANDI_PAY:
                return new LandiPay();
            default:
                throw new Exception("不支持的扫码支付渠道");
        }
    }
}
