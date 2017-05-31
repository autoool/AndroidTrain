package com.techidea.demand.pay;

import com.techidea.demand.pay.channel.PayOrder;
import com.techidea.demand.pay.scanpay.ScanPay;
import com.techidea.demand.pay.scanpay.ScanPayCallback;

/**
 * Created by zchao on 2017/5/31.
 */

public class WasaiScanPay implements ScanPay {

    @Override
    public void scanActivate() {

    }

    @Override
    public void scanLogin() {

    }

    @Override
    public void scanPay(PayOrder payOrder, ScanPayCallback scanPayCallback) {

    }

    @Override
    public void scanReverse(PayOrder payOrder, ScanPayCallback scanPayCallback) {

    }

    @Override
    public void scanQuery(PayOrder payOrder, ScanPayCallback scanPayCallback) {

    }

    @Override
    public void scanRefund(PayOrder payOrder, ScanPayCallback scanPayCallback) {

    }

    @Override
    public void scanRefundQuery(PayOrder payOrder, ScanPayCallback scanPayCallback) {

    }

    @Override
    public void rePrintScan() {

    }

    @Override
    public void rePrintScanSettle() {

    }
}
