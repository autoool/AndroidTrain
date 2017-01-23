package cn.fantasee.domain.interactor;

import java.util.List;

import cn.fantasee.domain.respository.DataRepositoryDomain;
import rx.Observable;

public class GetOnlineOrderRefuseReason extends RxBaseCase<List<OnlineRefuseReason>> {

    private DataRepositoryDomain dataRepositoryDomain;

    public GetOnlineOrderRefuseReason(DataRepositoryDomain dataRepositoryDomain) {
        this.dataRepositoryDomain = dataRepositoryDomain;
    }

    @Override
    public RxBaseCase initParams(String... paras) {
        return this;
    }

    @Override
    public Observable<List<OnlineRefuseReason>> execute() {
        return this.dataRepositoryDomain.getOnlineOrderRefuseReason();
    }
}
