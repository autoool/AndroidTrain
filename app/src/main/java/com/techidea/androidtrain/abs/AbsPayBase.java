package com.techidea.androidtrain.abs;

/**
 * Created by zchao on 2017/4/19.
 */

public abstract class AbsPayBase {

    protected final String TAG = this.getClass().getSimpleName();

    protected String baseData = "ZC";

    public AbsPayBase() {
    }

    protected boolean check(boolean isExist) {
        if (isExist) {
            return false;
        }
        return true;
    }
}
