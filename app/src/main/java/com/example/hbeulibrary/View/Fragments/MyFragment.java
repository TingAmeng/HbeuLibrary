package com.example.hbeulibrary.View.Fragments;

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

import com.example.hbeulibrary.Presenter.IUserPresenter;
import com.example.hbeulibrary.Presenter.Impl.UserPresenterImpl;
import com.example.hbeulibrary.R;
import com.example.hbeulibrary.Util.ActivityCollector;
import com.example.hbeulibrary.View.IMyView;
import com.example.hbeulibrary.View.Activities.RePwdActivity;
import com.example.hbeulibrary.View.Activities.loginActivity;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.widget.QMUIRadiusImageView;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;
import com.qmuiteam.qmui.widget.grouplist.QMUICommonListItemView;
import com.qmuiteam.qmui.widget.grouplist.QMUIGroupListView;

public class MyFragment extends Fragment implements View.OnClickListener, IMyView {

    private QMUIRadiusImageView MyImage;
    private QMUIGroupListView mGroupListView1;
    private ConstraintLayout cs;
    private TextView userName;

    private int mCurrentDialogStyle = com.qmuiteam.qmui.R.style.QMUI_Dialog;
    private QMUITipDialog tipDialog;
    IUserPresenter userPresenter;

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
        userPresenter = new UserPresenterImpl(this);
        tipDialog = new QMUITipDialog.Builder(getActivity())
                .setIconType(QMUITipDialog.Builder.ICON_TYPE_LOADING)
                .setTipWord("正在退出...")
                .create();
        cs.setOnClickListener(this);
        return view;
    }


    //初始化 GroupListView
    private void initGroupListView(){
        QMUICommonListItemView myItem = mGroupListView1.createItemView(
                ContextCompat.getDrawable(getContext(),R.drawable.ic_my_mesg),
                "查看个人信息",null,
                QMUICommonListItemView.HORIZONTAL,
                QMUICommonListItemView.ACCESSORY_TYPE_NONE
        );
        myItem.setOrientation(QMUICommonListItemView.VERTICAL);
        myItem.setAccessoryType(QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON);
        QMUICommonListItemView firstItem = mGroupListView1.createItemView(
                ContextCompat.getDrawable(getContext(),R.drawable.ic_key),
                "修改密码",null,
                QMUICommonListItemView.HORIZONTAL,
                QMUICommonListItemView.ACCESSORY_TYPE_NONE
        );
        firstItem.setAccessoryType(QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON);

        QMUICommonListItemView secondItem = mGroupListView1.createItemView(
                ContextCompat.getDrawable(getContext(),R.drawable.ic_exit),
                "注销登录", null,
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

                    } else if (text.equals("注销登录")) {
                        //Toast.makeText(getContext(),"退出登录",Toast.LENGTH_SHORT).show();
                        showConfirmMessageDialog();


                    } else if (text.equals("帮助与反馈")) {
                        Toast.makeText(getContext(),"帮助与反馈",Toast.LENGTH_SHORT).show();

                    } else if (text.equals("关于")) {
                        Toast.makeText(getContext(),"关于",Toast.LENGTH_SHORT).show();

                    } else if (text.equals("个人信息")) {
                        Toast.makeText(getContext(),"个人信息",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        };
        int size = QMUIDisplayHelper.dp2px(getContext(),20);
        QMUIGroupListView.newSection(getContext())
                .setTitle("")
                .setLeftIconSize(size,ViewGroup.LayoutParams.WRAP_CONTENT)
                .addItemView(myItem,onClickListener)
                .addItemView(firstItem,onClickListener)
                .addItemView(secondItem,onClickListener)
                .addItemView(thirdItem,onClickListener)
                .addItemView(fourItem,onClickListener)
                .addTo(mGroupListView1);
    }
    //含复选框的提示框
    private void showConfirmMessageDialog() {
        final QMUIDialog.CheckBoxMessageDialogBuilder qmuiCheckDialog = new QMUIDialog.CheckBoxMessageDialogBuilder(getActivity());
                qmuiCheckDialog.setTitle("注销后是否删除账号信息?")
                .setMessage("删除账号信息")
                .setChecked(true)
                .setCancelable(false)
                .addAction("取消", new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {
                        dialog.dismiss();
                    }
                })
                .addAction("退出", new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {
                        if (qmuiCheckDialog.isChecked()) {
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        Thread.sleep(500);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                    getActivity().runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            userPresenter.deleteUser(getActivity());
                                        }
                                    });
                                }
                            }).start();

                            goLogin();
                        } else {
                            goLogin();
                        }
                    }
                })
                .create(mCurrentDialogStyle).show();
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

    @Override
    public void showDialog() {
        if (null != tipDialog && !tipDialog.isShowing()) {
            tipDialog.show();
        }
    }

    @Override
    public void dismissDialog() {
        if (null != tipDialog && tipDialog.isShowing()) {
            tipDialog.dismiss();
        }
    }

    private void goLogin() {
        ActivityCollector.finishAll();
        Intent intent = new Intent(getActivity(),loginActivity.class);
        startActivity(intent);

    }
}
