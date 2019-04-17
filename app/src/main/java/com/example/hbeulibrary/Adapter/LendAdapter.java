package com.example.hbeulibrary.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.swipe.SimpleSwipeListener;
import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.BaseSwipeAdapter;
import com.example.hbeulibrary.DB.Book;
import com.example.hbeulibrary.DB.Lend;
import com.example.hbeulibrary.R;
import com.example.hbeulibrary.fragments.LendFragment;
import com.google.gson.Gson;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;


import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

public class LendAdapter extends BaseSwipeAdapter {
    private Context mContext;
    private LayoutInflater mInflater;

    private List<Lend> mLendList = new ArrayList<>();
    private int mCurrentDialogStyle = com.qmuiteam.qmui.R.style.QMUI_Dialog;


    public LendAdapter(Context context,List<Lend> list) {
        this.mContext = context;
        this.mLendList = list;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.lend_item_swipe;
    }

    /**
     *  第一次 加载 ListView 中的每个 item 的 view
     * */
    @Override
    public View generateView(int position, ViewGroup parent) {
        View view = mInflater.inflate(R.layout.lend_item,null);
        return view;
    }

    /**
     * 对每个 item 进行赋值
     * */
    @Override
    public void fillValues(final int position, View convertView) {
        TextView delete = (TextView) convertView.findViewById(R.id.lend_delete);
        TextView setTop = (TextView) convertView.findViewById(R.id.lend_set_top);
        TextView bookName = (TextView) convertView.findViewById(R.id.lend_book_name);
        TextView bookAuthor = (TextView) convertView.findViewById(R.id.lend_book_author);
        TextView lendDate = (TextView) convertView.findViewById(R.id.lend_date);
        TextView lendStatus = (TextView) convertView.findViewById(R.id.lend_status);

        final Lend lend = mLendList.get(position);
        bookName.setText(lend.getBookName());
        bookAuthor.setText(lend.getBookAuthor());
        lendDate.setText(lend.getLendTime());
        lendStatus.setText(lend.getStatus());

        final SwipeLayout swipeLayout = (SwipeLayout) convertView.findViewById(getSwipeLayoutResourceId(position));
        swipeLayout.addSwipeListener(new SimpleSwipeListener(){
            @Override
            public void onOpen(SwipeLayout layout) {
                super.onOpen(layout);
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(mContext,"删除 "+lend.getBookName(),Toast.LENGTH_SHORT).show();
                swipeLayout.close();
                if (lend.getStatus().equals("已借")) {
                    //调用QMUI的消息框
                    new QMUIDialog.MessageDialogBuilder(mContext)
                            .setTitle("操作失败")
                            .setMessage("请先去图书馆还书哦~")
                            .addAction("确定", new QMUIDialogAction.ActionListener() {
                                @Override
                                public void onClick(QMUIDialog dialog, int index) {
                                    dialog.dismiss();
                                }
                            })
                            .create(mCurrentDialogStyle).show();
                }
            }
        });
        setTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position == 0) {
                    Toast.makeText(mContext, "已经置顶 " + lend.getBookName(), Toast.LENGTH_SHORT).show();
                } else {
                    Lend lend1 = mLendList.get(0);
                    lend1.setWeight(0);
                    lend1.save();
                    Lend lend2 = mLendList.get(position);
                    lend2.setWeight(1);
                    lend2.save();
                }
                swipeLayout.close();
                LendFragment lendFragment = new LendFragment();
                mLendList = lendFragment.getLendList();
                notifyDataSetChanged();
            }
        });
        /*swipeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext,"查看 "+lend.getBookName(),Toast.LENGTH_SHORT).show();
            }
        });*/


    }

    @Override
    public int getCount() {
        return mLendList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }




    // 想把 置顶后的位置排序 保存到 pref中 是存在困难，排序随机。 故此方法有待进一步研究
    // 每个key 存入 pref ,位置不是有序的
//    private void setPref(){
//        SharedPreferences.Editor editor = mContext.getSharedPreferences
//                ("user1_lend_data", Context.MODE_PRIVATE).edit();
//        editor.clear();
//        editor.apply();
//        for (int i = 0; i < mLendList.size(); i++) {
//            Lend lend = mLendList.get(i);
//            editor.putInt("lend"+lend.getId(),lend.getId());
//        }
//        editor.apply();
//
//    }

    public void setLendList(List<Lend> list){
        mLendList = list;
    }
}
