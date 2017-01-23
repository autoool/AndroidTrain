package cn.fantasee.domain.interactor;

import cn.fantasee.domain.respository.DataRepositoryDomain;
import rx.Observable;

/**
 * Created by YY on 2016/8/15.
 */
public class PushMessage extends RxBaseCase<BooleanResult> {

    private String storeId;

    private DataRepositoryDomain mDataRepository;

    public PushMessage(DataRepositoryDomain dataRepository) {
        this.mDataRepository = dataRepository;
    }

    @Override
    public RxBaseCase initParams(String... paras) {
        storeId = paras[0];
        return this;
    }

    @Override
    public Observable<BooleanResult> execute() {
        return this.mDataRepository.pushMessage(storeId);
    }
}
