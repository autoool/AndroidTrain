package com.techidea.demand.pay.scanpay;

import com.techidea.demand.pay.channel.PayOrder;

/**
 * Created by zchao on 2017/5/31.
 */

public interface ScanPay {

    void scanActivate();//激活

    void scanLogin();//签到

    void scanPay(PayOrder payOrder, ScanPayCallback scanPayCallback);

    void scanReverse(PayOrder payOrder, ScanPayCallback scanPayCallback);

    void scanQuery(PayOrder payOrder, ScanPayCallback scanPayCallback);

    void scanRefund(PayOrder payOrder, ScanPayCallback scanPayCallback);

    void scanRefundQuery(PayOrder payOrder, ScanPayCallback scanPayCallback);

    void rePrintScan();

    void rePrintScanSettle();
}
