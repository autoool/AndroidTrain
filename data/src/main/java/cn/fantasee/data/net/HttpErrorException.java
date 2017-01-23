package cn.fantasee.data.net;

/**
 * Created by zchao on 2016/3/25.
 */
public class HttpErrorException extends RuntimeException{

    private String code;
    private String message;

    public HttpErrorException(String code,String detailMessage) {
        super(detailMessage);
        this.code = code;
        this.message = detailMessage;
    }

    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
