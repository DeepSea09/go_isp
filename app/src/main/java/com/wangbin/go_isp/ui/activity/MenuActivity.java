package com.wangbin.go_isp.ui.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.CustomListener;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.wangbin.go_isp.Constants;
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
public class MenuActivity extends AppCompatActivity {

    @BindView(R.id.tv_bdbq)
    TextView tv_bdbq;
    @BindView(R.id.tv_pdzc)
    TextView tv_pdzc;
    @BindView(R.id.tv_sz)
    TextView tv_sz;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_activity);
        ButterKnife.bind(this);
        SharedPreferences userSettings = getSharedPreferences("setting", 0);
        String dizhi = userSettings.getString("dizhi","http://47.94.107.122:8001/");
        String yewudizhi = userSettings.getString("yewudizhi","http://47.94.107.122:8000/");
        Constants.NET_URL = dizhi;
        Constants.NET_URLAPI = yewudizhi;
    }


    @OnClick({R.id.tv_bdbq,R.id.tv_pdzc,R.id.tv_sz})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_bdbq:
                Intent intent = new Intent(this, RoomSearchActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_pdzc:
                Intent intents = new Intent(this, ManualInventoryActivity.class);
                startActivity(intents);
                break;
            case R.id.tv_sz:
                Intent intentsz = new Intent(this, SettingActivity.class);
                startActivity(intentsz);
                break;
        }


    }

}

