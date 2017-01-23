package cn.fantasee.domain.interactor;

import cn.fantasee.domain.respository.DataRepositoryDomain;

import rx.Observable;

/**
 * Created by zchao on 2016/5/13.
 */
public class Login extends RxBaseCase<LoginUser> {

    private String username;
    private String password;

    private final DataRepositoryDomain mDataRepository;

    public Login(DataRepositoryDomain dataRepository) {
        this.mDataRepository = dataRepository;
    }

    @Override
    public Login initParams(String... paras) {
        this.username = paras[0];
        this.password = paras[1];
        return this;
    }

    @Override
    public Observable<LoginUser> execute() {
        return this.mDataRepository.login(username, password);
    }
}
