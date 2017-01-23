package cn.fantasee.domain.interactor;

import cn.fantasee.domain.respository.DataRepositoryDomain;
import rx.Observable;

public class ConfirmOnlineOrder extends RxBaseCase<BooleanResult> {

    String orderNo;

    private DataRepositoryDomain dataRepositoryDomain;

    public ConfirmOnlineOrder(DataRepositoryDomain dataRepositoryDomain) {
        this.dataRepositoryDomain = dataRepositoryDomain;
    }

    @Override
    public RxBaseCase initParams(String... paras) {
        orderNo = paras[0];
        return this;
    }

    @Override
    public Observable<BooleanResult> execute() {
        return this.dataRepositoryDomain.confirmOnlineOrder(orderNo);
    }
}
