package com.example.hbeulibrary;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.hbeulibrary.DB.User;
import com.example.hbeulibrary.Util.ActivityCollector;
import com.example.hbeulibrary.Util.BaseActivity;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;

public class RePwdActivity extends BaseActivity {
    private Toolbar toolbar;
    private EditText textPwd;
    private EditText textRePwd;
    private QMUIRoundButton btnSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_re_pwd);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        textPwd = (EditText) findViewById(R.id.password);
        textRePwd = (EditText) findViewById(R.id.re_password);
        btnSet = (QMUIRoundButton) findViewById(R.id.btn_set);

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
                checkPwd();
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


    /**
     * 判断两次密码是否输入一致
     * 确认后，跳转至登录界面重新登录
     * */
    private void checkPwd(){
        String pwd = textPwd.getText().toString();
        String rePwd = textRePwd.getText().toString();
        if (pwd.equals("") || rePwd.equals("")) {
            Toast.makeText(this, "输入内容为空", Toast.LENGTH_SHORT).show();
        } else if (pwd.equals(rePwd)) {
            SharedPreferences pref = getSharedPreferences("user_login", MODE_PRIVATE);
            String userAccount = pref.getString("account", "");
            User user = new User();
            user.setPassword(pwd);
            user.updateAll("account = ?", userAccount);
            // 跳转至 loginActivity
            Intent intent = new Intent(this, loginActivity.class);
            startActivity(intent);
            Toast.makeText(this,"请重新登录",Toast.LENGTH_SHORT).show();
            ActivityCollector.finishAll();  //销毁 所有Activity
        } else {
            Toast.makeText(this,"两次密码输入的不一致",Toast.LENGTH_SHORT).show();
        }
    }
}
