package cn.fantasee.domain.interactor;

import java.util.List;

import cn.fantasee.domain.respository.DataRepositoryDomain;
import rx.Observable;

/**
 * Created by YY on 2016/8/11.
 */
public class GetExpressCompany extends RxBaseCase<List<ExpressCompany>> {
    private DataRepositoryDomain dataRepositoryDomain;

    public GetExpressCompany(DataRepositoryDomain dataRepositoryDomain) {
        this.dataRepositoryDomain = dataRepositoryDomain;
    }

    @Override
    public RxBaseCase initParams(String... paras) {
        return this;
    }

    @Override
    public Observable<List<ExpressCompany>> execute() {
        return this.dataRepositoryDomain.getExpressCompany();
    }
}
