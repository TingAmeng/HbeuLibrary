package com.example.hbeulibrary.View.Activities;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.hbeulibrary.DB.Book;
import com.example.hbeulibrary.DB.Lend;
import com.example.hbeulibrary.Model.Impl.LendModelImpl;
import com.example.hbeulibrary.Presenter.IBookPresenter;
import com.example.hbeulibrary.Presenter.ILendPresenter;
import com.example.hbeulibrary.Presenter.Impl.BookPresenterImpl;
import com.example.hbeulibrary.Presenter.Impl.LendPresenterImpl;
import com.example.hbeulibrary.R;
import com.example.hbeulibrary.Util.MyApplication;
import com.example.hbeulibrary.View.Fragments.CollectFragment;
import com.example.hbeulibrary.View.IBookMessageView;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;

import org.litepal.LitePal;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class BookMassageActivity extends AppCompatActivity implements View.OnClickListener, IBookMessageView{

    private boolean fabChecked = false;
    private boolean isLended = false;

    private int userId;
    private int bookId;

    private Toolbar toolbar;
    private CollapsingToolbarLayout collapsingToolbar;
    private ImageView bookImage;
    private FloatingActionButton fabCollect;
    private Button lendBtn;
    private TextView numberView;
    private ListView mListView;
    private ListAdapter adapter;
    private Book book;
    private List<Lend> lendList = new ArrayList<>();

    IBookPresenter bookPresenter;
    ILendPresenter lendPresenter;


    private int mCurrentDialogStyle = com.qmuiteam.qmui.R.style.QMUI_Dialog;
    private QMUITipDialog tipDialog;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_mssage);

        initView();   //初始化各控件
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();


        if(actionBar != null){
            //将Toolbar的导航按钮显示出来
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        //listener
        fabCollect.setOnClickListener(this);
        lendBtn.setOnClickListener(this);

        //init
        //获得 bookId
        bookId = getIntent().getIntExtra("bookid",-1);
        //获得book
        book = LitePal.where("id = ?",Integer.toString(bookId)).find(Book.class).get(0);
        //实例化 presenter
        bookPresenter = new BookPresenterImpl(this);
        lendPresenter = new LendPresenterImpl(this);

        bookPresenter.showBookMessage(book);

        //该书籍是否已经收藏
        bookPresenter.isCollected(this,bookId);
        //该书籍是否已借阅
        String id = Integer.toString(bookId);
        lendList = LitePal.select("bookId").where("bookId = ?",id)
                .find(Lend.class);
        if(!lendList.isEmpty()){
            lendBtn.setText("已借阅");
            isLended = true;

        }

        //将书名名设置为当前标题
        collapsingToolbar.setTitle(book.getBookName());
        //使用Glide加载传入的图片，并设置到标题栏上的ImageView内
        Glide.with(this).load(book.getBookImageId())
                .override(120,160)
                .into(bookImage);

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
                if (!fabChecked) {
                    bookPresenter.addCollect(this,book.getId());

                } else {
                    bookPresenter.removeCollect(this,book.getId());
                }
                break;
            case R.id.lend_book:

                if (isLended) {
                    //调用QMUI的消息框
                    new QMUIDialog.MessageDialogBuilder(this)
                            .setTitle("操作失败")
                            .setMessage("您已借阅了哦~")
                            .setCancelable(false)
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
                            .setCancelable(false)
                            .addAction("取消", new QMUIDialogAction.ActionListener() {
                                @Override
                                public void onClick(QMUIDialog dialog, int index) {
                                    dialog.dismiss();
                                }
                            })
                            .addAction("确定", new QMUIDialogAction.ActionListener() {
                                @Override
                                public void onClick(QMUIDialog dialog, int index) {
                                    lendPresenter.addLend(book);
                                    dialog.dismiss();
                                }
                            })
                            .create(mCurrentDialogStyle).show();

                }
            default:
                break;
        }
    }


    @Override
    public void showBookMessage(String[] listItems) {
        List<String> data = new ArrayList<>();
        //Log.d("TAG",data.get(3));
        Collections.addAll(data,listItems);
        adapter = new ArrayAdapter<>(this,R.layout.book_list_item,data);
        mListView.setAdapter(adapter);
    }

    @Override
    public void collectSuccess(String msg) {
        Toast.makeText(BookMassageActivity.this, msg, Toast.LENGTH_SHORT).show();
        //更改 fab 的样式
        fabCollect.setImageResource(R.drawable.ic_book_heart_checked);
        fabChecked = true;
    }

    @Override
    public void removeSuccess(String msg) {
        Toast.makeText(BookMassageActivity.this, msg, Toast.LENGTH_SHORT).show();
        //更改 fab 的样式
        fabCollect.setImageResource(R.drawable.ic_book_heart);
        fabChecked = false;
    }

    @Override
    public void LendSuccess() {
        lendBtn.setText("已借阅");
        isLended = true;
        Toast.makeText(BookMassageActivity.this, "借阅成功", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void LendFailed() {
        //调用QMUI的消息框
        new QMUIDialog.MessageDialogBuilder(this)
                .setTitle("操作失败")
                .setMessage("暂时没有书哦~")
                .setCancelable(false)
                .addAction("确定", new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {
                        dialog.dismiss();
                    }
                })
                .create(mCurrentDialogStyle).show();
    }

    @Override
    public void showLoadDialog() {
        tipDialog = new QMUITipDialog.Builder(this)
                .setIconType(QMUITipDialog.Builder.ICON_TYPE_LOADING)
                .setTipWord("正在加载")
                .create();
        if (null != tipDialog && !tipDialog.isShowing()) {
            tipDialog.show();
        }
    }

    @Override
    public void dismissLoadDialog() {
        if (null != tipDialog && tipDialog.isShowing()) {
            tipDialog.dismiss();
        }
    }

    @Override
    public void changeFab() {
        fabCollect.setImageResource(R.drawable.ic_book_heart_checked);
        fabChecked = true;
    }

    @Override
    public void updateLendNumbers(int number) {
        numberView =mListView.getChildAt(7).findViewById(R.id.book_list_item);
        numberView.setText("可借数量："+number);
    }

}
