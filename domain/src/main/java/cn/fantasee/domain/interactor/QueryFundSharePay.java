package cn.fantasee.domain.interactor;

import cn.fantasee.domain.respository.DataRepositoryDomain;
import rx.Observable;

public class QueryFundSharePay extends RxBaseCase<FundShareQueryResult> {
    String orderNo;

    DataRepositoryDomain dataRepositoryDomain;

    public QueryFundSharePay(DataRepositoryDomain dataRepositoryDomain) {
        this.dataRepositoryDomain = dataRepositoryDomain;
    }


    @Override
    public RxBaseCase initParams(String... paras) {
        orderNo = paras[0];
        return this;
    }

    @Override
    public Observable<FundShareQueryResult> execute() {
        return this.dataRepositoryDomain.queryFundSharePay(orderNo);
    }
}
