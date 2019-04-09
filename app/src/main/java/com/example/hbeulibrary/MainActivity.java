package com.example.hbeulibrary;

import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.hbeulibrary.Adapter.ViewPagerAdapter;
import com.example.hbeulibrary.DB.Book;
import com.example.hbeulibrary.DB.InitDB;

import org.litepal.LitePal;

public class MainActivity extends AppCompatActivity
        implements BottomNavigationView.OnNavigationItemSelectedListener {


    private ViewPager mViewPager;
    private BottomNavigationView mBottomNavigationView;
    private TextView mTitleTextView;
    private ImageView searchButton;
    private ImageView editButton;
    private ImageView lendButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle();   //设置沉浸式标题栏
        initView();  //初始化空间
        initListener();   //初始化监听器


    }

    private void initView(){
        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        mBottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_nav);
        mTitleTextView = (TextView) findViewById(R.id.title_text_view);
        searchButton = (ImageView) findViewById(R.id.title_search_button);
        editButton = (ImageView) findViewById(R.id.title_edit_button);
        lendButton = (ImageView) findViewById(R.id.title_lend_button);
    }

    private void initListener(){
        mBottomNavigationView.setOnNavigationItemSelectedListener(this);
        //系统默认选中第一个,但是系统选中第一个不执行onNavigationItemSelected(MenuItem)方法,
        // 如果要求刚进入页面就执行clickTabOne()方法,则手动调用选中第一个
        mBottomNavigationView.setSelectedItemId(R.id.bottom_nav_search);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //ViewPager和BottomNaviationView联动,当ViewPager的某个页面被选中了,
                // 同时设置BottomNaviationView对应的tab按钮被选中
                switch (position){
                    case 0:
                        mBottomNavigationView.setSelectedItemId(R.id.bottom_nav_search);
                        break;
                    case 1:
                        mBottomNavigationView.setSelectedItemId(R.id.bottom_nav_book);
                        break;
                    case 2:
                        mBottomNavigationView.setSelectedItemId(R.id.bottom_nav_thinks);
                        break;
                    case 3:
                        mBottomNavigationView.setSelectedItemId(R.id.bottom_nav_my);
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int position) {
            }
        });
        //为Viewpager 设置adapter
        mViewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager()));
    }


    private void clickTabOne(){
        //为防止隔页切换时，滑过中间页面的问题，去除页面切换缓慢滑动的动画效果
        //mViewPager.setCurrentItem(0,false);
        mTitleTextView.setText("发现");
        searchButton.setVisibility(View.VISIBLE);
        editButton.setVisibility(View.GONE);
        lendButton.setVisibility(View.GONE);

    }

    private void clickTabTwo(){
        //mViewPager.setCurrentItem(1,false);
        mTitleTextView.setText("收藏");
        searchButton.setVisibility(View.GONE);
        lendButton.setVisibility(View.GONE);
        editButton.setVisibility(View.VISIBLE);
    }

    private void clickTabThree(){
        //mViewPager.setCurrentItem(2,false);
        mTitleTextView.setText("借阅");
        editButton.setVisibility(View.GONE);
        lendButton.setVisibility(View.VISIBLE);
    }

    private void clickFour(){
        //mViewPager.setCurrentItem(3,false);
        mTitleTextView.setText("我");
        lendButton.setVisibility(View.GONE);
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



    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        //BottomNaviationView和ViewPager联动,当BottomNavigationView
        // 的某个tab按钮被选中了,同时设置ViewPager对应的页面被选中
        int itemId = item.getItemId();
        switch (itemId){
            case R.id.bottom_nav_search:
                clickTabOne();
                return true;
            case R.id.bottom_nav_book:
                clickTabTwo();
                return true;
            case R.id.bottom_nav_thinks:
                clickTabThree();
                return true;
            case R.id.bottom_nav_my:
                clickFour();
                return true;
            default:
                break;
        }
        return false;
    }
}
