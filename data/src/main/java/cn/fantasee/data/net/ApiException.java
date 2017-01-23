package cn.fantasee.data.net;

/**
 * Created by zchao on 2016/9/5.
 */
public class ApiException extends Exception {

    private int code;
    private String message;

    public ApiException(int code, String detailMessage) {
        this.code = code;
        this.message = detailMessage;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
