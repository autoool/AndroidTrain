package cn.fantasee.domain.interactor;

import cn.fantasee.domain.respository.DataRepositoryDomain;
import rx.Observable;

/**
 * Created by zchao on 2016/8/4.
 */
public class PayByBankCard extends RxBaseCase<PayByResponse> {

    private DataRepositoryDomain dataRepositoryDomain;
    private String orderNo;
    private String tradeNo;
    private String amount;
    private String paymentId;
    private boolean finishOrder;

    public PayByBankCard(DataRepositoryDomain dataRepositoryDomain) {
        this.dataRepositoryDomain = dataRepositoryDomain;
    }

    @Override
    public RxBaseCase initParams(String... paras) {
        this.orderNo = paras[0];
        this.tradeNo = paras[1];
        this.amount = paras[2];
        this.paymentId = paras[3];
        this.finishOrder = Boolean.valueOf(paras[4]);
        return this;
    }

    @Override
    public Observable<PayByResponse> execute() {
        return this.dataRepositoryDomain.payByBankCard(orderNo, tradeNo, amount, paymentId, finishOrder);
    }
}
