package com.example.hbeulibrary.Presenter;

import android.content.Context;

import com.example.hbeulibrary.DB.Book;

import java.util.List;

public interface IBookPresenter {
    //获得全部书籍信息
    List<Book> getAllBook();
    //书籍信息展示
    void showBookMessage(Book book);
    //添加收藏
    void addCollect(Context context,int bookId);
    //移除收藏
    void removeCollect(Context context,int bookId);
    //判断是否已经收藏
    void isCollected(Context context,int bookId);
}
