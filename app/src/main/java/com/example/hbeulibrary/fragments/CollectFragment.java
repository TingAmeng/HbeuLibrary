package com.example.hbeulibrary.fragments;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.hbeulibrary.Adapter.CollectAdapter;
import com.example.hbeulibrary.DB.Book;
import com.example.hbeulibrary.R;
import com.example.hbeulibrary.Util.MyApplication;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;



public class CollectFragment extends Fragment implements View.OnClickListener{
    //LinkedHashMap() 存入数据和取出数据顺序一致，而HashMap()不行
    private Map<String,?> map = new LinkedHashMap<>();
    SharedPreferences pref;

    private List<Book> bookList = new ArrayList<>();
    private List<String> deList = new ArrayList<>();
    private List<Integer> deListPosition = new ArrayList<>();
    private SharedPreferences.Editor editor;

    private Context mContext;
    private RecyclerView recyclerView;
    private LinearLayout llEidt;
    private ImageView editButton;
    private Button cancelButton;
    private Button selAllBtn;
    private Button delBtn;
    //用来记录 selAllBtn 的点击
    private boolean isClicked = false;
    //CheckBox checkBox;

    CollectAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_collect,container,false);
        mContext = getActivity();
        recyclerView = (RecyclerView) view.findViewById(R.id.collect_rl);
        editButton = (ImageView) getActivity().findViewById(R.id.title_edit_button);
        llEidt = (LinearLayout) view.findViewById(R.id.ll_edit);
        cancelButton = (Button) view.findViewById(R.id.btn_cancel);
        selAllBtn = (Button) view.findViewById(R.id.btn_sel_all);
        delBtn = (Button) view.findViewById(R.id.btn_del);

        //View view1 = LayoutInflater.from(mContext).inflate(R.layout.collect_item,container,false);
        //checkBox = (CheckBox) view.findViewById(R.id.collect_check_box);

        editButton.setOnClickListener(this);
        cancelButton.setOnClickListener(this);
        selAllBtn.setOnClickListener(this);
        delBtn.setOnClickListener(this);
        Log.d("TAG", "onCreateView: ");
        bookList.clear();//清空list数据 ，避免重复加载
        //瀑布流布局,两个参数（布局的列数，排列方向）
        initCollect();   //添加  list 数据
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager
                (3,StaggeredGridLayoutManager.VERTICAL);
        adapter = new CollectAdapter(bookList);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);


        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            //编辑
            case R.id.title_edit_button:
                llEidt.setVisibility(View.VISIBLE);
                adapter.setCheckBoxVisible();
                adapter.notifyDataSetChanged();
                //清空deList;
                adapter.setDeList();
                break;
            //全选
            case R.id.btn_sel_all:
                if (isClicked) {  //取消全选
                    selAllBtn.setText("全  选");
                    adapter.setAllCheck();
                    adapter.notifyDataSetChanged();
                    isClicked = false;
                } else {  //全选
                    selAllBtn.setText("取消全选");
                    adapter.setAllCheck();
                    adapter.notifyDataSetChanged();
                    isClicked = true;
                }
                break;
            //删除
            case R.id.btn_del:
                delCollect();
                llEidt.setVisibility(View.GONE);
                adapter.setCheckBoxGone();
                adapter.notifyDataSetChanged();
                break;
            //取消
            case R.id.btn_cancel:
                //清空deList
                adapter.setDeList();
                llEidt.setVisibility(View.GONE);
                adapter.setCheckBoxGone();
                adapter.notifyDataSetChanged();
                break;
            default:
                    break;

        }
    }

    // 依据 pref 中的 书目id 实例化 Book 并添加到bookList中
    private void initCollect(){
        long[] ids = new long[20];
        int i =0;
        //实例化pref文件
        pref = getActivity().getSharedPreferences
                ("user1collect", Context.MODE_PRIVATE);
        //将文件的键值对赋值给 Map对象
        if (pref.getInt("userid",0) == 1) {

            map = pref.getAll();
            map.remove("userid");
            //遍历Map对象，
            for (String key : map.keySet()) {
                ids[i] = Integer.parseInt(map.get(key).toString());
                i++;
            }
        }
        //获得 收藏 中的 Book实例，并放入bookList
        bookList = LitePal.findAll(Book.class,ids);
    }

    // 删除选中的书目，清除 pref 中对应的id
    public void delCollect() {
        deList = adapter.getDeList();
        deListPosition = adapter.ItemPositionList();
        //mContext = MyApplication.getContext();
        editor = getActivity().getSharedPreferences("user1collect",Context.MODE_PRIVATE).edit();
        for (int i = 0; i < deList.size(); i++) {
            editor.remove(deList.get(i));
        }
        editor.apply();
        //因为 list.remove() 删除元素后，后面元素会自动向前移位，删除多个元素的话
        //必须 倒序 删除，这样前面删除的元素不会影响后面删除的元素
        for (int i = deListPosition.size()-1; i >= 0; i--) {
            int mPosition = deListPosition.get(i);
            bookList.remove(mPosition);
        }
//        bookList.removeAll(deListPosition);
//        adapter.notifyDataSetChanged();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("TAG", "onResume ");
    }

    //@Override
    //此方法 每次切换回来后会重新调用，hidden分别是否重新回来
    //由于viewpager的预加载机制,在viewpager里面的fragment
    // 其生命周期会发生混乱而且onHiddenChanged不起作用,
    // 例如onresume方法在没有用户可见的情况下就会调用
//    public void onHiddenChanged(boolean hidden) {
//        super.onHiddenChanged(hidden);
//        if (hidden) {
//            return;
//        } else {
//            Log.d("TAG", "onHiddenChanged: ");
//            adapter.notifyDataSetChanged();
//        }
//    }

    //
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

    }
}
