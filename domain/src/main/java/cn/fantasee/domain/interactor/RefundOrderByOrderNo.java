package cn.fantasee.domain.interactor;

import java.util.List;

import cn.fantasee.domain.respository.DataRepositoryDomain;
import rx.Observable;

/**
 * Created by zchao on 2016/8/12.
 */
public class RefundOrderByOrderNo extends RxBaseCase<List<RefundResponse>> {

    private DataRepositoryDomain dataRepositoryDomain;
    private String orderNo;
    private String refundAmount;
    private String returnReasonType;
    private String refuseReasonType;
    private String confirmUserId;
    private boolean thirdRefund;

    public RefundOrderByOrderNo(DataRepositoryDomain dataRepositoryDomain) {
        this.dataRepositoryDomain = dataRepositoryDomain;
    }

    @Override
    public RxBaseCase initParams(String... paras) {
        this.orderNo = paras[0];
        this.refundAmount = paras[1];
        this.returnReasonType = paras[2];
        this.refuseReasonType = paras[3];
        this.confirmUserId = paras[4];
        this.thirdRefund = Boolean.valueOf(paras[5]);
        return this;
    }

    @Override
    public Observable<List<RefundResponse>> execute() {
        return this.dataRepositoryDomain.refundOrderByOrderNo(orderNo, refundAmount, returnReasonType,
                refuseReasonType, confirmUserId, thirdRefund);
    }
}
