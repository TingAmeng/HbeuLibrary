package com.example.hbeulibrary.View;

public interface IBookMessageView {
    //显示书籍详细信息
    void showBookMessage(String[] listItems);
    //收藏成功
    void collectSuccess(String msg);
    //收藏失败
    void removeSuccess(String msg);
    //借阅成功
    void LendSuccess();
    //借阅失败
    void LendFailed();
    //显示进度加载框
    void showLoadDialog();
    //取消显示进度加载框
    void dismissLoadDialog();
    //更改 fab （收藏）按钮样式
    void changeFab();
    //更新可借数量
    void updateLendNumbers(int number);
}
