package com.example.hbeulibrary;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.hbeulibrary.DB.Book;
import com.example.hbeulibrary.DB.Lend;
import com.example.hbeulibrary.fragments.CollectFragment;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;

import org.litepal.LitePal;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class BookMassageActivity extends AppCompatActivity implements View.OnClickListener {

    final String BOOK_NAME = "book_name";
    final String BOOK_IMAGEID = "book_imageid";
    private boolean fabChecked = false;
    private boolean isLended = false;
    SharedPreferences.Editor editor;

    private Toolbar toolbar;
    private CollapsingToolbarLayout collapsingToolbar;
    private ImageView bookImage;
    private FloatingActionButton fabCollect;
    private Button lendBtn;
    private ListView mListView;
    private Book book;
    private List<Lend> lendList = new ArrayList<>();


    private int mCurrentDialogStyle = com.qmuiteam.qmui.R.style.QMUI_Dialog;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_mssage);

        //获得book对象
        book = (Book)getIntent().getSerializableExtra("book_data");
        initView();   //初始化各控件
        fabCollect.setOnClickListener(this);
        lendBtn.setOnClickListener(this);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            //将Toolbar的导航按钮显示出来
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        initBookMessage();  //加载书目信息

        //判断 该书籍 是否已收藏
        SharedPreferences pref = getSharedPreferences("user1collect",MODE_PRIVATE);
        int getId = pref.getInt("book"+book.getId(),-1);
        if (book.getId() == getId) {
            fabCollect.setImageResource(R.drawable.ic_book_heart_checked);
            fabChecked = true;
        }
        //判断该 书籍 是否已借阅
        String id = Integer.toString(book.getId());
        lendList = LitePal.select("bookId").where("bookId = ?",id)
                .find(Lend.class);
        if(!lendList.isEmpty()){
            lendBtn.setText("已借阅");
            isLended = true;

        }

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

    //初始化控件
    private void initView(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        bookImage = (ImageView) findViewById(R.id.book_massge_top_img);
        fabCollect = (FloatingActionButton) findViewById(R.id.fab_collect);
        mListView = (ListView) findViewById(R.id.book_message_listview);
        lendBtn = (Button) findViewById(R.id.lend_book);


    }

    /**
     * 点击事件监听
     * */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fab_collect:
                editor = getSharedPreferences("user1collect",MODE_PRIVATE).edit();
                if (!fabChecked) {
                    //调用QMUI的消息框
                    new QMUIDialog.MessageDialogBuilder(this)
                            .setTitle("hello~")
                            .setMessage("确定要收藏吗？")
                            .addAction("取消", new QMUIDialogAction.ActionListener() {
                                @Override
                                public void onClick(QMUIDialog dialog, int index) {
                                    dialog.dismiss();
                                }
                            })
                            .addAction("确定", new QMUIDialogAction.ActionListener() {
                                @Override
                                public void onClick(QMUIDialog dialog, int index) {
                                    //收藏成功后，更换fab的图片
                                    addCollect();
                                    fabCollect.setImageResource(R.drawable.ic_book_heart_checked);
                                    dialog.dismiss();
                                    Toast.makeText(BookMassageActivity.this, "收藏成功", Toast.LENGTH_SHORT).show();
                                    fabChecked = true;
                                }
                            })
                            .create(mCurrentDialogStyle).show();
                } else {
                    //调用QMUI的消息框
                    new QMUIDialog.MessageDialogBuilder(this)
                            .setTitle("hello~")
                            .setMessage("确定要取消收藏吗？")
                            .addAction("取消", new QMUIDialogAction.ActionListener() {
                                @Override
                                public void onClick(QMUIDialog dialog, int index) {
                                    dialog.dismiss();
                                }
                            })
                            .addAction("确定", new QMUIDialogAction.ActionListener() {
                                @Override
                                public void onClick(QMUIDialog dialog, int index) {
                                    //取消收藏成功后，跟换fab的图片
                                    removeCollect();
                                    fabCollect.setImageResource(R.drawable.ic_book_heart);
                                    dialog.dismiss();
                                    Toast.makeText(BookMassageActivity.this, "取消成功", Toast.LENGTH_SHORT).show();
                                    fabChecked = false;
                                }
                            })
                            .create(mCurrentDialogStyle).show();
                }
                break;
            case R.id.lend_book:

                if (isLended) {
                    //调用QMUI的消息框
                    new QMUIDialog.MessageDialogBuilder(this)
                            .setTitle("操作失败")
                            .setMessage("您已借阅了哦~")
                            .addAction("确定", new QMUIDialogAction.ActionListener() {
                                @Override
                                public void onClick(QMUIDialog dialog, int index) {
                                    dialog.dismiss();
                                }
                            })
                            .create(mCurrentDialogStyle).show();
                } else {
                    //调用QMUI的消息框
                    new QMUIDialog.MessageDialogBuilder(this)
                            .setTitle("hello~")
                            .setMessage("确定要借阅吗？")
                            .addAction("取消", new QMUIDialogAction.ActionListener() {
                                @Override
                                public void onClick(QMUIDialog dialog, int index) {
                                    dialog.dismiss();
                                }
                            })
                            .addAction("确定", new QMUIDialogAction.ActionListener() {
                                @Override
                                public void onClick(QMUIDialog dialog, int index) {
                                    lendBook();
                                    dialog.dismiss();
                                    lendBtn.setText("已借阅");
                                    isLended = true;
                                    Toast.makeText(BookMassageActivity.this, "借阅成功", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .create(mCurrentDialogStyle).show();

                }
            default:
                break;
        }
    }
    //加载 book 信息
    private void initBookMessage(){
        //将书名名设置为当前标题
        collapsingToolbar.setTitle(book.getBookName());
        //使用Glide加载传入的图片，并设置到标题栏上的ImageView内
        Glide.with(this).load(book.getBookImageId())
                .override(120,160)
                .into(bookImage);

        String[] listItems = new String[]{
                "作者：" + book.getBookAuthor(),
                "出版社：" + book.getBookPublish(),
                "出版时间：" + book.getBookPublishTime(),
                "页数：" + book.getPages(),
                "价格：" + book.getPrice(),
                "装帧：" + book.getLayout(),
                "IBSN：" + book.getBookIBSN(),
                "可借数量：" + book.getNumber(),
                "简介：" + book.getInfo()
        };
        List<String> data = new ArrayList<>();
        //Log.d("TAG",data.get(3));
        Collections.addAll(data,listItems);
        mListView.setAdapter(new ArrayAdapter<>(this,R.layout.book_list_item,data));
    }

    //收藏图书，将 userid 和 bookid 用 SharedPreferences方式存储
    private void addCollect() {
        editor.putInt("userid",1);
        editor.putInt("book"+book.getId(),book.getId());
        editor.apply();

    }
    private void removeCollect(){
        editor.remove("book"+book.getId());
        editor.apply();
    }

    //借书操作，将相应数据存入 Lend 表
    private void lendBook(){
        Lend lend = new Lend();
        Date mDate = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd/hh:mm E");
        String time = sdf.format(mDate);
        lend.setUserId(1);
        lend.setBookId(book.getId());
        lend.setBookName(book.getBookName());
        lend.setBookAuthor(book.getBookAuthor());
        lend.setLendTime(time);
        lend.setStatus("已借");
        lend.save();
    }
}
