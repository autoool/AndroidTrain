package com.techidea.demand.pay;

import android.content.Context;

import com.techidea.demand.pay.channel.PayChannelEnum;
import com.techidea.demand.pay.channel.PayOrder;
import com.techidea.demand.pay.channel.PayTypeEnum;
import com.techidea.demand.pay.scanpay.AliFactory;
import com.techidea.demand.pay.scanpay.ScanPay;
import com.techidea.demand.pay.scanpay.ScanPayCallback;
import com.techidea.demand.pay.scanpay.ScanPayResult;
import com.techidea.demand.pay.scanpay.UnionFactory;
import com.techidea.demand.pay.scanpay.UnionQrPayFactory;
import com.techidea.demand.pay.scanpay.WxFactory;
import com.techidea.demand.pay.unionpay.UnionPay;

/**
 * Created by zchao on 2017/5/31.
 */

public class PayTask {

    private ScanPay scanPay;
    private UnionPay unionPay;
    private Context context;

    public static PayTask getInstance() {
        return PayTaskHolder.holder;
    }

    private static class PayTaskHolder {
        static PayTask holder = new PayTask();
    }

    /**
     *
     * @param context
     * @param env 支付环境 测试、正式
     */
    public void init(Context context, String env) {
        this.context = context.getApplicationContext();
    }

    //缺省渠道保持不变
    private PayChannelEnum payChannelEnumAli;
    private PayChannelEnum payChannelEnumWx;
    private PayChannelEnum payChannelEnumUnion;
    private PayChannelEnum payChannelEnumUnionQr;

    //上一次支付成功的channel
    private PayChannelEnum lastPayChannelEnumAli;
    private PayChannelEnum lastPayChannelEnumWx;
    private PayChannelEnum lastPayChannelEnumUnion;
    private PayChannelEnum lastPayChannelEnumUnionQr;

    private int tryChannel = 0;//0 1 2


    public void setAliChannel(PayChannelEnum payChannelEnum) throws Exception {
        payChannelEnumAli = payChannelEnum;
        lastPayChannelEnumAli = payChannelEnum;
        scanPay = AliFactory.getScanAliPay(payChannelEnum, context);
    }

    public void setWxChannel(PayChannelEnum payChannelEnum) throws Exception {
        payChannelEnumWx = payChannelEnum;
        lastPayChannelEnumWx = payChannelEnum;
        scanPay = WxFactory.getScanWxPay(payChannelEnum);
    }

    public void serUnionQrChannel(PayChannelEnum payChannelEnum) throws Exception {
        payChannelEnumUnionQr = payChannelEnum;
        lastPayChannelEnumUnionQr = payChannelEnum;
        unionPay = UnionFactory.getPay(payChannelEnum);
    }

    public void setUnionChannel(PayChannelEnum payChannelEnum) throws Exception {
        payChannelEnumUnion = payChannelEnum;
        lastPayChannelEnumUnion = payChannelEnum;
        scanPay = UnionQrPayFactory.getPay(payChannelEnum);
    }

    public void aliPay(final PayOrder payOrder, final ScanPayCallback scanPayCallback) {
        final PayChannelEnum payChannelEnum = getRemoteChannel(PayTypeEnum.ALIPAY);
        tryChannel = 0;
        try {
            if (payChannelEnum != null) {
                setAliChannel(payChannelEnum);
                tryChannel = 1;
            } else {
                setAliChannel(lastPayChannelEnumAli);
                tryChannel = 2;
            }
            scanPay.scanPay(payOrder, new ScanPayCallback() {
                @Override
                public void executeResult(ScanPayResult result) {
                    if (!result.isSuccess()) {
                        if (!result.getErrorCode().equals("7777")) {
                            // TODO: 2017/5/31 什么情况下渠道不可用
                            /*
                            * 复观服务接口：除了网络超时以外的其他错误
                            * 银联 ：没有安装收单应用
                            * 扫码付：没有安装收单应用
                            * 银联二维码：没有安装收单应用
                            *
                            * */
                            setDefaultChannelAli(payOrder, scanPayCallback);
                            return;
                        }
                        payFail(result);
                    }
                    lastPayChannelEnumAli = payChannelEnum;//设置上次可用的支付渠道
                    paySuccess(result);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void scanWx(final PayOrder payOrder, final ScanPayCallback scanPayCallback) {
        final PayChannelEnum payChannelEnum = getRemoteChannel(PayTypeEnum.WEIXIN);
        try {
            if (payChannelEnum != null) {
                setWxChannel(payChannelEnum);
            } else {
                setWxChannel(lastPayChannelEnumWx);
            }

        } catch (Exception e) {

        }
    }

    private void setDefaultChannelAli(PayOrder payOrder, ScanPayCallback scanPayCallback) {
        try {
            if (tryChannel == 1) {
                tryChannel = 2;
                setAliChannel(lastPayChannelEnumAli);
            } else {
                tryChannel = 3;
                setAliChannel(payChannelEnumAli);
            }

            scanPay.scanPay(payOrder, new ScanPayCallback() {
                @Override
                public void executeResult(ScanPayResult result) {
                    if (!result.isSuccess()) {
                        if (tryChannel == 2) {
                            payFail(result);
                            tryChannel = 0;
                            return;
                        } else {
                            // TODO: 2017/5/31 支付渠道
                        }

                    }
                    paySuccess(result);
                }
            });
        } catch (Exception e) {

        }

    }

    private void payFail(ScanPayResult scanPayResult) {

    }

    private void paySuccess(ScanPayResult scanPayResult) {

    }

    private PayChannelEnum getRemoteChannel(PayTypeEnum payTypeEnum) {
        return null;
    }


}
