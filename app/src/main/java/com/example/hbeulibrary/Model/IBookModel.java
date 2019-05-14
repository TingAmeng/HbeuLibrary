package com.example.hbeulibrary.Model;

import android.content.Context;

import com.example.hbeulibrary.DB.Book;

import java.util.List;

public interface IBookModel {
    //获得所有书籍
    List<Book> getAllBook();
    //获得某个书籍
    Book getBook(int bookId);
    //组装 书籍信息
    String[] getBookMessage(Book book);
    //添加到收藏
    void addCollect(Context context,int bookId);
    //删除收藏
    void removeCollect(Context context,int bookId);
    //判断书籍是否已经收藏
    boolean isCollected(Context context,int bookId);
}
