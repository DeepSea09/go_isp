package com.wangbin.go_isp.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.CustomListener;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.wangbin.go_isp.R;
import com.wangbin.go_isp.utils.SearchDialog;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * <br></br>************************************************
 * <br></br>PROJECT_NAME : go_isp_ccb
 * <br></br>PACKAGE_NAME : com.wangbin.go_isp_ccb.ui.activity
 * <br></br>AUTHOR : WangBin
 * <br></br>DATA : 2019/5/11
 * <br></br>TIME : 17:21
 * <br></br>MSG :
 * <br></br>************************************************
 */
public class MainActivity extends AppCompatActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.btn_search)
    TextView btnSearch;
    private ArrayList<String> food = new ArrayList<>();
    private OptionsPickerView pvNoLinkOptions;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_activity_main);
        ButterKnife.bind(this);
        food.add("【0】机房001");
        food.add("【1】库房001");
        food.add("【2】办公室001");
        food.add("【3】机房002");
        food.add("【4】实验室001");
        food.add("【5】机房003");
        initNoLinkOptionsPicker();

    }

    private void showSelectRoomDiaog() {

        SearchDialog dialog = new SearchDialog();
        //  dialog.setDataList(receiveRoomCode_list);
        dialog.setSelectRoomListener(position -> {

            //showLoading("办公室001");

        });
        getSupportFragmentManager().beginTransaction().add(dialog, "SearchDialog").commitAllowingStateLoss();
        //dialog.show(getSupportFragmentManager(), "SelectRoomDialog");
    }

    @OnClick({R.id.btn_search})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_search:
                // showSelectRoomDiaog();
                pvNoLinkOptions.show();
                break;
        }


    }


    private void initNoLinkOptionsPicker() {

        pvNoLinkOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {

            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {



            }
        }).setLayoutRes(R.layout.pickerview_custom_options, new CustomListener() {
            @Override
            public void customLayout(View v) {

            }
        }).build();
        pvNoLinkOptions.setNPicker(food, null, null);
        pvNoLinkOptions.setSelectOptions(0);
    }


}

