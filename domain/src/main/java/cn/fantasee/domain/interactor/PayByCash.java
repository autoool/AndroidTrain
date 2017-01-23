package cn.fantasee.domain.interactor;

import cn.fantasee.domain.respository.DataRepositoryDomain;
import rx.Observable;

/**
 * Created by zchao on 2016/8/4.
 */
public class PayByCash extends RxBaseCase<PayByResponse> {

    private String orderNo;
    private String amount;
    private String paymentId;
    private boolean finishOrder;
    private DataRepositoryDomain dataRepositoryDomain;

    public PayByCash(DataRepositoryDomain dataRepositoryDomain) {
        this.dataRepositoryDomain = dataRepositoryDomain;
    }

    @Override
    public RxBaseCase initParams(String... paras) {
        this.orderNo = paras[0];
        this.amount = paras[1];
        this.paymentId = paras[2];
        this.finishOrder = Boolean.valueOf(paras[3]);
        return this;
    }

    @Override
    public Observable<PayByResponse> execute() {
        return this.dataRepositoryDomain.payByCash(orderNo, amount, paymentId, finishOrder);
    }
}
