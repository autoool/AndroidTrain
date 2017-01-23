package cn.fantasee.domain.interactor;

import java.util.List;

import cn.fantasee.domain.respository.DataRepositoryDomain;
import rx.Observable;

/**
 * Created by YY on 2016/8/18.
 */
public class GetMemberTagLibrary extends RxBaseCase<List<MemberTag>> {

    DataRepositoryDomain dataRepositoryDomain;

    public GetMemberTagLibrary(DataRepositoryDomain dataRepositoryDomain) {
        this.dataRepositoryDomain = dataRepositoryDomain;
    }

    @Override
    public RxBaseCase initParams(String... paras) {
        return this;
    }

    @Override
    public Observable<List<MemberTag>> execute() {
        return this.dataRepositoryDomain.getMemberTagLibrary();
    }
}
