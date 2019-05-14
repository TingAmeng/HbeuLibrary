package com.example.hbeulibrary.Presenter.Impl;

import android.content.Context;

import com.example.hbeulibrary.Model.IUserModel;
import com.example.hbeulibrary.Model.Impl.UserModelImpl;
import com.example.hbeulibrary.Presenter.IUserPresenter;
import com.example.hbeulibrary.View.IMyView;
import com.example.hbeulibrary.View.ILoginView;

public class UserPresenterImpl implements IUserPresenter {

    private IUserModel iUserModel;
    private ILoginView iLoginView;
    private IMyView iMyView;

    public UserPresenterImpl(ILoginView iLoginView) {
        this.iLoginView = iLoginView;
        this.iUserModel = new UserModelImpl(this);
    }
    public UserPresenterImpl(IMyView iMyView) {
        this.iMyView = iMyView;
        this.iUserModel = new UserModelImpl(this);
    }

    @Override
    public void requestLogin(String account,String password) {
        if (iLoginView !=null ) {
            //通知LoginActivity 显示加载框
            iLoginView.showDialog();
        }

        if (iUserModel != null) {
            // 通知 model开始执行
            iUserModel.requestLogin(account,password);
        }
    }

    @Override
    public void loginFailed(String msg) {
        if (iLoginView!= null) {
            //通知 LoginActivity 取消加载框显示登录失败信息
            iLoginView.dismissDialog();
            iLoginView.loginFailed(msg);
        }
    }

    @Override
    public void loginSuccess(String result) {
        if (iLoginView != null) {
            //通知 LoginActivity 取消加载框，并做登录成功后处理
            iLoginView.dismissDialog();
            iLoginView.loginSuccess(result);
        }
    }

    @Override
    public void saveLoginData(String account, String password, boolean isCheck, Context context) {

        iUserModel.saveLoginData(account,password,isCheck,context);
    }

    @Override
    public String[] readLoginData(Context context) {
        String[] strings = iUserModel.readLoginData(context);
        return strings;

    }

    @Override
    public void deleteUser(final Context context) {
        final Context mContext = context;
        if (iMyView!=null ) {
            //通知LoginActivity 显示加载框
            iMyView.showDialog();
        }

        if (iUserModel != null) {
            // 通知 model开始执行
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    iUserModel.deleteUser(mContext);
                }
            }).start();
            iMyView.dismissDialog();

        }
    }


}
