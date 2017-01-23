package cn.fantasee.data.repository;

import android.content.Context;

import java.util.List;

import cn.fantasee.data.cache.DataCache;
import cn.fantasee.data.cache.DataCacheImpl;
import cn.fantasee.data.repository.datasource.local.LocalDataSource;
import cn.fantasee.data.repository.datasource.remote.RemoteDataSource;
import cn.fantasee.domain.respository.DataRepositoryDomain;
import rx.Observable;

/**
 * Created by zchao on 2016/5/19.
 */
public class DataRepository implements DataRepositoryDomain {

    private static DataRepository INSTANCE = null;
    private final RemoteDataSource mRemoteDataSource;
    private final LocalDataSource mLocalDataSource;
    private final DataCache mDataCache;


    public DataRepository(Context context) {
        this.mDataCache = new DataCacheImpl(context);
        this.mLocalDataSource = new LocalDataSource(this.mDataCache);
        this.mRemoteDataSource = new RemoteDataSource(this.mDataCache);
    }

    public static DataRepository getInstance(Context context) {
        if (INSTANCE == null)
            INSTANCE = new DataRepository(context);
        return INSTANCE;
    }

    @Override
    public Observable<InitProductWrap> initProduct() {
//        if (this.mDataCache.isCached(DataCacheImpl.FILE_PRODUCTS)) {
//            if (this.mDataCache.isExpired(DataCacheImpl.FILE_PRODUCTS)) {
//                return this.mRemoteDataSource.initProduct();
//            } else {
//                return this.mLocalDataSource.initProduct();
//            }
//        } else {
        return this.mRemoteDataSource.initProduct();
//        }
    }

    @Override
    public Observable<List<UserInfo>> initUserInfo() {
//        if (this.mDataCache.isCached(DataCacheImpl.FILE_USREINFOS)) {
//            if (this.mDataCache.isExpired(DataCacheImpl.FILE_USREINFOS)) {
//                return this.mRemoteDataSource.initUserInfo();
//            } else {
//                return this.mLocalDataSource.initUserInfo();
//            }
//        } else {
        return this.mRemoteDataSource.initUserInfo();
//        }
    }

    @Override
    public Observable<PayTypeSetting> getStorePaymentSetting() {
        return this.mRemoteDataSource.getStorePaymentSetting();
    }

    @Override
    public Observable<List<RefundReason>> getRefundReason() {
        return this.mRemoteDataSource.getRefundReason();
    }

    @Override
    public Observable<List<OnlineRefuseReason>> getOnlineOrderRefuseReason() {
        return this.mRemoteDataSource.getOnlineOrderRefuseReason();
    }

    @Override
    public Observable<List<ExpressCompany>> getExpressCompany() {
        return this.mRemoteDataSource.getExpressCompany();
    }

    @Override
    public Observable<LoginUser> login(String username, String password) {
        return this.mRemoteDataSource.login(username, password);
    }

    @Override
    public Observable<BooleanResult> initPushDevice(String devicePushId) {
        return this.mRemoteDataSource.initPushDevice(devicePushId);
    }

    @Override
    public Observable<BooleanResult> pushMessage(String storeId) {
        return this.mRemoteDataSource.pushMessage(storeId);
    }

    @Override
    public Observable<BooleanResult> checkOrderRefundConfirmUser(String userName, String password) {
        return this.mRemoteDataSource.checkOrderRefundConfirmUser(userName, password);
    }

    @Override
    public Observable<Member> getMemberInfo(String queryCode, boolean isQrCode) {
        return this.mRemoteDataSource.getMemberInfo(queryCode, isQrCode);
    }

    @Override
    public Observable<OrderResult> createSalesOrder(String jsonOrder) {
        return this.mRemoteDataSource.createSalesOrder(jsonOrder);
    }

    @Override
    public Observable<PayByResponse> payByCash(String orderNo, String amount, String paymentId,
                                               boolean finishOrder) {
        return this.mRemoteDataSource.payByCash(orderNo, amount, paymentId, finishOrder);
    }

    @Override
    public Observable<PayResponse> aliPay(String orderNo, String amount, String code,
                                          String paymentId, boolean finishOrder) {
        return this.mRemoteDataSource.aliPay(orderNo, amount, code, paymentId, finishOrder);
    }

    @Override
    public Observable<PayResponse> alipayQuery(String orderNo) {
        return this.mRemoteDataSource.alipayQuery(orderNo);
    }

    @Override
    public Observable<PayResponse> weixinPay(String orderNo, String amount, String code,
                                             String paymentId, boolean finishOrder) {
        return this.mRemoteDataSource.weixinPay(orderNo, amount, code, paymentId, finishOrder);
    }

    @Override
    public Observable<PayResponse> weixinpayQuery(String orderNo) {
        return this.mRemoteDataSource.weixinpayQuery(orderNo);
    }

    @Override
    public Observable<PayByResponse> payByBankCard(String orderNo, String tradeNo, String amount,
                                                   String paymentId, boolean finishOrder) {
        return this.mRemoteDataSource.payByBankCard(orderNo, tradeNo, amount, paymentId, finishOrder);
    }

    @Override
    public Observable<PayByResponse> updateSalesOrderStatus(String orderNo, String status) {
        return this.mRemoteDataSource.updateSalesOrderStatus(orderNo, status);
    }

    @Override
    public Observable<PayByResponse> payByDebitQRCode(String orderNo, String amount, String tradeNo,
                                                      String payType, String paymentId, boolean finishOrder) {
        return this.mRemoteDataSource.payByDebitQRCode(orderNo, amount, tradeNo, payType, paymentId, finishOrder);
    }

    @Override
    public Observable<List<ResponseOrder>> getSalesOrdersByStatus(String pageIndex, String pageSize,
                                                                  String source, String orderLinearStatus,
                                                                  boolean isSelfPickUp, boolean todayOnly) {
        return this.mRemoteDataSource.getSalesOrdersByStatus(pageIndex, pageSize, source,
                orderLinearStatus, isSelfPickUp, todayOnly);
    }

    @Override
    public Observable<ResponseOrderDetail> getOrderDetail(String orderNo) {
        return this.mRemoteDataSource.getOrderDetail(orderNo);
    }

    @Override
    public Observable<BooleanResult> changeLoginPassword(String oldPass, String newPass) {
        return this.mRemoteDataSource.changeLoginPassword(oldPass, newPass);
    }

    @Override
    public Observable<BooleanResult> sendOnlineOrder(String orderNo, String expressCompanyId, String trackingNumber) {
        return this.mRemoteDataSource.sendOnlineOrder(orderNo, expressCompanyId, trackingNumber);
    }

    @Override
    public Observable<ReturnResponse> returnOrderByOrderNo(String orderNo, String refundAmount, String returnReasonType,
                                                           String refuseReasonType, String confirmUserId) {
        return this.mRemoteDataSource.returnOrderByOrderNo(orderNo, refundAmount, returnReasonType,
                refuseReasonType, confirmUserId);
    }

    @Override
    public Observable<List<RefundResponse>> refundOrderByOrderNo(String orderNo, String refundAmount, String returnReasonType,
                                                                 String refuseReasonType, String confirmUserId, boolean thirdRefund) {
        return this.mRemoteDataSource.refundOrderByOrderNo(orderNo, refundAmount, returnReasonType, refuseReasonType, confirmUserId, thirdRefund);
    }

    @Override
    public Observable<ShiftReport> getShiftReport(String devicesUserId) {
        return this.mRemoteDataSource.getShiftReport(devicesUserId);
    }

    @Override
    public Observable<List<DayReport>> getShiftStatistics(String queryDate) {
        return this.mRemoteDataSource.getShiftStatistics(queryDate);
    }

    @Override
    public Observable<List<ProductStatisticItem>> getProductStatisticsReport(String queryDate) {
        return this.mRemoteDataSource.getProductStatisticsReport(queryDate);
    }

    @Override
    public Observable<List<MemberTag>> getMemberTagLibrary() {
        return this.mRemoteDataSource.getMemberTagLibrary();
    }

    @Override
    public Observable<BooleanResult> editMemberTags(String memberId, String tags) {
        return this.mRemoteDataSource.editMemberTags(memberId, tags);
    }

    @Override
    public Observable<QrCode> getFundShareQrcode(String orderNo) {
        return this.mRemoteDataSource.getFundShareQrcode(orderNo);
    }

    @Override
    public Observable<FundShareQueryResult> queryFundSharePay(String orderNo) {
        return this.mRemoteDataSource.queryFundSharePay(orderNo);
    }

    @Override
    public Observable<BooleanResult> confirmOnlineOrder(String orderNo) {
        return this.mRemoteDataSource.confirmOnlineOrder(orderNo);
    }

    @Override
    public Observable<List<ResponseOrder>> getOrderList(String keyWord, String channel,
                                                        String orderConfirmStatus, String orderFinishStatus,
                                                        String pageIndex, String pageSize, boolean isSelfPickUp,
                                                        String beginTime, String endTime) {
        return this.mRemoteDataSource.getOrderList(keyWord, channel, orderConfirmStatus, orderFinishStatus,
                pageIndex, pageSize, isSelfPickUp, beginTime, endTime);
    }

    @Override
    public Observable<ResponseOrderDetail> getOrderDetailByPickupCode(String pickupCode) {
        return this.mRemoteDataSource.getOrderDetailByPickupCode(pickupCode);
    }

    @Override
    public Observable<BooleanResult> updateOnlineOrderStatus(String orderNo, String orderFinishStatus) {
        return this.mRemoteDataSource.updateOnlineOrderStatus(orderNo, orderFinishStatus);
    }
}
