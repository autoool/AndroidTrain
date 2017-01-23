package cn.fantasee.domain.interactor;

import cn.fantasee.domain.respository.DataRepositoryDomain;
import rx.Observable;

/**
 * Created by zchao on 2016/8/5.
 */
public class UpdateSalesOrderStatus extends RxBaseCase<PayByResponse> {

    private DataRepositoryDomain dataRepositoryDomain;
    private String orderNo;
    private String status;

    public UpdateSalesOrderStatus(DataRepositoryDomain dataRepositoryDomain) {
        this.dataRepositoryDomain = dataRepositoryDomain;
    }

    @Override
    public RxBaseCase initParams(String... paras) {
        orderNo = paras[0];
        status = paras[1];
        return this;
    }

    @Override
    public Observable<PayByResponse> execute() {
        return this.dataRepositoryDomain.updateSalesOrderStatus(orderNo, status);
    }
}
