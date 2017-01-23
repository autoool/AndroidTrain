package cn.fantasee.data.net;

import android.net.ParseException;

import com.google.gson.JsonParseException;

import org.json.JSONException;

import java.io.IOException;
import java.net.ConnectException;
import java.net.UnknownHostException;
import java.util.concurrent.TimeoutException;

import retrofit2.Response;
import retrofit2.adapter.rxjava.HttpException;

/**
 * Created by zchao on 2016/9/5.
 * http://blog.csdn.net/qq122627018/article/details/51689891
 * 定义异常提示信息
 */
public class ExceptionHandle {

    //对应HTTP的状态码
    private static final int UNAUTHORIZED = 401;
    private static final int FORBIDDEN = 403;
    private static final int NOT_FOUND = 404;
    private static final int REQUEST_TIMEOUT = 408;
    private static final int INTERNAL_SERVER_ERROR = 500;
    private static final int BAD_GATEWAY = 502;
    private static final int SERVICE_UNAVAILABLE = 503;
    private static final int GATEWAY_TIMEOUT = 504;
    private static final int PARSE_ERROR = 1101;
    private static final int UNKNOWN = 1102;
    private static final int NETWORD_ERROR = 1103;


    public static ApiException handleException(Throwable throwable) {
        ApiException apiException;
        /*if (throwable instanceof HttpException) {
            HttpException httpException = (HttpException) throwable;
            apiException = new ApiException(httpException.code(), "网络错误");
            Response response = httpException.response();
            switch (httpException.code()) {
                case UNAUTHORIZED:
                case FORBIDDEN:
                case NOT_FOUND:
                case REQUEST_TIMEOUT:
                case GATEWAY_TIMEOUT:
                case INTERNAL_SERVER_ERROR:
                case BAD_GATEWAY:
                case SERVICE_UNAVAILABLE:
                default:
                    apiException.setMessage("网络错误");  //均视为网络错误
                    break;
            }
            return apiException;
        } else*/
        if (throwable instanceof JsonParseException
                || throwable instanceof JSONException
                || throwable instanceof ParseException) {
            apiException = new ApiException(PARSE_ERROR, "数据解析错误，请联系管理员");//均视为解析错误
            return apiException;
        } else if (throwable instanceof HttpErrorException) {
            HttpErrorException exception = (HttpErrorException) throwable;
            apiException = new ApiException(NETWORD_ERROR, exception.getMessage());
            return apiException;
        } else if (throwable instanceof UnknownHostException) {
            apiException = new ApiException(NETWORD_ERROR, "请检查设备网络并联系管理员");
            return apiException;
        } else if (throwable instanceof IOException) {
            apiException = new ApiException(NETWORD_ERROR, "请检查设备网络并联系管理员");
            return apiException;
        } else if (throwable instanceof TimeoutException) {
            apiException = new ApiException(NETWORD_ERROR, "网络连接超时，请检查设备网络后联系管理员");
            return apiException;
        } else if (throwable instanceof ConnectException) {
            apiException = new ApiException(NETWORD_ERROR, "网络连接失败");
            return apiException;
        } else {
            apiException = new ApiException(UNKNOWN, "未知错误，请联系管理员");
            return apiException;
        }
    }
}
