package com.example.hbeulibrary.Model;

import android.content.Context;

import com.example.hbeulibrary.DB.User;

import java.util.List;

public interface IUserModel {
    //请求登录
    void requestLogin(String account, String password);
    //保存登录账号信息
    void saveLoginData(String account, String password, boolean isChecked, Context context);
    //读取用户登录的账号密码
    String[] readLoginData(Context context);
    //删除账号信息
    void deleteUser(Context context);

}
