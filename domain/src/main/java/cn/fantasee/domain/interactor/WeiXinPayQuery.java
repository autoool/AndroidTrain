package cn.fantasee.domain.interactor;

import cn.fantasee.domain.respository.DataRepositoryDomain;
import rx.Observable;

/**
 * Created by zchao on 2016/8/4.
 */
public class WeiXinPayQuery extends RxBaseCase<PayResponse> {

    private DataRepositoryDomain dataRepositoryDomain;
    private String orderNo;

    public WeiXinPayQuery(DataRepositoryDomain dataRepositoryDomain) {
        this.dataRepositoryDomain = dataRepositoryDomain;
    }

    @Override
    public RxBaseCase initParams(String... paras) {
        this.orderNo = paras[0];
        return this;
    }

    @Override
    public Observable<PayResponse> execute() {
        return this.dataRepositoryDomain.weixinpayQuery(orderNo);
    }
}
