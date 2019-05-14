package com.example.hbeulibrary.View.Activities;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.hbeulibrary.DB.User;
import com.example.hbeulibrary.R;
import com.example.hbeulibrary.Util.ActivityCollector;
import com.example.hbeulibrary.Util.BaseActivity;
import com.example.hbeulibrary.View.MainActivity;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;

import org.litepal.LitePal;

import java.util.List;

public class RegisterActivity extends BaseActivity {
    private Toolbar toolbar;
    private EditText textAccount;
    private EditText textPassword;
    private QMUIRoundButton btnLogin;
    private TextInputLayout tilAccount;
    private TextInputLayout tilPassword;

    private String account;
    private String pwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        textAccount = (EditText) findViewById(R.id.account);
        textPassword = (EditText) findViewById(R.id.password);
        btnLogin = (QMUIRoundButton) findViewById(R.id.btn_login);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        tilAccount = (TextInputLayout) findViewById(R.id.til_account);
        tilPassword = (TextInputLayout) findViewById(R.id.til_password);

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            //将Toolbar的导航按钮显示出来
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("");
        }


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkLogin();
            }
        });
        tilAccount.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                
            }
        });
    }

    //处理HomeAsUp按钮的点击事件，放回上一个Activity
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void checkLogin(){
        account = textAccount.getText().toString();
        pwd = textPassword.getText().toString();
        List<User> userList = LitePal.where("account = ?", account).find(User.class);
        if (userList.isEmpty()) {  //判断 账号是否已经注册过
            User user = new User();
            user.setAccount(account);
            user.setPassword(pwd);
            user.save();
            Toast.makeText(RegisterActivity.this,"注册成功",Toast.LENGTH_SHORT).show();
            final QMUITipDialog tipDialog2 = new QMUITipDialog.Builder(RegisterActivity.this)
                    .setIconType(QMUITipDialog.Builder.ICON_TYPE_LOADING)
                    .setTipWord("正在加载")
                    .create();
            tipDialog2.show();
            //延缓界面跳转进度
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try{
                        Thread.sleep(1000);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tipDialog2.dismiss();
                            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                            startActivity(intent);
                            ActivityCollector.finishAll();
                        }
                    });
                }
            }).start();
        } else {
            tilAccount.setError("账号已存在");
            tilAccount.setEnabled(true);
        }


    }
}
