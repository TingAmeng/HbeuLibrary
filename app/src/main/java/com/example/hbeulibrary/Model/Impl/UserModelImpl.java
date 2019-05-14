package com.example.hbeulibrary.Model.Impl;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;

import com.example.hbeulibrary.DB.User;
import com.example.hbeulibrary.Model.IUserModel;
import com.example.hbeulibrary.Presenter.IUserPresenter;
import com.example.hbeulibrary.Util.MyApplication;

import org.litepal.LitePal;
import java.util.List;

public class UserModelImpl implements IUserModel {

    private static final int FAILED = 0;
    private static final int SUCCESS = 1;
    private IUserPresenter userPresenter;

    int userId = MyApplication.getUserId();


    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case FAILED:
                    userPresenter.loginFailed("账号或密码不正确");
                    break;
                case SUCCESS:
                    userPresenter.loginSuccess("登录成功");
                    break;
            }
        }
    };

    public UserModelImpl(IUserPresenter presenter) {
        this.userPresenter = presenter;
    }

    @Override
    public void requestLogin(String account, final String password) {
        final List<User> userList = LitePal.where("account = ?",account).find(User.class);
        //模拟登录过程
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (userList.isEmpty()) {
                    handler.sendEmptyMessage(FAILED);
                } else if (userList.get(0).getPassword().equals(password)) {
                    int userId = userList.get(0).getId();
                    MyApplication application = new MyApplication();
                    application.setUserId(userId);
                    handler.sendEmptyMessage(SUCCESS);

                } else {
                    handler.sendEmptyMessage(FAILED);
                }
            }
        },1000);

    }

    @Override
    public void saveLoginData(String account, String password, boolean isChecked, Context context) {
        if (isChecked) {  //如果用户勾选了 记住密码 ，则保存账号、密码到  pref 中
            SharedPreferences.Editor editor = context.getSharedPreferences("user_login",Context.MODE_PRIVATE).edit();
            editor.putString("account", account);
            editor.putString("password", password);
            editor.apply();
        } else {    //如果用户取消勾选，则清空 pref
            SharedPreferences.Editor editor = context.getSharedPreferences("user_login",Context.MODE_PRIVATE).edit();
            editor.clear();
            editor.apply();
        }
    }

    @Override
    public String[] readLoginData(Context context) {
        String[] strings = new String[2];
        SharedPreferences pref = context.getSharedPreferences("user_login", context.MODE_PRIVATE);
        strings[0] = pref.getString("account", "");
        strings[1] = pref.getString("password","");
        return strings;
    }

    @Override
    public void deleteUser(Context context) {
        SharedPreferences.Editor editor = context.getSharedPreferences("user_login",Context.MODE_PRIVATE).edit();
        editor.clear();
        editor.apply();
        SharedPreferences.Editor editor1 = context.getSharedPreferences("user"+userId+"collect",Context.MODE_PRIVATE).edit();
        editor1.clear();
        editor1.apply();
    }

}
