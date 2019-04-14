package com.example.hbeulibrary.Util;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

public class ActivityCollector {

    public static List<Activity> activities = new ArrayList<>();  //暂存活动

    //添加一个活动
    public static void addActivity(Activity activity){
        activities.add(activity);
    }
    //移除一个活动
    public static void removeActivity(Activity activity){
        activities.remove(activity);
    }
    //销毁所有活动
    public static void finishAll(){
        for(Activity activity : activities){
            if (!activity.isFinishing())
                activity.finish();
        }
        activities.clear();
    }
}
