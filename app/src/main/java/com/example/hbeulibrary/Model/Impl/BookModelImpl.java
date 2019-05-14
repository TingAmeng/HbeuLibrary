package com.example.hbeulibrary.Model.Impl;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.hbeulibrary.DB.Book;
import com.example.hbeulibrary.Model.IBookModel;
import com.example.hbeulibrary.Presenter.IBookPresenter;
import com.example.hbeulibrary.Presenter.ILendPresenter;
import com.example.hbeulibrary.Util.MyApplication;

import org.litepal.LitePal;

import java.util.List;

public class BookModelImpl implements IBookModel {

    private IBookPresenter bookPresenter;
    private ILendPresenter lendPresenter;

    int userId = MyApplication.getUserId();

    public BookModelImpl(IBookPresenter bookPresenter) {
        this.bookPresenter = bookPresenter;
    }

    public BookModelImpl(ILendPresenter lendPresenter) {
        this.lendPresenter = lendPresenter;
    }

    @Override
    public List<Book> getAllBook() {
        List<Book> bookList = LitePal.findAll(Book.class);
        return bookList;
    }

    @Override
    public Book getBook(int bookId) {

        return null;
    }

    @Override
    public String[] getBookMessage(Book book) {
        String[] bookMsg = new String[]{
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
        return bookMsg;
    }

    @Override
    public void addCollect(Context context,int bookId) {
        SharedPreferences.Editor editor = context.getSharedPreferences("user"+userId+"collect",Context.MODE_PRIVATE).edit();
        editor.putInt("book"+bookId,bookId);
        editor.apply();
    }

    @Override
    public void removeCollect(Context context,int bookId) {
        SharedPreferences.Editor editor = context.getSharedPreferences("user"+userId+"collect",Context.MODE_PRIVATE).edit();
        editor.remove("book"+bookId);
        editor.apply();
    }

    @Override
    public boolean isCollected(Context context,int bookId) {
        SharedPreferences pref = context.getSharedPreferences("user"+userId+"collect",Context.MODE_PRIVATE);
        int getId = pref.getInt("book"+bookId,-1);
        if (bookId == getId) {
            return true;
        } else {
            return false;
        }
    }
}
