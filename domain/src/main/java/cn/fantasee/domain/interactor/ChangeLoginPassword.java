package cn.fantasee.domain.interactor;

import cn.fantasee.domain.respository.DataRepositoryDomain;
import rx.Observable;

public class ChangeLoginPassword extends RxBaseCase<BooleanResult> {
    String oldPass;
    String newPass;

    private DataRepositoryDomain dataRepositoryDomain;

    public ChangeLoginPassword(DataRepositoryDomain dataRepositoryDomain) {
        this.dataRepositoryDomain = dataRepositoryDomain;
    }

    @Override
    public RxBaseCase initParams(String... paras) {
        oldPass = paras[0];
        newPass = paras[1];
        return this;
    }

    @Override
    public Observable<BooleanResult> execute() {
        return dataRepositoryDomain.changeLoginPassword(oldPass, newPass);
    }
}
