package com.example.hbeulibrary.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
    //用与 判断 ListView 是否为空
    private boolean isFirst = true;
    private boolean isList = false;
    private LendAdapter adapter;
    private List<Lend> mLendList = new ArrayList<>();
    private Context mContext ;

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

        initPref(); //初始化 pref “user1_lend_data”
        initList(); // 根据 pref 中的key 查询到 借书记录， 添加到mLendList中
        //创建 LendAdapter 对象
        adapter = new LendAdapter(mContext,mLendList);
//        if (isList) {
//            lendTextView.setVisibility(View.VISIBLE);
//        } else {
//            //注入adapter, LendAdapter继承自BaseSwipeAdapter，绑定view和数据源操作 全在 LendAdapter中
            lendListView.setAdapter(adapter);

//        }
        return view;
    }


    public void isListNull() {
        isList = true;
    }

    public void listNoNull(){
        isList = false;
    }

    private void initPref(){
        SharedPreferences pref = getActivity().getSharedPreferences("user1_lend_data",Context.MODE_PRIVATE);
        if (pref.getAll().isEmpty()) {
            isFirst = true;
        }

        //如果是第一次启动程序，将查询到的 lend 记录 保存到 pref 中
        if (isFirst) {
            mLendList = LitePal.where("userid = ?","1")
                    .find(Lend.class);
            if (mLendList != null) {
                Gson gson = new Gson();
                String strJson = gson.toJson(mLendList); //将 list 转换为 json;
                SharedPreferences.Editor editor = getActivity().getSharedPreferences
                        ("user1_lend_data", Context.MODE_PRIVATE).edit();
                editor.putString("LEND_LIST",strJson);
                editor.apply();
            }
            isFirst = false;
            mLendList.clear();
        }
    }

    private void initList(){
        /*long[] ids = new long[20];
        int i =0;
        SharedPreferences pref = getActivity().getSharedPreferences("user1_lend_data",Context.MODE_PRIVATE);
        //将文件的键值对赋值给 Map对象
        Map<String,?> map = pref.getAll();
        //遍历Map对象，
        for (String key : map.keySet()) {
            ids[i] = Integer.parseInt(map.get(key).toString());
            i++;
        }
        //默认按 id 增序排列
        mLendList = LitePal.findAll(Lend.class,ids);*/
        SharedPreferences pref = getActivity().getSharedPreferences("user1_lend_data",Context.MODE_PRIVATE);
        String listJson = pref.getString("LEND_LIST","");  //取出strJson;
        if (!listJson.equals("")) {
            Gson gson = new Gson();
            mLendList = gson.fromJson(listJson,
                    new TypeToken<List<Lend>>(){}.getType());
        }

    }
}
