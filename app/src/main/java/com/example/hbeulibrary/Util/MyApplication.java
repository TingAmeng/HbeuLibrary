package com.example.hbeulibrary.Util;

/*每当程序启动的时候，系统会自动将这个类进行初始化，
 * 在其他地方有需要获取当前context的时候，可调用此类的getContext()
 * 方法，轻松获得*/
import android.app.Application;
import android.content.Context;

import org.litepal.LitePal;
import org.litepal.LitePalApplication;

import java.util.ArrayList;
import java.util.List;

public class MyApplication extends Application {
    private static Context context;
    private static int userId;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        //调用LitePal的初始化方法 ，解决 冲突
        LitePal.initialize(context);
    }

    public static Context getContext(){
        return context;
    }

    public static int getUserId() {
        return userId;
    }

    public  void setUserId(int userId) {
        this.userId = userId;
    }
}
