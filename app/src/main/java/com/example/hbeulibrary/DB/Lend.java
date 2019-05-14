package com.example.hbeulibrary.DB;

import org.litepal.crud.LitePalSupport;

public class Lend extends LitePalSupport {
    private int id;
    private int userId;
    private int bookId;
    private int weight;         // 置顶权重
    private String bookName;
    private String bookAuthor;
    private String lendDate;
    private String returnDate;
    private String status;     //借书状态
    private int lendTimes;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getBookId() {
        return bookId;
    }
    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getBookAuthor() {
        return bookAuthor;
    }

    public void setBookAuthor(String bookAuthor) {
        this.bookAuthor = bookAuthor;
    }

    public String getLendDate() {
        return lendDate;
    }

    public void setLendDate(String lendDate) {
        this.lendDate = lendDate;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getLendTimes() {
        return lendTimes;
    }

    public void setLendTimes(int lendTimes) {
        this.lendTimes = lendTimes;
    }
}
