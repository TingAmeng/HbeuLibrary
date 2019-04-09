package com.example.hbeulibrary.Util;

/*每当程序启动的时候，系统会自动将这个类进行初始化，
 * 在其他地方有需要获取当前context的时候，可调用此类的getContext()
 * 方法，轻松获得*/
import android.app.Application;
import android.content.Context;

import org.litepal.LitePalApplication;

import java.util.ArrayList;
import java.util.List;

public class MyApplication extends LitePalApplication {
    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    public static Context getContext(){
        return context;
    }

}
