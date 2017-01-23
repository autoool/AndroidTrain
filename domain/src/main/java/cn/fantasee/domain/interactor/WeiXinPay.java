package cn.fantasee.domain.interactor;

import cn.fantasee.domain.respository.DataRepositoryDomain;
import rx.Observable;

/**
 * Created by zchao on 2016/8/4.
 */
public class WeiXinPay extends RxBaseCase<PayResponse> {

    private DataRepositoryDomain dataRepositoryDomain;
    private String orderNo;
    private String amount;
    private String code;
    private String paymentId;
    private boolean finishOrder;

    public WeiXinPay(DataRepositoryDomain dataRepositoryDomain) {
        this.dataRepositoryDomain = dataRepositoryDomain;
    }

    @Override
    public RxBaseCase initParams(String... paras) {
        this.orderNo = paras[0];
        this.amount = paras[1];
        this.code = paras[2];
        this.paymentId = paras[3];
        this.finishOrder = Boolean.valueOf(paras[4]);
        return this;
    }

    @Override
    public Observable<PayResponse> execute() {
        return this.dataRepositoryDomain.weixinPay(orderNo, amount, code, paymentId, finishOrder);
    }
}
