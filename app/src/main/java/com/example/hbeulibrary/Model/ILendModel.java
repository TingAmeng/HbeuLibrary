package com.example.hbeulibrary.Model;

import com.example.hbeulibrary.DB.Book;

public interface ILendModel {
    // Lend表 添加借阅数据
    boolean addLend(Book book);
}
