package com.example.hbeulibrary.Presenter.Impl;

import android.content.Context;

import com.example.hbeulibrary.DB.Book;
import com.example.hbeulibrary.Model.IBookModel;
import com.example.hbeulibrary.Model.Impl.BookModelImpl;
import com.example.hbeulibrary.Presenter.IBookPresenter;
import com.example.hbeulibrary.View.IBookMessageView;
import com.example.hbeulibrary.View.IFindView;

import java.util.List;

public class BookPresenterImpl implements IBookPresenter {

    private IBookModel iBookModel;
    private IBookMessageView iBookMessageView;
    private IFindView iFindView;

    public BookPresenterImpl(IBookMessageView iBookMessageView) {
        this.iBookMessageView = iBookMessageView;
        this.iBookModel = new BookModelImpl(this);
    }

    public BookPresenterImpl(IFindView iFindView) {
        this.iFindView = iFindView;
        this.iBookModel = new BookModelImpl(this);
    }

    @Override
    public List<Book> getAllBook() {
        List<Book> bookList = iBookModel.getAllBook();
        return bookList;
    }

    @Override
    public void showBookMessage(Book book) {
        String[] bookMsg = iBookModel.getBookMessage(book);
        iBookMessageView.showBookMessage(bookMsg);
    }

    @Override
    public void addCollect(Context context, int bookId) {
        iBookModel.addCollect(context,bookId);
        iBookMessageView.collectSuccess("收藏成功");

    }

    @Override
    public void removeCollect(Context context, int bookId) {
        iBookModel.removeCollect(context,bookId);
        iBookMessageView.removeSuccess("取消收藏");
    }

    @Override
    public void isCollected(Context context, int bookId) {
        boolean isCollected = iBookModel.isCollected(context,bookId);
        if (isCollected) {
            iBookMessageView.changeFab();
        }
    }


}
