package com.example.hbeulibrary.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.hbeulibrary.BookMassageActivity;
import com.example.hbeulibrary.DB.Book;
import com.example.hbeulibrary.R;
import com.example.hbeulibrary.Util.MyApplication;

import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.ViewHolder> {

    private Context mContext;
    private List<Book> mBookList;

    static class ViewHolder extends RecyclerView.ViewHolder{
        View bookView;
        ImageView bookImage;
        TextView bookName;
        TextView bookAuthor;
        TextView bookIBSN;
        TextView bookInfo;
        //内部类构造函数，传入View
        public ViewHolder(View view){
            super(view);
            bookView = view;
            //通过view的findViewById()获取fruit_item.xml中的UI实例
            bookImage = (ImageView) view.findViewById(R.id.book_img);
            bookName = (TextView) view.findViewById(R.id.book_name);
            bookAuthor = (TextView) view.findViewById(R.id.book_author);
            bookIBSN = (TextView) view.findViewById(R.id.book_IBSN);
            bookInfo = (TextView) view.findViewById(R.id.book_info);
        }
    }

    public BookAdapter(List<Book> mBookList) {
        this.mBookList = mBookList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        if( mContext == null){
            mContext = parent.getContext();
        }
        //加载book_item布局 传入到vieHolder中
        View view = LayoutInflater.from(mContext).inflate
                (R.layout.book_item,parent,false);
        final ViewHolder viewHolder = new ViewHolder(view);
        //点击整个item时
        viewHolder.bookView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = viewHolder.getAdapterPosition();
                Book book = mBookList.get(position);
                Toast.makeText(v.getContext(),book.getBookName(),
                        Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(mContext, BookMassageActivity.class);
//                intent.putExtra("book_name",book.getBookName());
//                intent.putExtra("book_imageid",book.getBookImageId());
                intent.putExtra("book_data",book);
                mContext.startActivity(intent);
            }
        });
        //点击item中的图片时
        /*viewHolder.bookImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = viewHolder.getAdapterPosition();
                Book book = mBookList.get(position);
                Toast.makeText(v.getContext(),book.getBookImageId(),Toast.LENGTH_SHORT).show();
            }
        });*/
        return viewHolder;
    }

    /**
     * onBindViewHolder()对RecyclerView子项进行赋值
     * */

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        //通过position 获得book实例
        Book book = mBookList.get(position);
        //将数据填入相应空间
        Glide.with(mContext).load(book.getBookImageId())
                .override(82,120)
                .into(viewHolder.bookImage);
        viewHolder.bookName.setText(book.getBookName());
        viewHolder.bookAuthor.setText("作者："+book.getBookAuthor());
        viewHolder.bookIBSN.setText("IBSN："+book.getBookIBSN());
        viewHolder.bookInfo.setText("简介："+book.getInfo().substring(0,70)+"....");
    }

    @Override
    public int getItemCount() {
        return mBookList.size();
    }
}
