package cn.fantasee.domain.interactor;

import java.util.List;

import cn.fantasee.domain.respository.DataRepositoryDomain;
import rx.Observable;


public class GetProductStatistics extends RxBaseCase<List<ProductStatisticItem>> {

    String queryDate;

    private DataRepositoryDomain dataRepositoryDomain;

    public GetProductStatistics(DataRepositoryDomain dataRepositoryDomain) {
        this.dataRepositoryDomain = dataRepositoryDomain;
    }

    @Override
    public RxBaseCase initParams(String... paras) {
        queryDate = paras[0];
        return this;
    }

    @Override
    public Observable<List<ProductStatisticItem>> execute() {
        return this.dataRepositoryDomain.getProductStatisticsReport(queryDate);
    }
}
