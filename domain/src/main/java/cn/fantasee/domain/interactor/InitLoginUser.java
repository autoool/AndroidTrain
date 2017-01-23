package cn.fantasee.domain.interactor;

import java.util.List;

import cn.fantasee.domain.respository.DataRepositoryDomain;

import rx.Observable;

/**
 * Created by zchao on 2016/5/5.
 */
public class InitLoginUser extends RxBaseCase<List<UserInfo>> {

    private final DataRepositoryDomain mDataRepository;

    public InitLoginUser(DataRepositoryDomain dataRepository) {
        this.mDataRepository = dataRepository;
    }

    @Override
    public InitLoginUser initParams(String... paras) {
        return this;
    }

    @Override
    public Observable<List<UserInfo>> execute() {
        return this.mDataRepository.initUserInfo();
    }
}
