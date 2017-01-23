package cn.fantasee.domain.interactor;

import cn.fantasee.domain.respository.DataRepositoryDomain;
import rx.Observable;


public class GetStorePaymentSetting extends RxBaseCase<PayTypeSetting> {

    private DataRepositoryDomain dataRepositoryDomain;

    public GetStorePaymentSetting(DataRepositoryDomain dataRepositoryDomain) {
        this.dataRepositoryDomain = dataRepositoryDomain;
    }

    @Override
    public RxBaseCase initParams(String... paras) {
        return this;
    }

    @Override
    public Observable<PayTypeSetting> execute() {
        return this.dataRepositoryDomain.getStorePaymentSetting();
    }
}
