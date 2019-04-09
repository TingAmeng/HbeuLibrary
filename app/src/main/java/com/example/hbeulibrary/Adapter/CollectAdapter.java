package com.example.hbeulibrary.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.hbeulibrary.DB.Book;
import com.example.hbeulibrary.MainActivity;
import com.example.hbeulibrary.R;
import com.example.hbeulibrary.Util.MyApplication;
import com.example.hbeulibrary.fragments.CollectFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CollectAdapter extends RecyclerView.Adapter<CollectAdapter.ViewHolder> {
    private List<Book> mBookList;
    private Context mContext;
    //记录当前点击的item
    private int position;
    //记录要删除的书目item
    private List<String> deList;
    //记录要删除的位置
    private List<Integer> deListPosition = new ArrayList<>();
    //记录checkBox的隐藏状态
    private boolean isGone = false;
    //记录checkBox的选中状态
    private boolean isCheck = false;




    //内部类ViewHolder
    static class ViewHolder extends RecyclerView.ViewHolder{
        View collectView;
        ImageView bookImage;
        TextView bookName;
        CheckBox bookCheck;
        //内部类的构造函数，传入view
        public ViewHolder(View view){
            super(view);
            collectView = view;
            bookImage = (ImageView) view.findViewById(R.id.collect_book_img);
            bookName = (TextView) view.findViewById(R.id.collect_book_name);
            bookCheck = (CheckBox) view.findViewById(R.id.collect_check_box);
        }
    }

    //FruitAdapter的构造函数，传入数据源fruitList
    public CollectAdapter(List<Book> bookList){
        mBookList = bookList;
    }

    @NonNull
    @Override
    //由于FruitAdapter继承自RecyclerView.Adapter,必须重写这3个方法

    //onCreateViewHolder用于创建一个ViewHolder实例
    public CollectAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        if( mContext == null){
            mContext = viewGroup.getContext();
        }
        setDeList(); //初始化 deList<>,记录CheckBox 选中的 对应的bookid;
        setItemPositionList(); //初始化List 记录 CheckBox选中的item位置
        //加载fruit_item布局 传入到vieHolder中
        View view = LayoutInflater.from(mContext).inflate(R.layout.collect_item,viewGroup,false);

        final ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.collectView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                position = viewHolder.getAdapterPosition();
                Book book = mBookList.get(position);
                Toast.makeText(mContext,book.getBookName(),Toast.LENGTH_SHORT).show();
            }
        });
        viewHolder.bookCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                position = viewHolder.getAdapterPosition();
                Book book = mBookList.get(position);
                if (isChecked) {
                    //将勾选的书目id 放入 deList在中
                    deList.add("book"+book.getId());
                    deListPosition.add(position);

                } else {
                    //取消勾选，移除对应booid
                    deList.remove("book"+book.getId());
                    Integer mPosition = position;
                    deListPosition.remove(mPosition);
                }
            }
        });
        return viewHolder;
    }

    @Override
    //onBindViewHolder()对RecyclerView子项进行赋值
    public void onBindViewHolder(@NonNull CollectAdapter.ViewHolder viewHolder, int position) {
        //通过positon参数获取fruit实例
        Book book = mBookList.get(position);
        //将数据填入相应控件
        Glide.with(mContext).load(book.getBookImageId())
                .override(82,120)
                .into(viewHolder.bookImage);
        viewHolder.bookName.setText(book.getBookName());
        if (isGone) {
            viewHolder.bookCheck.setVisibility(View.VISIBLE);
        } else {
            viewHolder.bookCheck.setVisibility(View.GONE);
        }
        if (isCheck) {
            viewHolder.bookCheck.setChecked(true);
        } else {
            viewHolder.bookCheck.setChecked(false);
        }

    }

    @Override
    public int getItemCount() {
        return mBookList.size();
    }

    public List<String> getDeList(){
        return deList;
    }

    public void setDeList(){
        deList = new ArrayList<>();
        //这里千万不能这样初始化
        //deList = null;   不能设为空。
    }

    public List<Integer> ItemPositionList(){
        return deListPosition;

    }

    public void setItemPositionList(){
        deListPosition = new ArrayList<>();
    }
    public void setCheckBoxGone(){
        isGone = false;
    }
    public void setCheckBoxVisible(){
        isGone = true;
    }

    public void setAllCheck(){
        if (!isCheck) {
            isCheck = true;
        } else {
            isCheck = false;
        }

    }
}
