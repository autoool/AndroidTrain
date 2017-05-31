package com.techidea.demand.pay.fenqipay;

/**
 * Created by zchao on 2017/5/31.
 */

public interface FenqiPay {
    void pay();

    void payQuery();

    void payPickCodeConfirm();

    void payCancel();
}
