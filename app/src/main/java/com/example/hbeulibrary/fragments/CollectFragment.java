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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

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
    private ImageButton btnEdit;
    private Button cancelButton;
    private Button selAllBtn;
    private Button delBtn;
    private TextView nullText;
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
        btnEdit = (ImageButton) getActivity().findViewById(R.id.title_edit_button);
        llEidt = (LinearLayout) view.findViewById(R.id.ll_edit);
        cancelButton = (Button) view.findViewById(R.id.btn_cancel);
        selAllBtn = (Button) view.findViewById(R.id.btn_sel_all);
        delBtn = (Button) view.findViewById(R.id.btn_del);
        nullText = (TextView) view.findViewById(R.id.text_null);

        btnEdit.setOnClickListener(this);
        cancelButton.setOnClickListener(this);
        selAllBtn.setOnClickListener(this);
        delBtn.setOnClickListener(this);

        bookList.clear();//清空list数据 ，避免重复加载
        //瀑布流布局,两个参数（布局的列数，排列方向）
        initCollect();   //添加  list 数据
        if (bookList.isEmpty()) {
            nullText.setVisibility(View.VISIBLE);
        } else {
            nullText.setVisibility(View.GONE);
            StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager
                    (3, StaggeredGridLayoutManager.VERTICAL);
            adapter = new CollectAdapter(bookList);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(adapter);
        }
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
                //清空deList、deListPosition;
                adapter.setDeList();
                adapter.setDeListPosition();
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
                if (bookList.isEmpty()) {  //如果 bookList 没有收藏书籍
                    nullText.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                }
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
    // 第一次 加载  需要从 pref 中获取用户 收藏书单 信息
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
        bookList.clear();
        bookList = LitePal.findAll(Book.class,ids);
    }

    // 删除选中的书目，清除 pref 中对应的id
    public void delCollect() {
        deList = adapter.getDeList();
        deListPosition = adapter.ItemPositionList();
        editor = getActivity().getSharedPreferences("user1collect",Context.MODE_PRIVATE).edit();
        for (int i = 0; i < deList.size(); i++) {
            editor.remove(deList.get(i));
        }
        editor.apply();
        //因为 list.remove() 删除元素后，后面元素会自动向前移位，删除多个元素的话
        //必须 倒序 删除，这样前面删除的元素不会影响后面删除的元素
        // List 中 没有随机删除 index的直接方法，如果你随机勾选 多个item, 用remove方法 删除时，会出错，
        // 每一次 remove 过后，List 中的元素重新排序。
        BubbleSort(deListPosition);
        for (int i = 0; i < deListPosition.size(); i++) { //动态移除 bookList 中的数据，不再去 pref 文件中获取
            int mPosition = deListPosition.get(i);
            bookList.remove(mPosition);
        }


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


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

    }

    /**
     *
     * 将list  中的元素的值 从大到小排序
     * */
    private void BubbleSort(List<Integer> list){
        if (list.size() >= 2) {
            for (int i = 0; i < list.size(); i++) {
                for (int j = list.size() - 1; j > i; j--) {
                    if (list.get(j) > list.get(j-1)) {
                        int t = list.get(j-1);
                        list.set(j-1,list.get(j));
                        list.set(j,t);
                    }
                }
            }
        }
    }
}
