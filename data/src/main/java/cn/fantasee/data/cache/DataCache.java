package cn.fantasee.data.cache;

import java.util.List;

import rx.Observable;

/**
 * Created by zchao on 2016/7/6.
 */
public interface DataCache {

    Observable<List<UserInfo>> getLoginUsers();

    void putLoginUsers(List<UserInfo> userInfoList);

    Observable<InitProductWrap> getProducts();

    void putProducts(InitProductWrap productList);

    Observable<LoginUser> getLoginUser();

    void putLoginUser(LoginUser loginUser);

    boolean isCached(String filename);

    boolean isExpired(String filename);

    void evictAll();
}
