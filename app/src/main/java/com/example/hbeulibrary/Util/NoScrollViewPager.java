package com.example.hbeulibrary.Util;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class NoScrollViewPager extends ViewPager {
    //private boolean noScroll = false;
    public NoScrollViewPager(@NonNull Context context) {
        super(context);
    }

    public NoScrollViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }


    /*public void setNoScroll(boolean noScroll){
        this.noScroll = noScroll;
    }
*/
    @Override
    public void scrollTo(int x, int y) {
        super.scrollTo(x, y);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        /*if (noScroll) {
            return false;
        } else {
            return super.onTouchEvent(ev);
        }*/
        return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        /*if (noScroll) {
            return false;
        } else {
            return super.onInterceptTouchEvent(ev);
        }*/
        return false;

    }

    @Override
    public void setCurrentItem(int item) {
        super.setCurrentItem(item);
    }

    @Override
    public void setCurrentItem(int item, boolean smoothScroll) {
        super.setCurrentItem(item, smoothScroll);
    }
}
