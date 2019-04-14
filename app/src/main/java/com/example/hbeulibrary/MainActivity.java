package com.example.hbeulibrary;

import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;


import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hbeulibrary.Util.BaseActivity;
import com.example.hbeulibrary.Util.BottomNavigationViewHelper;
import com.example.hbeulibrary.fragments.CollectFragment;
import com.example.hbeulibrary.fragments.LendFragment;
import com.example.hbeulibrary.fragments.MyFragment;
import com.example.hbeulibrary.fragments.SearchFragment;

import org.litepal.LitePal;

public class MainActivity extends BaseActivity
        implements BottomNavigationView.OnNavigationItemSelectedListener {

    private BottomNavigationView mBottomNavigationView;
    private TextView mTitleTextView;
    private ImageView searchButton;
    private ImageView editButton;
    private ImageView lendButton;
    private FrameLayout frameLayout;
    private Fragment[] mFragments;
    private int lastShowFragment = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle();   //设置沉浸式标题栏
        initView();  //初始化空间
        initListener();   //初始化监听器
        initFragment();   // 初始化Fragments


    }

    private void initView(){
        mBottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_nav);
        mTitleTextView = (TextView) findViewById(R.id.title_text_view);
        searchButton = (ImageView) findViewById(R.id.title_search_button);
        editButton = (ImageView) findViewById(R.id.title_edit_button);
        lendButton = (ImageView) findViewById(R.id.title_lend_button);
        frameLayout = (FrameLayout) findViewById(R.id.frag_content);

        BottomNavigationViewHelper.disableShiftMode(mBottomNavigationView);
    }

    private void initListener(){
        mBottomNavigationView.setOnNavigationItemSelectedListener(this);
        //系统默认选中第一个,但是系统选中第一个不执行onNavigationItemSelected(MenuItem)方法,
        // 如果要求刚进入页面就执行clickTabOne()方法,则手动调用选中第一个
        mBottomNavigationView.setSelectedItemId(R.id.bottom_nav_search);
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
                clickTabFour();
                return true;
            default:
                break;
        }
        return false;
    }


    // 点击 search
    private void clickTabOne(){
        mTitleTextView.setText("发现");
        searchButton.setVisibility(View.VISIBLE);
        editButton.setVisibility(View.GONE);
        lendButton.setVisibility(View.GONE);
        if (lastShowFragment != 0) {
            switchFragment(lastShowFragment,0);
            lastShowFragment =0;
        }

    }
    // 点击 collect
    private void clickTabTwo(){

        mTitleTextView.setText("收藏");
        searchButton.setVisibility(View.GONE);
        lendButton.setVisibility(View.GONE);
        editButton.setVisibility(View.VISIBLE);
        if (lastShowFragment != 1) {
            switchFragment(lastShowFragment,1);
            lastShowFragment = 1;
        }
    }
    // 点击 lend
    private void clickTabThree(){
        mTitleTextView.setText("借阅");
        editButton.setVisibility(View.GONE);
        lendButton.setVisibility(View.VISIBLE);
        searchButton.setVisibility(View.GONE);
        if (lastShowFragment != 2) {
            switchFragment(lastShowFragment,2);
            lastShowFragment = 2;
        }
    }
    // 点击  my
    private void clickTabFour(){
        mTitleTextView.setText("我");
        lendButton.setVisibility(View.GONE);
        editButton.setVisibility(View.GONE);
        searchButton.setVisibility(View.GONE);
        if (lastShowFragment != 3) {
            switchFragment(lastShowFragment,3);
            lastShowFragment = 3;
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


    /**
     *  初始化
     *
     * */
    private void initFragment(){
        //实例 各 Fragment
        SearchFragment searchFragment = new SearchFragment();
        CollectFragment collectFragment = new CollectFragment();
        LendFragment lendFragment = new LendFragment();
        MyFragment myFragment = new MyFragment();
        mFragments = new Fragment[]{searchFragment,collectFragment,
                lendFragment,myFragment};
        lastShowFragment = 0;
        //默认选中第一个
        FragmentTransaction fm = getSupportFragmentManager().beginTransaction();
                fm.add(R.id.frag_content,searchFragment)
                        .add(R.id.frag_content,collectFragment)
                        .add(R.id.frag_content,lendFragment)
                        .add(R.id.frag_content,myFragment)
                        .hide(collectFragment)
                        .hide(lendFragment)
                        .hide(myFragment)
                        .show(searchFragment)
                        .commit();
    }


    /**
     *  切换 Fragment
     *
     * @param lastIndex 上个 fragment的索引
     * @param index      需要显示 fragment 的索引
     * */

    public void switchFragment(int lastIndex,int index) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.hide(mFragments[lastIndex]);
        /*if (!mFragments[index].isAdded()) {
            transaction.add(R.id.frag_content,mFragments[index]);
        }*/
        transaction.show(mFragments[index]).commit();
    }

}
