package cn.fantasee.domain.interactor;

import java.util.List;

import cn.fantasee.domain.respository.DataRepositoryDomain;
import rx.Observable;

/**
 * Created by YY on 2016/8/16.
 */
public class GetShiftStatistics extends RxBaseCase<List<DayReport>> {
    String queryDate;

    private DataRepositoryDomain dataRepositoryDomain;

    public GetShiftStatistics(DataRepositoryDomain dataRepositoryDomain) {
        this.dataRepositoryDomain = dataRepositoryDomain;
    }

    @Override
    public RxBaseCase initParams(String... paras) {
        queryDate = paras[0];
        return this;
    }

    @Override
    public Observable<List<DayReport>> execute() {
        return this.dataRepositoryDomain.getShiftStatistics(queryDate);
    }
}
