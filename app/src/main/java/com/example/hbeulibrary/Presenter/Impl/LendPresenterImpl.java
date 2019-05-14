package com.example.hbeulibrary.Presenter.Impl;

import com.example.hbeulibrary.DB.Book;
import com.example.hbeulibrary.Model.IBookModel;
import com.example.hbeulibrary.Model.ILendModel;
import com.example.hbeulibrary.Model.Impl.BookModelImpl;
import com.example.hbeulibrary.Model.Impl.LendModelImpl;
import com.example.hbeulibrary.Presenter.ILendPresenter;
import com.example.hbeulibrary.View.IBookMessageView;

public class LendPresenterImpl implements ILendPresenter {
    private IBookModel iBookModel;
    private ILendModel iLendModel;
    private IBookMessageView iBookMessageView;

    public LendPresenterImpl(IBookMessageView iBookMessageView) {
        this.iBookMessageView = iBookMessageView;
        this.iLendModel = new LendModelImpl(this);
        this.iBookModel = new BookModelImpl(this);
    }

    @Override
    public void addLend(Book book) {
        if (iLendModel.addLend(book)) {
            iBookMessageView.LendSuccess();
            iBookMessageView.updateLendNumbers(book.getNumber());
        } else {
            iBookMessageView.LendFailed();
        }
    }
}
