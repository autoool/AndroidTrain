package cn.fantasee.domain.interactor;

import java.util.List;

import cn.fantasee.domain.respository.DataRepositoryDomain;
import rx.Observable;

/**
 * Created by YY on 2016/8/11.
 */
public class GetRefundReason extends RxBaseCase<List<RefundReason>> {

    private DataRepositoryDomain dataRepositoryDomain;

    public GetRefundReason(DataRepositoryDomain dataRepositoryDomain) {
        this.dataRepositoryDomain = dataRepositoryDomain;
    }

    @Override
    public RxBaseCase initParams(String... paras) {
        return this;
    }

    @Override
    public Observable<List<RefundReason>> execute() {
        return this.dataRepositoryDomain.getRefundReason();
    }
}
