package com.example.hbeulibrary.Presenter;

import android.content.Context;

public interface IUserPresenter {
    //用户登录的接口
    void requestLogin(String account,String password);
    //登录失败
    void loginFailed(String msg);
    //登录成功
    void loginSuccess(String result);
    //保存登录信息
    void saveLoginData(String account, String password, boolean isCheck, Context context);
    //读取登录信息
    String[] readLoginData(Context context);
    //删除用户信息
    void deleteUser(Context context);
}
