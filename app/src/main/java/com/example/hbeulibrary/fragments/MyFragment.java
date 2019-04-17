package com.example.hbeulibrary.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hbeulibrary.R;
import com.example.hbeulibrary.RePwdActivity;
import com.example.hbeulibrary.Util.ActivityCollector;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.widget.QMUIRadiusImageView;
import com.qmuiteam.qmui.widget.grouplist.QMUICommonListItemView;
import com.qmuiteam.qmui.widget.grouplist.QMUIGroupListView;

public class MyFragment extends Fragment implements View.OnClickListener {

    private QMUIRadiusImageView MyImage;
    private QMUIGroupListView mGroupListView1;
    private ConstraintLayout cs;
    private TextView userName;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my,container,false);
        MyImage = (QMUIRadiusImageView) view.findViewById(R.id.my_image);
        mGroupListView1 = (QMUIGroupListView) view.findViewById(R.id.group_list);
        cs = (ConstraintLayout) view.findViewById(R.id.cs);
        userName = (TextView) view.findViewById(R.id.user_name);

        initGroupListView();
        cs.setOnClickListener(this);
        return view;
    }


    //初始化 GroupListView
    private void initGroupListView(){
        QMUICommonListItemView firstItem = mGroupListView1.createItemView(
                ContextCompat.getDrawable(getContext(),R.drawable.ic_key),
                "修改密码",null,
                QMUICommonListItemView.HORIZONTAL,
                QMUICommonListItemView.ACCESSORY_TYPE_NONE
        );
        firstItem.setOrientation(QMUICommonListItemView.VERTICAL);
        firstItem.setAccessoryType(QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON);

        QMUICommonListItemView secondItem = mGroupListView1.createItemView(
                ContextCompat.getDrawable(getContext(),R.drawable.ic_exit),
                "退出程序", null,
                QMUICommonListItemView.HORIZONTAL,
                QMUICommonListItemView.ACCESSORY_TYPE_NONE
        );
        secondItem.setAccessoryType(QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON);

        QMUICommonListItemView thirdItem = mGroupListView1.createItemView(
                ContextCompat.getDrawable(getContext(),R.drawable.ic_bulb),
                "帮助与反馈", null,
                QMUICommonListItemView.HORIZONTAL,
                QMUICommonListItemView.ACCESSORY_TYPE_NONE
        );
        thirdItem.setAccessoryType(QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON);

        QMUICommonListItemView fourItem = mGroupListView1.createItemView(
                ContextCompat.getDrawable(getContext(),R.drawable.ic_about),
                "关于", null,
                QMUICommonListItemView.HORIZONTAL,
                QMUICommonListItemView.ACCESSORY_TYPE_NONE
        );
        fourItem.setAccessoryType(QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON);

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v instanceof QMUICommonListItemView) {
                    CharSequence text = ((QMUICommonListItemView) v).getText();
                    if (text.equals("修改密码")) {
                        //Toast.makeText(getContext(),"修改密码",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getActivity(), RePwdActivity.class);
                        startActivity(intent);

                    } else if (text.equals("退出程序")) {
                        //Toast.makeText(getContext(),"退出登录",Toast.LENGTH_SHORT).show();
                        ActivityCollector.finishAll();

                    } else if (text.equals("帮助与反馈")) {
                        Toast.makeText(getContext(),"帮助与反馈",Toast.LENGTH_SHORT).show();

                    } else if (text.equals("关于")) {
                        Toast.makeText(getContext(),"关于",Toast.LENGTH_SHORT).show();

                    }
                }
            }
        };
        int size = QMUIDisplayHelper.dp2px(getContext(),20);
        QMUIGroupListView.newSection(getContext())
                .setTitle("")
                .setLeftIconSize(size,ViewGroup.LayoutParams.WRAP_CONTENT)
                .addItemView(firstItem,onClickListener)
                .addItemView(secondItem,onClickListener)
                .addItemView(thirdItem,onClickListener)
                .addItemView(fourItem,onClickListener)
                .addTo(mGroupListView1);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cs:
                //Toast.makeText(getContext(),"查看个人信息",Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }
}
