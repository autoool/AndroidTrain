package cn.fantasee.domain.interactor;

import rx.Observable;

/**
 * Created by zchao on 2016/5/5.
 */
public abstract class RxBaseCase<T> {

    protected RxBaseCase() {
    }

    public abstract RxBaseCase initParams(String... paras);
    public abstract Observable<T> execute();
}
