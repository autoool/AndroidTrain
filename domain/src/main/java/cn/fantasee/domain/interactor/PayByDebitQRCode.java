package cn.fantasee.domain.interactor;

import cn.fantasee.domain.respository.DataRepositoryDomain;
import rx.Observable;

/**
 * Created by zchao on 2016/8/5.
 */
public class PayByDebitQRCode extends RxBaseCase<PayByResponse> {

    private DataRepositoryDomain dataRepositoryDomain;
    private String orderNo;
    private String amount;
    private String tradeNo;
    private String payType;
    private String paymentId;
    private boolean finishOrder;

    public PayByDebitQRCode(DataRepositoryDomain dataRepositoryDomain) {
        this.dataRepositoryDomain = dataRepositoryDomain;
    }

    @Override
    public RxBaseCase initParams(String... paras) {
        this.orderNo = paras[0];
        this.amount = paras[1];
        this.tradeNo = paras[2];
        this.payType = paras[3];
        this.paymentId = paras[4];
        this.finishOrder = Boolean.valueOf(paras[5]);
        return this;
    }

    @Override
    public Observable<PayByResponse> execute() {
        return this.dataRepositoryDomain.payByDebitQRCode(orderNo, amount, tradeNo, payType, paymentId, finishOrder);
    }
}
