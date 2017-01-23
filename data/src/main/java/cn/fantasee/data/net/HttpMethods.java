package cn.fantasee.data.net;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.functions.Func1;

/**
 * Created by zchao on 2016/5/5.
 */
public class HttpMethods {

    private static String BASE_URL = "";
    private static String BASE_URL_HTTPS = "";
    private static final int DEFAULT_TIMEOUT_SECONDS = 30;

    private String token = "";
    //base params
    private Map<String, String> baseHeaderParams;

    private Retrofit retrofit;
    private Retrofit retrofitHttps;
    private ApiService service;
    private ApiService serviceHttps;

    private static class SingletonHolder {
        private static final HttpMethods INSTANCE = new HttpMethods();
    }

    public static HttpMethods getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public static void setBaseUrl(String baseUrl) {
        BASE_URL = baseUrl;
    }

    public static void setBaseUrlHttps(String baseUrlHttps) {
        BASE_URL_HTTPS = baseUrlHttps;
    }

    private HttpMethods() {
        baseHeaderParams = new HashMap<>();
        retrofit = new Retrofit.Builder()
                .client(getClient())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(BASE_URL)
                .build();

       /* retrofitHttps = new Retrofit.Builder()
                .client(getHttpsClient())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(BASE_URL_HTTPS)
                .build();*/

        service = retrofit.create(ApiService.class);
//        serviceHttps = retrofitHttps.create(HttpApi.class);
    }

    public Observable<List<UserInfo>> initLoginUsers() {
        return service.initLoginUsers(null)
                .compose(RxUtils.<HttpResult<List<UserInfo>>>rxSchedulerHelper())
                .map(new HttpResultFuncList<List<UserInfo>>())
                .onErrorResumeNext(new HttpExceptionFunc<List<UserInfo>>());
    }

    public Observable<InitProductWrap> initProduct() {
        return service.initProduct(null)
                .compose(RxUtils.<HttpResult<InitProductWrap>>rxSchedulerHelper())
                .map(new HttpResultFuncObject<InitProductWrap>())
                .onErrorResumeNext(new HttpExceptionFunc<InitProductWrap>());
    }

    public Observable<PayTypeSetting> getStorePaymentSetting() {
        return service.getStorePaymentSetting(null)
                .compose(RxUtils.<HttpResult<PayTypeSetting>>rxSchedulerHelper())
                .map(new HttpResultFuncObject<PayTypeSetting>())
                .onErrorResumeNext(new HttpExceptionFunc<PayTypeSetting>());
    }

    public Observable<List<ExpressCompany>> getExpressCompany() {
        return service.getExpressCompany(null)
                .compose(RxUtils.<HttpResult<List<ExpressCompany>>>rxSchedulerHelper())
                .map(new HttpResultFuncList<List<ExpressCompany>>())
                .onErrorResumeNext(new HttpExceptionFunc<List<ExpressCompany>>());
    }

    public Observable<List<RefundReason>> getRefundReason() {
        return service.getRefundReason(null)
                .compose(RxUtils.<HttpResult<List<RefundReason>>>rxSchedulerHelper())
                .map(new HttpResultFuncList<List<RefundReason>>())
                .onErrorResumeNext(new HttpExceptionFunc<List<RefundReason>>());
    }

    public Observable<List<OnlineRefuseReason>> getOnlineOrderRefuseReason() {
        return service.getOnlineOrderRefuseReason(null)
                .compose(RxUtils.<HttpResult<List<OnlineRefuseReason>>>rxSchedulerHelper())
                .map(new HttpResultFuncList<List<OnlineRefuseReason>>())
                .onErrorResumeNext(new HttpExceptionFunc<List<OnlineRefuseReason>>());
    }

    public Observable<BooleanResult> checkOrderRefundConfirmUser(String userName, String password) {
        return service.checkOrderRefundConfirmUser(userName, password)
                .compose(RxUtils.<HttpResult<BooleanResult>>rxSchedulerHelper())
                .map(new HttpResultFuncObject<BooleanResult>())
                .onErrorResumeNext(new HttpExceptionFunc<BooleanResult>());
    }

    public Observable<LoginUser> login(String username, String password) {
        return service.login(username, password)
                .compose(RxUtils.<HttpResult<LoginUser>>rxSchedulerHelper())
                .map(new HttpResultFuncObject<LoginUser>())
                .onErrorResumeNext(new HttpExceptionFunc<LoginUser>());
    }

    public Observable<BooleanResult> initPushDevice(String devicePushId) {
        return service.initPushDevice(devicePushId)
                .compose(RxUtils.<HttpResult<BooleanResult>>rxSchedulerHelper())
                .map(new HttpResultFuncObject<BooleanResult>())
                .onErrorResumeNext(new HttpExceptionFunc<BooleanResult>());
    }

    public Observable<BooleanResult> pushMessage(String storeId) {
        return service.pushMessage(storeId)
                .compose(RxUtils.<HttpResult<BooleanResult>>rxSchedulerHelper())
                .map(new HttpResultFuncObject<BooleanResult>())
                .onErrorResumeNext(new HttpExceptionFunc<BooleanResult>());
    }

    public Observable<Member> getMemberInfo(String queryCode, boolean isQrCode) {
        return service.getMemberInfo(queryCode, isQrCode)
                .compose(RxUtils.<HttpResult<Member>>rxSchedulerHelper())
                .map(new HttpResultFuncObject<Member>())
                .onErrorResumeNext(new HttpExceptionFunc<Member>());
    }

    public Observable<OrderResult> createSalesOrder(String jsonOrder) {
        return service.createSalesOrder(jsonOrder)
                .compose(RxUtils.<HttpResult<OrderResult>>rxSchedulerHelper())
                .map(new HttpResultFuncObject<OrderResult>())
                .onErrorResumeNext(new HttpExceptionFunc<OrderResult>());
    }

    public Observable<PayByResponse> payByCash(String orderNo, String amount, String paymentId,
                                               boolean finishOrder) {
        return service.payByCash(orderNo, amount, paymentId, finishOrder)
                .compose(RxUtils.<HttpResult<PayByResponse>>rxSchedulerHelper())
                .map(new HttpResultFuncObject<PayByResponse>())
                .onErrorResumeNext(new HttpExceptionFunc<PayByResponse>());
    }

    public Observable<PayResponse> payByAliPay(String orderNo, String amount, String code,
                                               String paymentId, boolean finishOrder) {
        return service.alipay(orderNo, amount, code, paymentId, finishOrder)
                .compose(RxUtils.<HttpResult<PayResponse>>rxSchedulerHelper())
                .map(new HttpResultFuncObject<PayResponse>())
                .onErrorResumeNext(new HttpExceptionFunc<PayResponse>());
    }

    public Observable<PayResponse> alipayQuery(String orderNo) {
        return service.alipayQuery(orderNo)
                .compose(RxUtils.<HttpResult<PayResponse>>rxSchedulerHelper())
                .map(new HttpResultFuncObject<PayResponse>())
                .onErrorResumeNext(new HttpExceptionFunc<PayResponse>());
    }

    public Observable<PayResponse> payByWeiXinPay(String orderNo, String amount, String code,
                                                  String paymentId, boolean finishOrder) {
        return service.weixinpay(orderNo, amount, code, paymentId, finishOrder)
                .compose(RxUtils.<HttpResult<PayResponse>>rxSchedulerHelper())
                .map(new HttpResultFuncObject<PayResponse>())
                .onErrorResumeNext(new HttpExceptionFunc<PayResponse>());
    }

    public Observable<PayResponse> weixinpayQuery(String orderNo) {
        return service.weixinpayQuery(orderNo)
                .compose(RxUtils.<HttpResult<PayResponse>>rxSchedulerHelper())
                .map(new HttpResultFuncObject<PayResponse>())
                .onErrorResumeNext(new HttpExceptionFunc<PayResponse>());
    }

    public Observable<PayByResponse> payByBankCard(String orderNo, String tradeNo, String amount,
                                                   String paymentId, boolean finishOrder) {
        return service.payByBankCard(orderNo, tradeNo, amount, paymentId, finishOrder)
                .compose(RxUtils.<HttpResult<PayByResponse>>rxSchedulerHelper())
                .map(new HttpResultFuncObject<PayByResponse>())
                .onErrorResumeNext(new HttpExceptionFunc<PayByResponse>());
    }

    public Observable<PayByResponse> updateSalesOrderStatus(String orderNo, String status) {
        return service.updateSalesOrderStatus(orderNo, status)
                .compose(RxUtils.<HttpResult<PayByResponse>>rxSchedulerHelper())
                .map(new HttpResultFuncObject<PayByResponse>())
                .onErrorResumeNext(new HttpExceptionFunc<PayByResponse>());
    }

    public Observable<PayByResponse> payByDebitQRCode(String orderNo, String amount, String tradeNo,
                                                      String payType, String paymentId, boolean finishOrder) {
        return service.payByDebitQRCode(orderNo, amount, tradeNo, payType, paymentId, finishOrder)
                .compose(RxUtils.<HttpResult<PayByResponse>>rxSchedulerHelper())
                .map(new HttpResultFuncObject<PayByResponse>())
                .onErrorResumeNext(new HttpExceptionFunc<PayByResponse>());
    }

    public Observable<List<ResponseOrder>> getSalesOrdersByStatus(String pageIndex, String pageSize, String source,
                                                                  String orderLinearStatus, boolean isSelfPickUp,
                                                                  boolean todayOnly) {
        return service.getSalesOrdersByStatus(pageIndex, pageSize, source,
                orderLinearStatus, isSelfPickUp, todayOnly)
                .compose(RxUtils.<HttpResult<List<ResponseOrder>>>rxSchedulerHelper())
                .map(new HttpResultFuncList<List<ResponseOrder>>())
                .onErrorResumeNext(new HttpExceptionFunc<List<ResponseOrder>>());
    }

    public Observable<ResponseOrderDetail> getOrderDetail(String orderNo) {
        return service.getOrderDetail(orderNo)
                .compose(RxUtils.<HttpResult<ResponseOrderDetail>>rxSchedulerHelper())
                .map(new HttpResultFuncObject<ResponseOrderDetail>())
                .onErrorResumeNext(new HttpExceptionFunc<ResponseOrderDetail>());
    }

    public Observable<BooleanResult> changeLoginPassword(String oldPass, String newPass) {
        return service.changeLoginPassword(oldPass, newPass)
                .compose(RxUtils.<HttpResult<BooleanResult>>rxSchedulerHelper())
                .map(new HttpResultFuncObject<BooleanResult>())
                .onErrorResumeNext(new HttpExceptionFunc<BooleanResult>());
    }

    public Observable<BooleanResult> sendOnlineOrder(String orderNo, String expressCompanyId, String trackingNumber) {
        return service.sendOnlineOrder(orderNo, expressCompanyId, trackingNumber)
                .compose(RxUtils.<HttpResult<BooleanResult>>rxSchedulerHelper())
                .map(new HttpResultFuncObject<BooleanResult>())
                .onErrorResumeNext(new HttpExceptionFunc<BooleanResult>());
    }

    public Observable<ReturnResponse> returnOrderByOrderNo(String orderNo, String refundAmount, String returnReasonType,
                                                           String refuseReasonType, String confirmUserId) {
        return service.returnOrderByOrderNo(orderNo, refundAmount, returnReasonType, refuseReasonType, confirmUserId)
                .compose(RxUtils.<HttpResult<ReturnResponse>>rxSchedulerHelper())
                .map(new HttpResultFuncObject<ReturnResponse>())
                .onErrorResumeNext(new HttpExceptionFunc<ReturnResponse>());
    }


    public Observable<List<RefundResponse>> refundOrderByOrderNo(String orderNo, String refundAmount, String returnReasonType,
                                                                 String refuseReasonType, String confirmUserId, boolean thirdRefund) {
        return service.refundOrderByOrderNo(orderNo, refundAmount, returnReasonType, refuseReasonType, confirmUserId, thirdRefund)
                .compose(RxUtils.<HttpResult<List<RefundResponse>>>rxSchedulerHelper())
                .map(new HttpResultFuncList<List<RefundResponse>>())
                .onErrorResumeNext(new HttpExceptionFunc<List<RefundResponse>>());
    }

    public Observable<ShiftReport> getShiftReport(String deviceUserId) {
        return service.getShiftReport(deviceUserId)
                .compose(RxUtils.<HttpResult<ShiftReport>>rxSchedulerHelper())
                .map(new HttpResultFuncObject<ShiftReport>())
                .onErrorResumeNext(new HttpExceptionFunc<ShiftReport>());
    }

    public Observable<List<DayReport>> getShiftStatistics(String queryDate) {
        return service.getShiftStatistics(queryDate)
                .compose(RxUtils.<HttpResult<List<DayReport>>>rxSchedulerHelper())
                .map(new HttpResultFuncList<List<DayReport>>())
                .onErrorResumeNext(new HttpExceptionFunc<List<DayReport>>());
    }

    public Observable<List<ProductStatisticItem>> getProductStatisticsReport(String queryDate) {
        return service.getProductStatisticsReport(queryDate)
                .compose(RxUtils.<HttpResult<List<ProductStatisticItem>>>rxSchedulerHelper())
                .map(new HttpResultFuncList<List<ProductStatisticItem>>())
                .onErrorResumeNext(new HttpExceptionFunc<List<ProductStatisticItem>>());
    }

    public Observable<List<MemberTag>> getMemberTagLibrary() {
        return service.getMemberTagLibrary(null)
                .compose(RxUtils.<HttpResult<List<MemberTag>>>rxSchedulerHelper())
                .map(new HttpResultFuncList<List<MemberTag>>())
                .onErrorResumeNext(new HttpExceptionFunc<List<MemberTag>>());
    }

    public Observable<BooleanResult> editMemberTags(String memberId, String tags) {
        return service.editMemberTags(memberId, tags)
                .compose(RxUtils.<HttpResult<BooleanResult>>rxSchedulerHelper())
                .map(new HttpResultFuncObject<BooleanResult>())
                .onErrorResumeNext(new HttpExceptionFunc<BooleanResult>());
    }

    public Observable<QrCode> getFundShareQrcode(String orderNo) {
        return service.getFundShareQrcode(orderNo)
                .compose(RxUtils.<HttpResult<QrCode>>rxSchedulerHelper())
                .map(new HttpResultFuncObject<QrCode>())
                .onErrorResumeNext(new HttpExceptionFunc<QrCode>());
    }

    public Observable<FundShareQueryResult> queryFundSharePay(String orderNo) {
        return service.queryFundSharePay(orderNo)
                .compose(RxUtils.<HttpResult<FundShareQueryResult>>rxSchedulerHelper())
                .map(new HttpResultFuncObject<FundShareQueryResult>())
                .onErrorResumeNext(new HttpExceptionFunc<FundShareQueryResult>());
    }

    public Observable<BooleanResult> confirmOnlineOrder(String orderNo) {
        return service.confirmOnlineOrder(orderNo)
                .compose(RxUtils.<HttpResult<BooleanResult>>rxSchedulerHelper())
                .map(new HttpResultFuncObject<BooleanResult>())
                .onErrorResumeNext(new HttpExceptionFunc<BooleanResult>());
    }

    public Observable<List<ResponseOrder>> getOrderList(String keyWord, String channel,
                                                        String orderConfirmStatus, String orderFinishStatus,
                                                        String pageIndex, String pageSize, boolean isSelfPickUp,
                                                        String beginTime, String endTime) {
        return service.getOrderList(keyWord, channel, orderConfirmStatus, orderFinishStatus, pageIndex, pageSize,
                isSelfPickUp, beginTime, endTime)
                .compose(RxUtils.<HttpResult<List<ResponseOrder>>>rxSchedulerHelper())
                .map(new HttpResultFuncList<List<ResponseOrder>>())
                .onErrorResumeNext(new HttpExceptionFunc<List<ResponseOrder>>());
    }

    public Observable<ResponseOrderDetail> getOrderDetailByPickupCode(String pickupCode) {
        return service.getOrderDetailByPickupCode(pickupCode)
                .compose(RxUtils.<HttpResult<ResponseOrderDetail>>rxSchedulerHelper())
                .map(new HttpResultFuncObject<ResponseOrderDetail>())
                .onErrorResumeNext(new HttpExceptionFunc<ResponseOrderDetail>());
    }

    public Observable<BooleanResult> updateOnlineOrderStatus(String orderNo,String orderFinishStatus){
        return service.updateOnlineOrderStatus(orderNo,orderFinishStatus)
                .compose(RxUtils.<HttpResult<BooleanResult>>rxSchedulerHelper())
                .map(new HttpResultFuncObject<BooleanResult>())
                .onErrorResumeNext(new HttpExceptionFunc<BooleanResult>());
    }

    /**
     * 用来统一处理Http的resultCode,并将HttpResult的Data部分剥离出来返回给subscriber
     *
     * @param <T> Subscriber真正需要的数据类型，也就是Data部分的数据类型
     * @return list
     */
    private class HttpResultFuncList<T> implements Func1<HttpResult<T>, T> {

        @Override
        public T call(HttpResult<T> httpResult) {
            if (!httpResult.getCode().equals("1"))
                throw new HttpErrorException(httpResult.getCode(), httpResult.getMsg());
            return httpResult.getList();
        }
    }

    private class HttpResultFuncObject<T> implements Func1<HttpResult<T>, T> {

        @Override
        public T call(HttpResult<T> httpResult) {
            if (!httpResult.getCode().equals("1")) {
                throw new HttpErrorException(httpResult.getCode(), httpResult.getMsg());
            }
            return httpResult.getObject();
        }
    }

    private class HttpExceptionFunc<T> implements Func1<Throwable, Observable<T>> {
        @Override
        public Observable<T> call(Throwable throwable) {
            return Observable.error(ExceptionHandle.handleException(throwable));
        }
    }


    /* 增加token  在form表单添加*/
    Interceptor baseParamsInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request originalRequest = chain.request();
            Request.Builder requestBuilder = originalRequest.newBuilder();

            if (originalRequest.body() instanceof FormBody) {
                FormBody.Builder newFormBody = new FormBody.Builder();
                FormBody oldFormBody = (FormBody) originalRequest.body();
                for (int i = 0; i < oldFormBody.size(); i++) {
                    newFormBody.addEncoded(oldFormBody.encodedName(i), oldFormBody.encodedValue(i));
                }
                if (token != null && token.trim().length() > 0)
                    newFormBody.add("token", token);
                for (Map.Entry<String, String> next : baseHeaderParams.entrySet()) {
                    newFormBody.add(next.getKey(), next.getValue());
                }
                requestBuilder.method(originalRequest.method(), newFormBody.build());
            }
            Request request = requestBuilder.build();
            return chain.proceed(request);
        }
    };


    private OkHttpClient getClient() {

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        return new OkHttpClient.Builder()
                .addInterceptor(logging)
                .retryOnConnectionFailure(false)
                .readTimeout(DEFAULT_TIMEOUT_SECONDS, TimeUnit.SECONDS)
                .connectTimeout(DEFAULT_TIMEOUT_SECONDS, TimeUnit.SECONDS)
                .addInterceptor(baseParamsInterceptor)
                .build();
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void addBaseHeaderParams(String key, String values) {
        baseHeaderParams.put(key, values);
    }

    public String removeBaseParamByKey(String key) {
        return baseHeaderParams.remove(key);
    }


    //    private OkHttpClient getHttpsClient() {
//        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
//        if (BuildConfig.DEBUG) {
//            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
//        } else {
//            logging.setLevel(HttpLoggingInterceptor.Level.BASIC);
//        }
//        return new OkHttpClient.Builder()
//                .addInterceptor(logging)
//                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
//                .sslSocketFactory(SslSocketFactory.getInstance().getSSLSocketFactory())
//                .build();
//    }
}
