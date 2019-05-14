package com.example.hbeulibrary.View;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import com.example.hbeulibrary.DB.Lend;
import com.example.hbeulibrary.R;
import org.litepal.LitePal;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class NotifyService extends Service {

    //创建CheckLendBinder实例
    private CheckLendBinder mBinder = new CheckLendBinder();


    /*新建一个CheckLendBinder类，继承自Binder,
    内部提供检查书籍借阅到期的方法*/
    class CheckLendBinder extends Binder {

        public void startNotify() {
            long day = 0;
            List<Lend> lendList = LitePal.findAll(Lend.class);
            for (int i =0; i < lendList.size(); i++) {
                Lend lend = lendList.get(i);
                try {
                    //将 截止日期 还原成 Date对象
                    String returnDateStr = lend.getReturnDate();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    Date returnDate = sdf.parse(returnDateStr);
                    //
                    Date mDate = new Date();
                    String nowDateStr = sdf.format(mDate);
                    Date nowDate = sdf.parse(nowDateStr);
                    day = (returnDate.getTime() - nowDate.getTime()) / (24 * 60 * 60 * 1000);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if (day < 7) {
                    getNotificationManager().notify(i,
                            getNotification("到期通知",lend.getBookName(),lend.getReturnDate()));
                }
            }
        }
    }

    public NotifyService() {
    }

    @Override
    //活动和服务绑定的时候调用
    public IBinder onBind(Intent intent) {
        return mBinder;   //返回mBinder对象
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        /*new Thread(new Runnable() {
            @Override
            public void run() {
                //处理具体逻辑
                stopSelf();   //任务完成后自动停止服务
            }
        }).start();*/
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    /*此方法返回一个 manager来对通知进行管理，创建通知的第一步*/
    private NotificationManager getNotificationManager(){
        return (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
    }

    private Notification getNotification(String title,String bookName,String returnDate){
        Intent intent = new Intent(this,MainActivity.class);
        //给通知 添加一个点击意图，点击即进入MainActivity
        PendingIntent pi = PendingIntent.getActivity(this,0,intent,0);
        /*使用Builder构造器,来构建Notification，并实例化为Builder*/
        NotificationCompat.Builder builder = new
                NotificationCompat.Builder(this)
                .setContentTitle(title)
                .setStyle(new NotificationCompat.BigTextStyle().
                        bigText("您借阅的（"+bookName+")将在（"+returnDate+")到期，请及时还书"))
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher))
                .setAutoCancel(true)
                .setSound(Uri.fromFile(new File
                        ("/system/media/audio/notifications/Jingle.ogg")))//设置提示音
                .setVibrate(new long[]{0,200}); //设置手机静止和震动的时长，还要在配置文件声明权限 //设置通知的重要程度

        //返回构建好的通知
        return builder.build();
    }



}
