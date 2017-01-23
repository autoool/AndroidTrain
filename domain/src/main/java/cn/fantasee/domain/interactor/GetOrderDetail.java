package cn.fantasee.domain.interactor;

import cn.fantasee.domain.respository.DataRepositoryDomain;
import rx.Observable;

/**
 * Created by zchao on 2016/8/9.
 */
public class GetOrderDetail extends RxBaseCase<ResponseOrderDetail> {

    private DataRepositoryDomain dataRepositoryDomain;
    private String orderNo;

    public GetOrderDetail(DataRepositoryDomain dataRepositoryDomain) {
        this.dataRepositoryDomain = dataRepositoryDomain;
    }

    @Override
    public RxBaseCase initParams(String... paras) {
        orderNo = paras[0];
        return this;
    }

    @Override
    public Observable<ResponseOrderDetail> execute() {
        return this.dataRepositoryDomain.getOrderDetail(orderNo);
    }
}
