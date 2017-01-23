package cn.fantasee.data.repository.datasource.local;

import cn.fantasee.data.cache.DataCache;

import java.util.List;

import rx.Observable;

/**
 * Created by zchao on 2016/7/6.
 * //本地数据源
 */
public class LocalDataSource {

    private DataCache mDataCache;

    public LocalDataSource(DataCache dataCache) {
        this.mDataCache = dataCache;
    }


    public Observable<InitProductWrap> initProduct() {
        return this.mDataCache.getProducts();
    }


    public Observable<List<UserInfo>> initUserInfo() {
        return this.mDataCache.getLoginUsers();
    }


    public Observable<LoginUser> login() {
        return this.mDataCache.getLoginUser();
    }

}
