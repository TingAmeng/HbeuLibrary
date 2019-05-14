package com.example.hbeulibrary.Model.Impl;

import com.example.hbeulibrary.DB.Book;
import com.example.hbeulibrary.DB.Lend;
import com.example.hbeulibrary.Model.ILendModel;
import com.example.hbeulibrary.Presenter.ILendPresenter;
import com.example.hbeulibrary.Util.MyApplication;

import org.litepal.LitePal;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class LendModelImpl implements ILendModel {

    private ILendPresenter iLendPresenter;

    int userId = MyApplication.getUserId();

    public LendModelImpl() {

    }

    public LendModelImpl(ILendPresenter iLendPresenter) {
        this.iLendPresenter = iLendPresenter;
    }

    @Override
    public boolean addLend(Book book) {
        if (book.getNumber() > 0) {

            //定义日期格式
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            //获得 当前日期
            Date mDate = new Date();
            mDate.toString();
            String nowDateStr = sdf.format(mDate);
            //日历对象
            Calendar rightNow = Calendar.getInstance();
            //设置日历 当前date
            rightNow.setTime(mDate);
            //加一个 月
            //rightNow.add(Calendar.MONTH,1);
            //加 3天
            rightNow.add(Calendar.DAY_OF_MONTH,5);
            //一个月之后的 date 对象
            Date returnDate = rightNow.getTime();
            String returnDateStr = sdf.format(returnDate);


            // 插入 Lend
            Lend lend = new Lend();
            lend.setUserId(userId);
            lend.setBookId(book.getId());
            lend.setWeight(0);
            lend.setBookName(book.getBookName());
            lend.setBookAuthor(book.getBookAuthor());
            lend.setLendDate(nowDateStr);
            lend.setReturnDate(returnDateStr);
            lend.setStatus("已借");
            lend.setLendTimes(1);
            lend.save();

            //更新 Book
            book.setNumber(book.getNumber()-1);
            book.save();

            return true;
        } else {
            return false;
        }

    }
}
