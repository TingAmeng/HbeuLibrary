package com.example.hbeulibrary.View.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hbeulibrary.DB.Book;
import com.example.hbeulibrary.DB.InitDB;
import com.example.hbeulibrary.DB.User;
import com.example.hbeulibrary.Presenter.IUserPresenter;
import com.example.hbeulibrary.Presenter.Impl.UserPresenterImpl;
import com.example.hbeulibrary.R;
import com.example.hbeulibrary.Util.BaseActivity;
import com.example.hbeulibrary.View.ILoginView;
import com.example.hbeulibrary.View.MainActivity;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;

import org.litepal.LitePal;


public class loginActivity extends BaseActivity implements ILoginView,View.OnClickListener {
    private AutoCompleteTextView textAccount;
    private EditText textPassword;
    private CheckBox checkPwd;
    private QMUIRoundButton btnLogin;
    private TextView forgetPwd;
    private TextView skipLogin;

    private String userAccount;
    private String userPassword;

    private int mCurrentDialogStyle = com.qmuiteam.qmui.R.style.QMUI_Dialog;
    private QMUITipDialog tipDialog;
    IUserPresenter userPresenter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //find view
        textAccount = (AutoCompleteTextView) findViewById(R.id.account);
        textPassword = (EditText) findViewById(R.id.password);
        checkPwd = (CheckBox) findViewById(R.id.check_pwd);
        btnLogin = (QMUIRoundButton) findViewById(R.id.btn_login);
        forgetPwd = (TextView) findViewById(R.id.forget_pwd);
        skipLogin = (TextView) findViewById(R.id.skip_login);
        checkPwd.setChecked(true);

        //set listener
        btnLogin.setOnClickListener(this);
        forgetPwd.setOnClickListener(this);
        skipLogin.setOnClickListener(this);

        //init
        initDB();
        tipDialog = new QMUITipDialog.Builder(this)
                .setIconType(QMUITipDialog.Builder.ICON_TYPE_LOADING)
                .setTipWord("正在加载")
                .create();
        userPresenter = new UserPresenterImpl(this);
        setLoginData();


    }

    /**
     * 登录检查
     * */
    private void checkLogin(){
        userAccount = textAccount.getText().toString();
        userPassword = textPassword.getText().toString();
        //请求登录
        userPresenter.requestLogin(userAccount,userPassword);
        //保存用户登录信息
        userPresenter.saveLoginData(userAccount,userPassword,checkPwd.isChecked(),this);
    }


    //正在加载提示框
    @Override
    public void showDialog() {
        if (null != tipDialog && !tipDialog.isShowing()) {
            tipDialog.show();
        }
    }
    //取消正在加载提示框
    @Override
    public void dismissDialog() {
        if (null != tipDialog && tipDialog.isShowing()) {
            tipDialog.dismiss();
        }
    }

    //登录成功
    @Override
    public void loginSuccess(String result) {
        Toast.makeText(this,result,Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     * 用户登录失败弹出框
     * */
    @Override
    public void loginFailed(String msg) {
        //调用QMUI的消息框
        new QMUIDialog.MessageDialogBuilder(this)
                .setTitle("登录失败")
                .setMessage(msg)
                .addAction("确定", new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {
                        dialog.dismiss();
                    }
                })
                .create(mCurrentDialogStyle)
                .show();
    }

    // 写入 账号密码
    private void setLoginData(){
        String[] strings = userPresenter.readLoginData(this);
        textAccount.setText(strings[0]);
        textPassword.setText(strings[1]);
    }

    // 初始化数据库
    private void initDB(){
        //调用LitePal的getDatabase()方法，数据库自动创建
        LitePal.getDatabase();
        if (LitePal.findFirst(Book.class) == null) {
            InitDB.initDB();
        }
    }

    //跳转至找回密码界面
    private void goForget(){
        Intent intent = new Intent(this,ForgetPwdActivity.class);
        startActivity(intent);
    }

    //跳转至 主界面
    private void skipLogin(){
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                checkLogin();
                break;
            case R.id.forget_pwd:
                goForget();
                break;
            case R.id.skip_login:
                skipLogin();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
