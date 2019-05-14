package com.example.hbeulibrary.View.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.example.hbeulibrary.Adapter.LendAdapter;
import com.example.hbeulibrary.DB.Lend;
import com.example.hbeulibrary.R;
import com.example.hbeulibrary.Util.MyApplication;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LendFragment extends Fragment {

    private LendAdapter adapter;
    private List<Lend> mLendList = new ArrayList<>();
    private Context mContext;
    private ImageButton btnRefresh;

    private int userId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lend, container, false);
        ListView lendListView = (ListView) view.findViewById(R.id.lend_list_view);
        TextView lendTextView = (TextView) view.findViewById(R.id.lend_text_view);
        btnRefresh = (ImageButton) getActivity().findViewById(R.id.title_refresh_button);


        initList(); // 在Lend表中查询到 借书记录， 添加到mLendList中

        if (mLendList.isEmpty()) {
           lendTextView.setVisibility(View.VISIBLE);

       } else {
            lendTextView.setVisibility(View.GONE);
            adapter = new LendAdapter(mContext,mLendList);
            //注入adapter, LendAdapter继承自BaseSwipeAdapter，绑定view和数据源操作 全在 LendAdapter中
            lendListView.setAdapter(adapter);
       }

       btnRefresh.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               initList();
               adapter.setLendList(mLendList);
               adapter.notifyDataSetChanged();
           }
       });

        return view;
    }


    private void initList(){
        MyApplication myApplication = new MyApplication();
        userId = myApplication.getUserId();

        mLendList = LitePal.where("userid = ?", Integer.toString(userId))
                .find(Lend.class);

            Lend lend1 = new Lend();
        //跳过 Lend 表中的第一个
        for (int i=1;i<mLendList.size();i++) {

            Lend lend = mLendList.get(i);

            if (lend.getWeight() == 1) {
                lend1 = lend;   //获得  weight == 1 的 lend
                //删除原来的lend
                mLendList.remove(lend);
                //将 lend1 插入 第一个
                mLendList.add(0,lend1);
            }
        }
    }

    public List<Lend> getLendList() {
        initList();
        return mLendList;
    }
}
