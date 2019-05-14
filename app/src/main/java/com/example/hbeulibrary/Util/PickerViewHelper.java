package com.example.hbeulibrary.Util;

import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.example.hbeulibrary.Adapter.LendAdapter;
import com.example.hbeulibrary.DB.Lend;
import com.example.hbeulibrary.R;
import com.example.hbeulibrary.View.Fragments.LendFragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PickerViewHelper implements IPickerViewHelper{

    private List<String> optionDays = new ArrayList<>();
    private long days = 0;

    @Override
    public void showPickerView(final View view, final Lend lend) {
        OptionsPickerView pvOptions = new OptionsPickerBuilder(view.getContext(),
                new OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int options2, int options3, View v) {
                        //返回的分别是三个级别的选中位置
                        String pickStr = optionDays.get(options1);
                        if (pickStr == "一天") {
                            days = 1;
                        } else if (pickStr == "一个月") {
                            days = 30;
                        } else if (pickStr == "二个月") {
                            days = 60;
                        } else if (pickStr == "三个月"){
                            days = 90;
                        }
                        String returnDateStr = lend.getReturnDate();
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        try {
                            Date returnDate = sdf.parse(returnDateStr);
                            Date newDate = new Date(returnDate.getTime() + days*24*60*60*1000);
                            returnDateStr = sdf.format(newDate);
                            lend.setReturnDate(returnDateStr);
                            lend.setLendTimes(lend.getLendTimes()+1);
                            lend.save();
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        TextView returnDate = (TextView) view.findViewById(R.id.return_date);
                        returnDate.setText(returnDateStr);
                        Toast.makeText(MyApplication.getContext(),"续借成功",Toast.LENGTH_SHORT);
                    }
                }).setTitleText("请选择续借天数")
                .build();
        pvOptions.setPicker(optionDays);
        pvOptions.show();
    }

    @Override
    public void iniData() {
        optionDays.add("一天");
        optionDays.add("一个月");
        optionDays.add("二个月");
        optionDays.add("三个月");
    }
}
