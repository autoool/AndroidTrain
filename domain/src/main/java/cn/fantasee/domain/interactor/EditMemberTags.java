package cn.fantasee.domain.interactor;

import cn.fantasee.domain.respository.DataRepositoryDomain;
import rx.Observable;

public class EditMemberTags extends RxBaseCase<BooleanResult> {
    String memberId;
    String tags;

    DataRepositoryDomain dataRepositoryDomain;

    public EditMemberTags(DataRepositoryDomain dataRepositoryDomain) {
        this.dataRepositoryDomain = dataRepositoryDomain;
    }

    @Override
    public EditMemberTags initParams(String... paras) {
        this.memberId = paras[0];
        this.tags = paras[1];
        return this;
    }

    @Override
    public Observable<BooleanResult> execute() {
        return this.dataRepositoryDomain.editMemberTags(memberId, tags);
    }
}
