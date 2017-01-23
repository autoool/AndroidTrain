package cn.fantasee.domain.interactor;

import cn.fantasee.domain.respository.DataRepositoryDomain;
import rx.Observable;

/**
 * Created by YY on 2016/9/13.
 */
public class GetOrderDetailByPickupCode extends RxBaseCase<ResponseOrderDetail> {

    String pickupCode;

    private DataRepositoryDomain dataRepositoryDomain;

    public GetOrderDetailByPickupCode(DataRepositoryDomain dataRepositoryDomain) {
        this.dataRepositoryDomain = dataRepositoryDomain;
    }

    @Override
    public RxBaseCase initParams(String... paras) {
        pickupCode = paras[0];
        return this;
    }

    @Override
    public Observable<ResponseOrderDetail> execute() {
        return this.dataRepositoryDomain.getOrderDetailByPickupCode(pickupCode);
    }
}
