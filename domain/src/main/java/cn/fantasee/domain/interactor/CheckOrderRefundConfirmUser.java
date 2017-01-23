package cn.fantasee.domain.interactor;

import cn.fantasee.domain.respository.DataRepositoryDomain;
import rx.Observable;

/**
 * Created by YY on 2016/8/2.
 */
public class CheckOrderRefundConfirmUser extends RxBaseCase<BooleanResult> {

    String userName;
    String password;

    private DataRepositoryDomain mDataRepository;

    public CheckOrderRefundConfirmUser(DataRepositoryDomain dataRepository) {
        this.mDataRepository = dataRepository;
    }

    @Override
    public RxBaseCase initParams(String... paras) {
        userName = paras[0];
        password = paras[1];
        return this;
    }

    @Override
    public Observable<BooleanResult> execute() {
        return this.mDataRepository.checkOrderRefundConfirmUser(userName, password);
    }
}
