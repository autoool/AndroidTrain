package cn.fantasee.data.net;

import java.util.List;

import okhttp3.RequestBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import rx.Observable;

/**
 * Created by zchao on 2016/5/5.
 */
public interface ApiService {

    @FormUrlEncoded
    @POST("initLoginUsers.do")
    Observable<HttpResult<List<UserInfo>>> initLoginUsers(
            @Field("manufactureDeviceCode") String manufactureDeviceCode
    );

    @FormUrlEncoded
    @POST("getProducts.do")
    Observable<HttpResult<InitProductWrap>> initProduct(
            @Field("manufactureDeviceCode") String manufactureDeviceCode
    );

    @FormUrlEncoded
    @POST("getStorePaymentSetting.do")
    Observable<HttpResult<PayTypeSetting>> getStorePaymentSetting(
            @Field("manufactureDeviceCode") String manufactureDeviceCode
    );

    @FormUrlEncoded
    @POST("getExpressCompany.do")
    Observable<HttpResult<List<ExpressCompany>>> getExpressCompany(
            @Field("manufactureDeviceCode") String manufactureDeviceCode
    );


    @FormUrlEncoded
    @POST("getRefundReason.do")
    Observable<HttpResult<List<RefundReason>>> getRefundReason(
            @Field("manufactureDeviceCode") String manufactureDeviceCode
    );

    @FormUrlEncoded
    @POST("getOnlineOrderRefuseReason.do")
    Observable<HttpResult<List<OnlineRefuseReason>>> getOnlineOrderRefuseReason(
            @Field("manufactureDeviceCode") String manufactureDeviceCode
    );

    @FormUrlEncoded
    @POST("login.do")
    Observable<HttpResult<LoginUser>> login(
            @Field("username") String username,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("initPushDevice.do")
    Observable<HttpResult<BooleanResult>> initPushDevice(
            @Field("devicePushId") String devicePushId
    );

    //查询在线订单数量 手动调用地址
    @FormUrlEncoded
    @POST("pushMessage.do")
    Observable<HttpResult<BooleanResult>> pushMessage(
            @Field("storeId") String storeId
    );

    // 退款账号验证
    @FormUrlEncoded
    @POST("checkOrderRefundConfirmUser.do")
    Observable<HttpResult<BooleanResult>> checkOrderRefundConfirmUser(
            @Field("username") String username,
            @Field("password") String password
    );

    /**
     * @param queryCode
     * @param isQrCode  是否是扫二维码
     * @return
     */
    @FormUrlEncoded
    @POST("getMemberInfo.do")
    Observable<HttpResult<Member>> getMemberInfo(
            @Field("queryCode") String queryCode,
            @Field("isQrCode") boolean isQrCode
    );

    @FormUrlEncoded
    @POST("createSalesOrder.do")
    Observable<HttpResult<OrderResult>> createSalesOrder(
            @Field("jsonOrder") String jsonOrder
    );

    @FormUrlEncoded
    @POST("cashPay.do")
    Observable<HttpResult<PayByResponse>> payByCash(
            @Field("orderNo") String orderNo,
            @Field("amount") String amount,
            @Field("paymentId") String paymentId,
            @Field("finishOrder") boolean finishOrder
    );

    @FormUrlEncoded
    @POST("debitcardPay.do")
    Observable<HttpResult<PayByResponse>> payByBankCard(
            @Field("orderNo") String orderNo,
            @Field("tradeNo") String tradeNo,
            @Field("amount") String amount,
            @Field("paymentId") String paymentId,
            @Field("finishOrder") boolean finishOrder
    );

    @FormUrlEncoded
    @POST("payByAliPay.do")
    Observable<HttpResult<PayResponse>> alipay(
            @Field("orderNo") String orderNo,
            @Field("amount") String amount,
            @Field("code") String code,
            @Field("paymentId") String paymentId,
            @Field("finishOrder") boolean finishOrder
    );

    @FormUrlEncoded
    @POST("alipayQuery.do")
    Observable<HttpResult<PayResponse>> alipayQuery(
            @Field("orderNo") String orderNo
    );

    @FormUrlEncoded
    @POST("wxPay.do")
    Observable<HttpResult<PayResponse>> weixinpay(
            @Field("orderNo") String orderNo,
            @Field("amount") String amount,
            @Field("code") String code,
            @Field("paymentId") String paymentId,
            @Field("finishOrder") boolean finishOrder
    );

    @FormUrlEncoded
    @POST("wxpayQuery.do")
    Observable<HttpResult<PayResponse>> weixinpayQuery(
            @Field("orderno") String orderNo
    );

    @FormUrlEncoded
    @POST("payByThirdParty.do")
    Observable<HttpResult<PayByResponse>> payByDebitQRCode(
            @Field("orderNo") String orderNo,
            @Field("amount") String amount,
            @Field("tradeNo") String tradeNo,
            @Field("payType") String payType,
            @Field("paymentId") String paymentId,
            @Field("finishOrder") boolean finishOrder
    );

    /**
     * @param orderNo
     * @param status  订单状态 STATUS_NOT_PAY,STATUS_DOING,STATUS_DONE,
     *                STATUS_FINISHED,STATUS_CANCELED,STATUS_CLOSED
     * @return
     */
    @FormUrlEncoded
    @POST("updateSalesOrderStatus.do")
    Observable<HttpResult<PayByResponse>> updateSalesOrderStatus(
            @Field("orderNo") String orderNo,
            @Field("status") String status
    );

//    /**
//     * @param pageIndex
//     * @param pageSize
//     * @param channel            订单来源
//     * @param orderFinishStatus  订单完成状态
//     * @param orderConfirmStatus 订单确认状态
//     * @param todayOnly
//     * @return
//     */
//    @FormUrlEncoded
//    @POST("getSalesOrdersByStatus.do")
//    Observable<HttpResult<List<ResponseOrder>>> getSalesOrdersByStatus(
//            @Field("pageIndex") String pageIndex,
//            @Field("pageSize") String pageSize,
//            @Field("channel") String channel,
//            @Field("orderFinishStatus") String orderFinishStatus,
//            @Field("orderConfirmStatus") String orderConfirmStatus,
//            @Field("isSelfPickUp") boolean isSelfPickUp,
//            @Field("todayOnly") boolean todayOnly
//    );

    /**
     * @param pageIndex
     * @param pageSize
     * @param channel           订单来源
     * @param orderLinearStatus 订单状态
     * @param todayOnly
     * @return
     */
    @FormUrlEncoded
    @POST("getSalesOrdersByStatus.do")
    Observable<HttpResult<List<ResponseOrder>>> getSalesOrdersByStatus(
            @Field("pageIndex") String pageIndex,
            @Field("pageSize") String pageSize,
            @Field("channel") String channel,
            @Field("orderLinearStatus") String orderLinearStatus,
            @Field("isSelfPickUp") boolean isSelfPickUp,
            @Field("todayOnly") boolean todayOnly
    );

    @FormUrlEncoded
    @POST("getOrderDetailByOrderNo.do")
    Observable<HttpResult<ResponseOrderDetail>> getOrderDetail(
            @Field("orderNo") String orderNo
    );

    @FormUrlEncoded
    @POST("changeLoginPassword.do")
    Observable<HttpResult<BooleanResult>> changeLoginPassword(
            @Field("oldPassword") String oldPassword,
            @Field("newPassword") String newPassword
    );

    @FormUrlEncoded
    @POST("sendOnlineOrder.do")
    Observable<HttpResult<BooleanResult>> sendOnlineOrder(
            @Field("orderNo") String orderNo,
            @Field("expressCompanyId") String expressCompanyId,
            @Field("trackingNumber") String trackingNumber
    );

    /**
     * 退货接口 不用
     *
     * @param orderNo
     * @param refundAmount
     * @param returnReasonType 退后原因类型
     * @param refuseReasonType 网络订单拒绝类型
     * @param confirmUserId    确认主管id
     */
    @FormUrlEncoded
    @POST("returnOrderByOrderNo.do")
    Observable<HttpResult<ReturnResponse>> returnOrderByOrderNo(
            @Field("orderNo") String orderNo,
            @Field("refundAmount") String refundAmount,
            @Field("returnReasonType") String returnReasonType,
            @Field("refuseReasonType") String refuseReasonType,
            @Field("confirmUserId") String confirmUserId
    );

    /**
     * 退款接口
     *
     * @param orderNo
     * @param refundAmount
     * @param returnReasonType 退货原因类型
     * @param refuseReasonType 退款原因类型
     * @param confirmUserId    确认管理员id
     * @param thirdRefund      是否是第三方支付
     * @return
     */
    @FormUrlEncoded
    @POST("refundOrderByOrderNo.do")
    Observable<HttpResult<List<RefundResponse>>> refundOrderByOrderNo(
            @Field("orderNo") String orderNo,
            @Field("refundAmount") String refundAmount,
            @Field("returnReasonType") String returnReasonType,
            @Field("refuseReasonType") String refuseReasonType,
            @Field("confirmUserId") String confirmUserId,
            @Field("thirdRefund") boolean thirdRefund
    );

    @FormUrlEncoded
    @POST("getShiftReport.do")
    Observable<HttpResult<ShiftReport>> getShiftReport(
            @Field("deviceUserId") String deviceUserId
    );

    @FormUrlEncoded
    @POST("getShiftStatistics.do")
    Observable<HttpResult<List<DayReport>>> getShiftStatistics(
            @Field("queryDate") String queryDate
    );

    @FormUrlEncoded
    @POST("getProductStatisticsReport.do")
    Observable<HttpResult<List<ProductStatisticItem>>> getProductStatisticsReport(
            @Field("queryDate") String queryDate
    );

    @FormUrlEncoded
    @POST("getMemberTagLibrary.do")
    Observable<HttpResult<List<MemberTag>>> getMemberTagLibrary(
            @Field("manufactureDeviceCode") String manufactureDeviceCode
    );

    @FormUrlEncoded
    @POST("editMemberTags.do")
    Observable<HttpResult<BooleanResult>> editMemberTags(
            @Field("memberId") String memberId,
            @Field("tags") String tags
    );

    @FormUrlEncoded
    @POST("getFundShareQrcode.do")
    Observable<HttpResult<QrCode>> getFundShareQrcode(
            @Field("orderNo") String orderNo
    );

    @FormUrlEncoded
    @POST("queryFundSharePay.do")
    Observable<HttpResult<FundShareQueryResult>> queryFundSharePay(
            @Field("orderNo") String orderNo
    );

    @FormUrlEncoded
    @POST("confirmOnlineOrder.do")
    Observable<HttpResult<BooleanResult>> confirmOnlineOrder(
            @Field("orderNo") String orderNo
    );

    /**
     * 关键字查询订单 接口<br>
     * 开始时间结束时间为空 则查询所有
     *
     * @param channel            来源
     * @param orderFinishStatus  订单完成状态
     * @param orderConfirmStatus 订单确认状态
     * @param keyword            关键字
     * @param beginTime          开始时间(YYYY-HH-DD)
     * @param endTime            结束时间(YYYY-HH-DD)
     */
    @FormUrlEncoded
    @POST("getOrderList.do")
    Observable<HttpResult<List<ResponseOrder>>> getOrderList(
            @Field("keyword") String keyword,
            @Field("channel") String channel,
            @Field("orderConfirmStatus") String orderConfirmStatus,
            @Field("orderFinishStatus") String orderFinishStatus,
            @Field("pageIndex") String pageIndex,
            @Field("pageSize") String pageSize,
            @Field("isSelfPickUp") boolean isSelfPickUp,
            @Field("beginTime") String beginTime,
            @Field("endTime") String endTime
    );

    @FormUrlEncoded
    @POST("getOrderDetailByPickupCode.do")
    Observable<HttpResult<ResponseOrderDetail>> getOrderDetailByPickupCode(
            @Field("pickupCode") String pickupCode
    );

    @FormUrlEncoded
    @POST("updateOnlineOrderStatus.do")
    Observable<HttpResult<BooleanResult>> updateOnlineOrderStatus(
            @Field("orderNo") String orderNo,
            @Field("orderFinishStatus") String orderFinishStatus
    );

    @Multipart
    @POST("upload")
    Observable<HttpResult<String>> uploadImage(
            @Part("photo") RequestBody photo,
            @Part("description") RequestBody description);
}
