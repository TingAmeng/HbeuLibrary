package com.example.hbeulibrary.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.hbeulibrary.Adapter.BookAdapter;
import com.example.hbeulibrary.DB.Book;
import com.example.hbeulibrary.DB.InitDB;
import com.example.hbeulibrary.R;
import com.example.hbeulibrary.Util.ItemDivider;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment {

    private List<Book> bookList = LitePal.findAll(Book.class);


    private RecyclerView searchRecyclerView;
    private BookAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search,container,false);
        //initBooks();


        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        searchRecyclerView = (RecyclerView) view.findViewById(R.id.search_recycler_view);
        searchRecyclerView.setLayoutManager(layoutManager);
        //设置分隔线
        searchRecyclerView.addItemDecoration(new ItemDivider()
                .setDividerWith(1).setDividerColor(R.color.colorPrimary));
        adapter = new BookAdapter(bookList);
        searchRecyclerView.setAdapter(adapter);
        return view;
    }




}
