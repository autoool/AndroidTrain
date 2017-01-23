package cn.fantasee.domain.interactor;

import cn.fantasee.domain.respository.DataRepositoryDomain;
import rx.Observable;

/**
 * Created by zchao on 2016/8/11.
 */
public class SendOnlineOrder extends RxBaseCase<BooleanResult> {

    private DataRepositoryDomain dataRepositoryDomain;
    private String orderNo;
    private String expressCompanyId;
    private String trackingNumber;

    public SendOnlineOrder(DataRepositoryDomain dataRepositoryDomain) {
        this.dataRepositoryDomain = dataRepositoryDomain;
    }

    @Override
    public RxBaseCase initParams(String... paras) {
        this.orderNo = paras[0];
        this.expressCompanyId = paras[1];
        this.trackingNumber = paras[2];
        return this;
    }

    @Override
    public Observable<BooleanResult> execute() {
        return dataRepositoryDomain.sendOnlineOrder(orderNo, expressCompanyId, trackingNumber);
    }
}
