package cn.fantasee.domain.interactor;

import cn.fantasee.domain.respository.DataRepositoryDomain;
import rx.Observable;

public class GetFundShareQrcode extends RxBaseCase<QrCode> {

    String orderNo;

    DataRepositoryDomain dataRepositoryDomain;

    public GetFundShareQrcode(DataRepositoryDomain dataRepositoryDomain) {
        this.dataRepositoryDomain = dataRepositoryDomain;
    }


    @Override
    public RxBaseCase initParams(String... paras) {
        orderNo = paras[0];
        return this;
    }

    @Override
    public Observable<QrCode> execute() {
        return this.dataRepositoryDomain.getFundShareQrcode(orderNo);
    }
}
