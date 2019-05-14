package com.example.hbeulibrary.View.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.hbeulibrary.Adapter.BookAdapter;
import com.example.hbeulibrary.DB.Book;
import com.example.hbeulibrary.Model.IBookModel;
import com.example.hbeulibrary.Presenter.IBookPresenter;
import com.example.hbeulibrary.Presenter.Impl.BookPresenterImpl;
import com.example.hbeulibrary.R;
import com.example.hbeulibrary.View.Activities.SearchActivity;
import com.example.hbeulibrary.Util.ItemDivider;
import com.example.hbeulibrary.View.IFindView;

import org.litepal.LitePal;

import java.util.List;

public class SearchFragment extends Fragment implements IFindView {

    private IBookPresenter bookPresenter;
    private List<Book> bookList;

    private RecyclerView searchRecyclerView;
    private BookAdapter adapter;
    private ImageButton btnSearch;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search,container,false);

        //bind view
        btnSearch = (ImageButton) getActivity().findViewById(R.id.title_search_button);
        searchRecyclerView = (RecyclerView) view.findViewById(R.id.search_recycler_view);

        //init
        bookPresenter = new BookPresenterImpl(this);
        bookList = bookPresenter.getAllBook();

        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        searchRecyclerView.setLayoutManager(layoutManager);
        //设置分隔线
        searchRecyclerView.addItemDecoration(new ItemDivider()
                .setDividerWith(1).setDividerColor(R.color.colorPrimary));
        adapter = new BookAdapter(bookList);
        searchRecyclerView.setAdapter(adapter);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goSearch();
            }
        });
        return view;
    }


    /**
     * 跳转至 search
     * */
    private void goSearch() {
        Intent intent = new Intent(getActivity(), SearchActivity.class);
        startActivity(intent);
    }

}
