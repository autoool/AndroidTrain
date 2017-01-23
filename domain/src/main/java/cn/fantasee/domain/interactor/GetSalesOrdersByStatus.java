package cn.fantasee.domain.interactor;

import java.util.List;

import cn.fantasee.domain.respository.DataRepositoryDomain;
import rx.Observable;

/**
 * Created by zchao on 2016/8/8.
 */
public class GetSalesOrdersByStatus extends RxBaseCase<List<ResponseOrder>> {

    private DataRepositoryDomain dataRepositoryDomain;
    private String pageIndex;
    private String pageSize;
    private String source;
    private String orderLinearStatus;
    private boolean isSelfPickUp;
    private boolean todayOnly;

    public GetSalesOrdersByStatus(DataRepositoryDomain dataRepositoryDomain) {
        this.dataRepositoryDomain = dataRepositoryDomain;
    }

    @Override
    public RxBaseCase initParams(String... paras) {
        this.pageIndex = paras[0];
        this.pageSize = paras[1];
        this.source = paras[2];
        this.orderLinearStatus = paras[3];
        this.isSelfPickUp = Boolean.valueOf(paras[4]);
        this.todayOnly = Boolean.valueOf(paras[5]);
        return this;
    }

    @Override
    public Observable<List<ResponseOrder>> execute() {
        return this.dataRepositoryDomain.getSalesOrdersByStatus(pageIndex, pageSize, source,
                orderLinearStatus, isSelfPickUp, todayOnly);
    }
}
