package cn.fantasee.data.repository.datasource.remote;

import java.util.List;

import cn.fantasee.data.cache.DataCache;
import cn.fantasee.data.net.HttpMethods;
import cn.fantasee.data.repository.datasource.DataStore;
import rx.Observable;
import rx.functions.Action1;

/**
 * Created by zchao on 2016/5/19.
 * 服务端数据
 */
public class RemoteDataSource implements DataStore {

    private final DataCache mDataCache;

    private final Action1<InitProductWrap> saveToPtoduct = new Action1<InitProductWrap>() {
        @Override
        public void call(InitProductWrap productWrap) {
            mDataCache.putProducts(productWrap);
        }
    };

    private final Action1<List<UserInfo>> saveToUserInfos = new Action1<List<UserInfo>>() {
        @Override
        public void call(List<UserInfo> userInfoList) {
            mDataCache.putLoginUsers(userInfoList);
        }
    };

    private final Action1<LoginUser> saveToLoginUser = new Action1<LoginUser>() {
        @Override
        public void call(LoginUser loginUser) {
            if (loginUser != null)
                HttpMethods.getInstance().setToken(loginUser.getToken());
            mDataCache.putLoginUser(loginUser);
        }
    };

    public RemoteDataSource(DataCache dataCache) {
        this.mDataCache = dataCache;
    }

    @Override
    public Observable<InitProductWrap> initProduct() {
        return HttpMethods.getInstance().initProduct().doOnNext(saveToPtoduct);
    }

    @Override
    public Observable<List<UserInfo>> initUserInfo() {
        return HttpMethods.getInstance().initLoginUsers().doOnNext(saveToUserInfos);
    }

    @Override
    public Observable<PayTypeSetting> getStorePaymentSetting() {
        return HttpMethods.getInstance().getStorePaymentSetting();
    }

    @Override
    public Observable<List<ExpressCompany>> getExpressCompany() {
        return HttpMethods.getInstance().getExpressCompany();
    }

    @Override
    public Observable<List<RefundReason>> getRefundReason() {
        return HttpMethods.getInstance().getRefundReason();
    }

    @Override
    public Observable<List<OnlineRefuseReason>> getOnlineOrderRefuseReason() {
        return HttpMethods.getInstance().getOnlineOrderRefuseReason();
    }

    @Override
    public Observable<LoginUser> login(String userName, String password) {
        return HttpMethods.getInstance().login(userName, password).doOnNext(saveToLoginUser);
    }

    @Override
    public Observable<BooleanResult> initPushDevice(String devicePushId) {
        return HttpMethods.getInstance().initPushDevice(devicePushId);
    }

    @Override
    public Observable<BooleanResult> pushMessage(String storeId) {
        return HttpMethods.getInstance().pushMessage(storeId);
    }

    @Override
    public Observable<Member> getMemberInfo(String queryCode, boolean isQrCode) {
        return HttpMethods.getInstance().getMemberInfo(queryCode, isQrCode);
    }

    @Override
    public Observable<OrderResult> createSalesOrder(String jsonOrder) {
        return HttpMethods.getInstance().createSalesOrder(jsonOrder);
    }

    @Override
    public Observable<BooleanResult> checkOrderRefundConfirmUser(String userName, String password) {
        return HttpMethods.getInstance().checkOrderRefundConfirmUser(userName, password);
    }

    @Override
    public Observable<PayByResponse> payByCash(String orderNo, String amount, String paymentId,
                                               boolean finishOrder) {
        return HttpMethods.getInstance().payByCash(orderNo, amount, paymentId, finishOrder);
    }

    @Override
    public Observable<PayResponse> aliPay(String orderNo, String amount, String code,
                                          String paymentId, boolean finishOrder) {
        return HttpMethods.getInstance().payByAliPay(orderNo, amount, code, paymentId, finishOrder);
    }

    @Override
    public Observable<PayResponse> alipayQuery(String orderNo) {
        return HttpMethods.getInstance().alipayQuery(orderNo);
    }

    @Override
    public Observable<PayResponse> weixinPay(String orderNo, String amount, String code,
                                             String paymentId, boolean finishOrder) {
        return HttpMethods.getInstance().payByWeiXinPay(orderNo, amount, code, paymentId, finishOrder);
    }

    @Override
    public Observable<PayResponse> weixinpayQuery(String orderNo) {
        return HttpMethods.getInstance().weixinpayQuery(orderNo);
    }

    @Override
    public Observable<PayByResponse> payByBankCard(String orderNo, String tradeNo, String amount,
                                                   String paymentId, boolean finishOrder) {
        return HttpMethods.getInstance().payByBankCard(orderNo, tradeNo, amount, paymentId, finishOrder);
    }

    @Override
    public Observable<PayByResponse> updateSalesOrderStatus(String orderNo, String status) {
        return HttpMethods.getInstance().updateSalesOrderStatus(orderNo, status);
    }

    @Override
    public Observable<PayByResponse> payByDebitQRCode(String orderNo, String amount, String tradeNo, String payType, String paymentId, boolean finishOrder) {
        return HttpMethods.getInstance().payByDebitQRCode(orderNo, amount, tradeNo, payType, paymentId, finishOrder);
    }

    @Override
    public Observable<List<ResponseOrder>> getSalesOrdersByStatus(String pageIndex, String pageSize,
                                                                  String source, String orderLinearStatus,
                                                                  boolean isSelfPickUp, boolean todayOnly) {
        return HttpMethods.getInstance().getSalesOrdersByStatus(pageIndex, pageSize, source,
                orderLinearStatus, isSelfPickUp, todayOnly);
    }

    @Override
    public Observable<ResponseOrderDetail> getOrderDetail(String orderNo) {
        return HttpMethods.getInstance().getOrderDetail(orderNo);
    }

    @Override
    public Observable<BooleanResult> changeLoginPassword(String oldPass, String newPass) {
        return HttpMethods.getInstance().changeLoginPassword(oldPass, newPass);
    }


    @Override
    public Observable<BooleanResult> sendOnlineOrder(String orderNo, String expressCompanyId, String trackingNumber) {
        return HttpMethods.getInstance().sendOnlineOrder(orderNo, expressCompanyId, trackingNumber);
    }

    @Override
    public Observable<ReturnResponse> returnOrderByOrderNo(String orderNo, String refundAmount, String returnReasonType,
                                                           String refuseReasonType, String confirmUserId) {
        return HttpMethods.getInstance().returnOrderByOrderNo(orderNo, refundAmount, returnReasonType,
                refuseReasonType, confirmUserId);
    }

    @Override
    public Observable<List<RefundResponse>> refundOrderByOrderNo(String orderNo, String refundAmount, String returnReasonType,
                                                                 String refuseReasonType, String confirmUserId, boolean thirdRefund) {
        return HttpMethods.getInstance().refundOrderByOrderNo(orderNo, refundAmount, returnReasonType, refuseReasonType, confirmUserId, thirdRefund);
    }

    @Override
    public Observable<ShiftReport> getShiftReport(String devicesUserId) {
        return HttpMethods.getInstance().getShiftReport(devicesUserId);
    }

    @Override
    public Observable<List<DayReport>> getShiftStatistics(String queryDate) {
        return HttpMethods.getInstance().getShiftStatistics(queryDate);
    }

    @Override
    public Observable<List<ProductStatisticItem>> getProductStatisticsReport(String queryDate) {
        return HttpMethods.getInstance().getProductStatisticsReport(queryDate);
    }

    @Override
    public Observable<List<MemberTag>> getMemberTagLibrary() {
        return HttpMethods.getInstance().getMemberTagLibrary();
    }

    @Override
    public Observable<BooleanResult> editMemberTags(String memberId, String tags) {
        return HttpMethods.getInstance().editMemberTags(memberId, tags);
    }

    @Override
    public Observable<QrCode> getFundShareQrcode(String orderNo) {
        return HttpMethods.getInstance().getFundShareQrcode(orderNo);
    }

    @Override
    public Observable<FundShareQueryResult> queryFundSharePay(String orderNo) {
        return HttpMethods.getInstance().queryFundSharePay(orderNo);
    }

    @Override
    public Observable<BooleanResult> confirmOnlineOrder(String orderNo) {
        return HttpMethods.getInstance().confirmOnlineOrder(orderNo);
    }

    @Override
    public Observable<List<ResponseOrder>> getOrderList(String keyWord, String channel,
                                                        String orderConfirmStatus, String orderFinishStatus,
                                                        String pageIndex, String pageSize, boolean isSelfPickUp,
                                                        String beginTime, String endTime) {
        return HttpMethods.getInstance().getOrderList(keyWord, channel, orderConfirmStatus, orderFinishStatus,
                pageIndex, pageSize, isSelfPickUp, beginTime, endTime);
    }

    @Override
    public Observable<ResponseOrderDetail> getOrderDetailByPickupCode(String pickupCode) {
        return HttpMethods.getInstance().getOrderDetailByPickupCode(pickupCode);
    }

    @Override
    public Observable<BooleanResult> updateOnlineOrderStatus(String orderNo, String orderFinishStatus) {
        return HttpMethods.getInstance().updateOnlineOrderStatus(orderNo, orderFinishStatus);
    }
}
