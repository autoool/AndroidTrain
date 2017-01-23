package cn.fantasee.domain.interactor;

import cn.fantasee.domain.respository.DataRepositoryDomain;
import rx.Observable;

/**
 * Created by YY on 2016/9/13.
 */
public class UpdateOnlineOrderStatus extends RxBaseCase<BooleanResult> {
    String orderNo;
    String orderFinishStatus;

    private DataRepositoryDomain dataRepositoryDomain;

    public UpdateOnlineOrderStatus(DataRepositoryDomain dataRepositoryDomain) {
        this.dataRepositoryDomain = dataRepositoryDomain;
    }

    @Override
    public RxBaseCase initParams(String... paras) {
        orderNo = paras[0];
        orderFinishStatus = paras[1];
        return this;
    }

    @Override
    public Observable<BooleanResult> execute() {
        return this.dataRepositoryDomain.updateOnlineOrderStatus(orderNo, orderFinishStatus);
    }
}
