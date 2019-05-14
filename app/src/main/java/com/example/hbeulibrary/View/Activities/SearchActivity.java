package com.example.hbeulibrary.View.Activities;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.hbeulibrary.Adapter.BookAdapter;
import com.example.hbeulibrary.DB.Book;
import com.example.hbeulibrary.R;
import com.example.hbeulibrary.Util.ItemDivider;
import com.example.hbeulibrary.Util.Search_EditText;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageView backImageView;
    private LinearLayout ll2;
    private Search_EditText search_editText;
    private TextView nullText;
    private RecyclerView recyclerView;

    private BookAdapter adapter;

    private List<Book> bookList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        setTitle();

        backImageView = (ImageView) findViewById(R.id.back);
        ll2 = (LinearLayout) findViewById(R.id.ll2);
        search_editText = (Search_EditText) findViewById(R.id.ed_search);
        nullText = (TextView) findViewById(R.id.text_null);
        recyclerView = (RecyclerView) findViewById(R.id.search_recycler_view);

        backImageView.setOnClickListener(this);
        search_editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    //隐藏软键盘
                    ((InputMethodManager)search_editText.getContext().getSystemService(INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                                    InputMethodManager.HIDE_NOT_ALWAYS);
                    String str_search = search_editText.getText().toString();
                    bookList = LitePal.where("bookName like ?", "%" + str_search + "%")
                            .find(Book.class);
                    if (bookList.isEmpty()) {
                        bookList.clear();
                        bookList = LitePal.where("bookAuthor like ?", "%" + str_search + "%")
                                .find(Book.class);
                        if (bookList.isEmpty()) {
                            bookList.clear();
                            bookList = LitePal.where("bookIBSN like ?", "%" + str_search + "%")
                                    .find(Book.class);
                            if (bookList.isEmpty()) {
                                ll2.setVisibility(View.GONE);
                                recyclerView.setVisibility(View.GONE);
                                nullText.setVisibility(View.VISIBLE);
                            } else {
                                setAdapter(bookList);
                            }
                        } else {
                            setAdapter(bookList);
                        }
                    } else {
                        setAdapter(bookList);
                    }

                }
                return false;
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            default:
                break;
        }
    }


    /**
     * 实现背景图和状态栏融合一起的效果
     * */
    private void setTitle(){
        //修改系统状态栏颜色
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        }
    }

    //封装 加载RecycleView 的操作
    private void setAdapter(List<Book> bookList) {
        ll2.setVisibility(View.GONE);
        nullText.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
        LinearLayoutManager layoutManager = new LinearLayoutManager(SearchActivity.this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerView.setLayoutManager(layoutManager);
        //设置分隔线
        recyclerView.addItemDecoration(new ItemDivider()
                .setDividerWith(1).setDividerColor(R.color.colorPrimary));
        adapter = new BookAdapter(bookList);
        recyclerView.setAdapter(adapter);
    }
}
