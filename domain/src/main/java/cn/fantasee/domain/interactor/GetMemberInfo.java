package cn.fantasee.domain.interactor;

import cn.fantasee.domain.respository.DataRepositoryDomain;
import rx.Observable;

/**
 * Created by zchao on 2016/6/13.
 */
public class GetMemberInfo extends RxBaseCase<Member> {

    private String queryCode;
    private boolean isQrCode;
    private final DataRepositoryDomain mDataRepositoryDomain;

    public GetMemberInfo(DataRepositoryDomain dataRepositoryDomain) {
        this.mDataRepositoryDomain = dataRepositoryDomain;
    }

    @Override
    public RxBaseCase initParams(String... paras) {
        queryCode = paras[0];
        isQrCode = Boolean.valueOf(paras[1]);
        return this;
    }

    @Override
    public Observable<Member> execute() {
        return mDataRepositoryDomain.getMemberInfo(queryCode,isQrCode);
    }
}
