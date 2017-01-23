package cn.fantasee.data.net;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by zchao on 2016/6/12.
 */
public class RxUtils {

    public static <T> Observable.Transformer<T, T> rxSchedulerHelper() {
        return new Observable.Transformer<T, T>() {
            @Override
            public Observable<T> call(Observable<T> tObservable) {
                return tObservable.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .unsubscribeOn(Schedulers.io());
            }
        };
    }

  /*  public static <T> Observable.Transformer<HttpResult<T>, T> handleResult() {
        return new Observable.Transformer<HttpResult<T>, T>() {
            @Override
            public Observable<T> call(Observable<HttpResult<T>> httpResultObservable) {
                return httpResultObservable.flatMap(
                        new Func1<HttpResult<T>, Observable<T>>() {
                            @Override
                            public Observable<T> call(final HttpResult<T> tHttpResult) {
                                if (tHttpResult.getCode() == 1) {
//                                    返回真正需要的数据
                                    return Observable.create(new Observable.OnSubscribe<T>() {
                                        @Override
                                        public void call(Subscriber<? super T> subscriber) {
                                            if (tHttpResult.getObject() != null) {

                                            } else if (tHttpResult.getList() != null) {

                                            }
                                        }
                                    });
                                } else if (tHttpResult.getCode() == 900) {
                                    // TODO: 2016/7/5 token失效
                                } else {
                                    //处理错误
                                }
                                return Observable.empty();
                            }
                        }
                );
            }
        };
    }

    private static <T> Observable<T> getData(T t) {
        return null;
    }*/

   /* public static <T> Observable.Transformer<HttpResult<T>, T> handleResult() {
        return new Observable.Transformer<HttpResult<T>, T>() {
            @Override
            public Observable<T> call(Observable<HttpResult<T>> httpResultObservable) {
                return httpResultObservable.flatMap(new Func1<HttpResult<T>, Observable<T>>() {
                    @Override
                    public Observable<T> call(HttpResult<T> tHttpResult) {
                        if (tHttpResult.getCode() == 1) {
                            if (tHttpResult.getObject() != null) {
                                return null;
                            } else if (tHttpResult.getList() != null) {
                                return null;
                            }
                        } else {
                            return Observable.error(new HttpErrorException(tHttpResult.getCode(), tHttpResult.getMsg()))
                        }
                    }
                });
            }
        };
    }*/

}
