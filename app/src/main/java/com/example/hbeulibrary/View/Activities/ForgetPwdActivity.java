package com.example.hbeulibrary.View.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.example.hbeulibrary.DB.User;
import com.example.hbeulibrary.R;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;

import org.litepal.LitePal;

import java.util.List;

public class ForgetPwdActivity extends AppCompatActivity{
    private Toolbar toolbar;
    private EditText textAccount;
    private EditText textRePwd;
    private QMUIRoundButton btnSet;
    private TextInputLayout tilAccount;
    private TextInputLayout tilPassword;
    private String account;
    private String password;

    private int mCurrentDialogStyle = com.qmuiteam.qmui.R.style.QMUI_Dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgetpwd);

        textAccount = (EditText) findViewById(R.id.account);
        textRePwd = (EditText) findViewById(R.id.re_password);
        btnSet = (QMUIRoundButton) findViewById(R.id.btn_set);
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

        btnSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check();
            }
        });
        tilAccount.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (account.equals("")) {
                    tilAccount.setError("账号不能为空");
                    tilAccount.setErrorEnabled(true);

                } else {
                    tilAccount.setErrorEnabled(false);
                }
            }
        });
        tilPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (password.equals("")) {
                    tilPassword.setError("密码不能为空");
                    tilPassword.setErrorEnabled(true);

                } else {
                    tilPassword.setErrorEnabled(false);
                }
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

    private void check(){
        account = textAccount.getText().toString();
        password = textRePwd.getText().toString();
        List<User> userList = LitePal.where("account = ?", account).find(User.class);
        if (userList.isEmpty()) {   //账号不存在
            setFailed();
        } else {
            User userToUpdate = new User();
            userToUpdate.setPassword(password);
            userToUpdate.updateAll("account = ?", account);
            QMUITipDialog tipDialog1 = new QMUITipDialog.Builder(ForgetPwdActivity.this)
                    .setIconType(QMUITipDialog.Builder.ICON_TYPE_SUCCESS)
                    .setTipWord("修改成功")
                    .create();
            tipDialog1.show();
            tipDialog1.dismiss();
            Intent intent = new Intent(ForgetPwdActivity.this, loginActivity.class);
            startActivity(intent);
        }
    }


    //修改失败
    private void setFailed() {
        //调用QMUI的消息框
        new QMUIDialog.MessageDialogBuilder(this)
                .setTitle("操作失败")
                .setMessage("账号不存在")
                .addAction("确定", new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {
                        dialog.dismiss();
                    }
                })
                .create(mCurrentDialogStyle).show();
    }
}