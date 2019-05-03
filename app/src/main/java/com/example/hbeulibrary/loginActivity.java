package com.example.hbeulibrary;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hbeulibrary.DB.Book;
import com.example.hbeulibrary.DB.InitDB;
import com.example.hbeulibrary.DB.User;
import com.example.hbeulibrary.Util.BaseActivity;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

import static android.widget.Toast.LENGTH_SHORT;

public class loginActivity extends BaseActivity implements View.OnClickListener {
    private AutoCompleteTextView textAccount;
    private EditText textPassword;
    private CheckBox checkPwd;
    private QMUIRoundButton btnLogin;
    private TextView forgetPwd;
    private TextView signIN;

    private List<User> userList = new ArrayList<>();

    private String userAccount;
    private String userPassword;

    private int mCurrentDialogStyle = com.qmuiteam.qmui.R.style.QMUI_Dialog;
    private QMUITipDialog tipDialog;

    private boolean isEmpty = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        textAccount = (AutoCompleteTextView) findViewById(R.id.account);
        textPassword = (EditText) findViewById(R.id.password);
        checkPwd = (CheckBox) findViewById(R.id.check_pwd);
        btnLogin = (QMUIRoundButton) findViewById(R.id.btn_login);
        forgetPwd = (TextView) findViewById(R.id.forget_pwd);
        signIN = (TextView) findViewById(R.id.sign_in);
        checkPwd.setChecked(true);
        readPaw();
        initListener();
        initDB();


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                checkLogin();
                break;
            case R.id.forget_pwd:
                //Toast.makeText(this,"忘记密码",Toast.LENGTH_SHORT).show();
                goFoget();
                break;
            case R.id.sign_in:
                //Toast.makeText(this,"申请注册",Toast.LENGTH_SHORT).show();
                skipLogin();
                break;
            default:
                break;
        }
    }

    //初始化 点击事件监听
    private void initListener(){

        btnLogin.setOnClickListener(this);
        forgetPwd.setOnClickListener(this);
        signIN.setOnClickListener(this);
    }

    /**
     * 登录检查
     * */
    private void checkLogin(){

        userAccount = textAccount.getText().toString();
        userPassword = textPassword.getText().toString();
        userList = LitePal.where("account = ?",userAccount).find(User.class);
        if (userList.isEmpty()) {//如果 没有该账号
            loginFailed();
        } else if (!userList.get(0).getPassword().equals(userPassword)) {  //如果密码填写错误
            loginFailed();
        } else {
            savePaw();
            //跳转至 主界面
            //加载动画
            tipDialog = new QMUITipDialog.Builder(this)
                    .setIconType(QMUITipDialog.Builder.ICON_TYPE_LOADING)
                    .setTipWord("正在加载")
                    .create();
            tipDialog.show();
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
            Toast.makeText(this,"登录成功",Toast.LENGTH_SHORT).show();
            finish();  //销毁登录界面
        }
    }

    /**
     * 用户登录失败
     * */
    private void loginFailed(){
        //调用QMUI的消息框
        new QMUIDialog.MessageDialogBuilder(this)
                .setTitle("登录失败")
                .setMessage("账号或或密码错误")
                .addAction("确定", new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {
                        dialog.dismiss();
                    }
                })
                .create(mCurrentDialogStyle).show();
    }

    /**
     * 保存 密码信息
     * */
    private void savePaw(){
        if (checkPwd.isChecked()) {  //如果用户勾选了 记住密码 ，则保存账号、密码到  pref 中
            SharedPreferences.Editor editor = getSharedPreferences("user_login", MODE_PRIVATE).edit();
            editor.putString("account", userAccount);
            editor.putString("password", userPassword);
            editor.apply();
        } else {    //如果用户取消勾选，则清空 pref
            SharedPreferences.Editor editor = getSharedPreferences("user_login",MODE_PRIVATE).edit();
            editor.clear();
            editor.apply();
        }
    }
    /**
     * 从pref 读取账号密码信息
     * */
    private void readPaw(){
        SharedPreferences pref = getSharedPreferences("user_login", MODE_PRIVATE);
        textAccount.setText(pref.getString("account", ""));
        textPassword.setText(pref.getString("password",""));
    }

    private void initDB(){
        //调用LitePal的getDatabase()方法，数据库自动创建
        LitePal.getDatabase();
        if (LitePal.findFirst(Book.class) == null) {
            InitDB.initDB();
        }
    }

    private void goFoget(){
        Intent intent = new Intent(this,ForgetPwdActivity.class);
        startActivity(intent);
    }

    private void goRepwd(){
        Intent intent = new Intent(this,RegisterActivity.class);
        startActivity(intent);
    }

    private void skipLogin(){
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }
}
