package cn.fantasee.domain.interactor;

import cn.fantasee.domain.respository.DataRepositoryDomain;
import rx.Observable;

/**
 * Created by YY on 2016/8/15.
 */
public class GetShiftReport extends RxBaseCase<ShiftReport> {

    private String devicesUserId;

    private DataRepositoryDomain dataRepositoryDomain;

    public GetShiftReport(DataRepositoryDomain dataRepositoryDomain) {
        this.dataRepositoryDomain = dataRepositoryDomain;
    }

    @Override
    public RxBaseCase initParams(String... paras) {
        devicesUserId = paras[0];
        return this;
    }

    @Override
    public Observable<ShiftReport> execute() {
        return this.dataRepositoryDomain.getShiftReport(devicesUserId);
    }
}
