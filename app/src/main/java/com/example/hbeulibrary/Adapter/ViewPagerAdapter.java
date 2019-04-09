package com.example.hbeulibrary.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.hbeulibrary.fragments.CollectFragment;
import com.example.hbeulibrary.fragments.MyFragment;
import com.example.hbeulibrary.fragments.SearchFragment;
import com.example.hbeulibrary.fragments.LendFragment;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    //由于页面已经固定，这里把Adapter 需要的fragment提前创建
    private Fragment[] mFragments = new Fragment[]
            {new SearchFragment(), new CollectFragment(),
                    new LendFragment(),new MyFragment()};

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments[position];
    }

    @Override
    public int getCount() {
        return 4;
    }
}
