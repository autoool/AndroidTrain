package cn.fantasee.domain.respository;

import java.util.List;

import rx.Observable;

/**
 * Created by zchao on 2016/5/19.
 */
public interface DataRepositoryDomain {

    Observable<InitProductWrap> initProduct();

    Observable<List<UserInfo>> initUserInfo();

    Observable<PayTypeSetting> getStorePaymentSetting();

    Observable<List<RefundReason>> getRefundReason();

    Observable<List<OnlineRefuseReason>> getOnlineOrderRefuseReason();

    Observable<List<ExpressCompany>> getExpressCompany();

    Observable<LoginUser> login(String username, String password);

    Observable<BooleanResult> initPushDevice(String devicePushId);

    Observable<BooleanResult> pushMessage(String storeId);

    Observable<BooleanResult> checkOrderRefundConfirmUser(String userName, String password);

    Observable<Member> getMemberInfo(String queryCode, boolean isQrCode);

    Observable<OrderResult> createSalesOrder(String jsonOrder);

    Observable<PayByResponse> payByCash(String orderNo, String amount, String paymentId,
                                        boolean finishOrder);

    Observable<PayResponse> aliPay(String orderNo, String amount, String code,
                                   String paymentId, boolean finishOrder);

    Observable<PayResponse> alipayQuery(String orderNo);

    Observable<PayResponse> weixinPay(String orderNo, String amount, String code,
                                      String paymentId, boolean finishOrder);

    Observable<PayResponse> weixinpayQuery(String orderNo);

    Observable<PayByResponse> payByBankCard(String orderNo, String tradeNo, String amount,
                                            String paymentId, boolean finishOrder);

    Observable<PayByResponse> updateSalesOrderStatus(String orderNo, String status);

    Observable<PayByResponse> payByDebitQRCode(String orderNo, String amount, String tradeNo,
                                               String payType, String paymentId, boolean finishOrder);

    Observable<List<ResponseOrder>> getSalesOrdersByStatus(String pageIndex, String pageSize, String source,
                                                           String orderLinearStatus,
                                                           boolean isSelfPickUp, boolean todayOnly);

    Observable<ResponseOrderDetail> getOrderDetail(String orderNo);

    Observable<BooleanResult> changeLoginPassword(String oldPass, String newPass);

    Observable<BooleanResult> sendOnlineOrder(String orderNo, String expressCompanyId, String trackingNumber);

    Observable<ReturnResponse> returnOrderByOrderNo(String orderNo, String refundAmount, String returnReasonType,
                                                    String refuseReasonType, String confirmUserId);

    Observable<List<RefundResponse>> refundOrderByOrderNo(String orderNo, String refundAmount, String returnReasonType,
                                                          String refuseReasonType, String confirmUserId, boolean thirdRefund);

    Observable<ShiftReport> getShiftReport(String devicesUserId);

    Observable<List<DayReport>> getShiftStatistics(String queryDate);

    Observable<List<ProductStatisticItem>> getProductStatisticsReport(String queryDate);

    Observable<List<MemberTag>> getMemberTagLibrary();

    Observable<BooleanResult> editMemberTags(String memberId, String tags);

    Observable<QrCode> getFundShareQrcode(String orderNo);

    Observable<FundShareQueryResult> queryFundSharePay(String orderNo);

    Observable<BooleanResult> confirmOnlineOrder(String orderNo);

    Observable<List<ResponseOrder>> getOrderList(String keyWord, String channel,
                                                 String orderConfirmStatus, String orderFinishStatus,
                                                 String pageIndex, String pageSize,boolean isSelfPickUp,
                                                 String beginTime, String endTime);

    Observable<ResponseOrderDetail> getOrderDetailByPickupCode(String pickupCode);

    Observable<BooleanResult> updateOnlineOrderStatus(String orderNo, String orderFinishStatus);
}
