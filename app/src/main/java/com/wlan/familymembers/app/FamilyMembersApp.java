package com.wlan.familymembers.app;

import android.app.Application;

import com.blankj.utilcode.util.Utils;

/**
 * 类作用：
 * Created by Administrator on 2018/9/15.
 */

public class FamilyMembersApp extends Application {
    public static boolean isLogin;
    @Override
    public void onCreate() {
        super.onCreate();
        Utils.init(this);
    }
}
