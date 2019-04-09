package com.example.hbeulibrary.DB;

import org.litepal.crud.LitePalSupport;

import java.io.Serializable;

public class Book extends LitePalSupport implements Serializable {
    private int id;
    private int bookImageId;   //图片id
    private String bookName;   //书名
    private String bookAuthor;  //作者
    private String bookPublish;  //出版社
    private String bookPublishTime;  //出版时间
    private int pages;      //页数
    private double price;    //价格
    private String layout;    //装帧
    private String bookIBSN;  //IBSN
    private int number;      //可借数量
    private String info;     //内容简介


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getBookIBSN() {
        return bookIBSN;
    }

    public void setBookIBSN(String bookIBSN) {
        this.bookIBSN = bookIBSN;
    }

    public int getBookImageId() {
        return bookImageId;
    }

    public void setBookImageId(int bookImageId) {
        this.bookImageId = bookImageId;
    }

    public String getBookPublish() {
        return bookPublish;
    }

    public void setBookPublish(String bookPublish) {
        this.bookPublish = bookPublish;
    }

    public String getBookPublishTime() {
        return bookPublishTime;
    }

    public void setBookPublishTime(String bookPublishTime) {
        this.bookPublishTime = bookPublishTime;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getLayout() {
        return layout;
    }

    public void setLayout(String layout) {
        this.layout = layout;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
