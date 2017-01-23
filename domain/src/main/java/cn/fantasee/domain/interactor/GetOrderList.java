package cn.fantasee.domain.interactor;

import java.util.List;

import cn.fantasee.domain.respository.DataRepositoryDomain;
import rx.Observable;

/**
 * Created by YY on 2016/8/31.
 */
public class GetOrderList extends RxBaseCase<List<ResponseOrder>> {

    private String keyWord;
    private String channel;
    private String orderConfirmStatus;
    private String orderFinishStatus;
    private String pageIndex;
    private String pageSize;
    private boolean isSelfPickUp;
    private String beginTime;
    private String endTime;

    private DataRepositoryDomain dataRepositoryDomain;

    public GetOrderList(DataRepositoryDomain dataRepositoryDomain) {
        this.dataRepositoryDomain = dataRepositoryDomain;
    }

    @Override
    public RxBaseCase initParams(String... paras) {
        this.keyWord = paras[0];
        this.channel = paras[1];
        this.orderConfirmStatus = paras[2];
        this.orderFinishStatus = paras[3];
        this.pageIndex = paras[4];
        this.pageSize = paras[5];
        this.isSelfPickUp = Boolean.valueOf(paras[6]);
        this.beginTime = paras[7];
        this.endTime = paras[8];
        return this;
    }

    @Override
    public Observable<List<ResponseOrder>> execute() {
        return this.dataRepositoryDomain.getOrderList(keyWord, channel, orderConfirmStatus, orderFinishStatus,
                pageIndex, pageSize, isSelfPickUp, beginTime, endTime);
    }
}
