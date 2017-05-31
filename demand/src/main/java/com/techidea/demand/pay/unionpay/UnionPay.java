package com.techidea.demand.pay.unionpay;

/**
 * Created by zchao on 2017/5/31.
 */

public interface UnionPay {

    void unionLogin();//签到

    void unionPay();//消费

    void unionQuery();//消费查询

    void unionReverse();//撤销（冲正）

    void unionReverseQuery();//撤销查询

    void unionRefund();//退货

    void unionRefundQuery();//退货查询

    void settleUnion();//结算

    void rePrintUnion();//重打印订单

    void rePrintUnionSettle();//重打印结算单

}
