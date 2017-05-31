package com.techidea.demand.pay;

import com.techidea.demand.pay.channel.PayOrder;
import com.techidea.demand.pay.scanpay.ScanPay;
import com.techidea.demand.pay.scanpay.ScanPayCallback;
import com.techidea.demand.pay.unionpay.UnionPay;

/**
 * Created by zchao on 2017/5/31.
 */

public class SwiftpassPay implements ScanPay,UnionPay {

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

    @Override
    public void unionLogin() {

    }

    @Override
    public void unionPay() {

    }

    @Override
    public void unionQuery() {

    }

    @Override
    public void unionReverse() {

    }

    @Override
    public void unionReverseQuery() {

    }

    @Override
    public void unionRefund() {

    }

    @Override
    public void unionRefundQuery() {

    }

    @Override
    public void settleUnion() {

    }

    @Override
    public void rePrintUnion() {

    }

    @Override
    public void rePrintUnionSettle() {

    }
}
