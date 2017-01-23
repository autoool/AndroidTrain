package cn.fantasee.domain.interactor;

import cn.fantasee.domain.respository.DataRepositoryDomain;

import rx.Observable;

/**
 * Created by zchao on 2016/5/12.
 */
public class InitProduct extends RxBaseCase<InitProductWrap> {

    private DataRepositoryDomain mDataRepository;

    public InitProduct(DataRepositoryDomain dataRepository) {
        this.mDataRepository = dataRepository;
    }

    @Override
    public RxBaseCase initParams(String... paras) {
        return this;
    }

    @Override
    public Observable<InitProductWrap> execute() {
        return this.mDataRepository.initProduct();
    }
}
