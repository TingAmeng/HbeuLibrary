package com.example.hbeulibrary;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.hbeulibrary.DB.Book;
import com.example.hbeulibrary.DB.InitDB;

import org.litepal.LitePal;

public class LauncherActivity extends AppCompatActivity {

    private boolean isEmpty = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);

        //调用LitePal的getDatabase()方法，数据库自动创建
        LitePal.getDatabase();
        if (LitePal.findFirst(Book.class) == null) {
            InitDB.initDB();
        }
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }
}
