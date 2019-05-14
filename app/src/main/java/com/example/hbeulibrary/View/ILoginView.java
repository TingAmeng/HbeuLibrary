package com.example.hbeulibrary.View;

public interface ILoginView {
    //登录成功的画面
    void loginSuccess(String result);
    //登录失败的画面
    void loginFailed(String msg);
    //显示加载框
    void showDialog();
    //取消加载框
    void dismissDialog();
}
