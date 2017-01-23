package cn.fantasee.domain.interactor;

import cn.fantasee.domain.respository.DataRepositoryDomain;
import rx.Observable;

/**
 * Created by zchao on 2016/8/3.
 */
public class CreateSalesOrder extends RxBaseCase<OrderResult> {

    private String jsonOrder;
    private DataRepositoryDomain dataRepositoryDomain;

    public CreateSalesOrder(DataRepositoryDomain dataRepositoryDomain) {
        this.dataRepositoryDomain = dataRepositoryDomain;
    }

    @Override
    public RxBaseCase initParams(String... paras) {
        this.jsonOrder = paras[0];
        return this;
    }

    @Override
    public Observable<OrderResult> execute() {
        return dataRepositoryDomain.createSalesOrder(jsonOrder);
    }
}
