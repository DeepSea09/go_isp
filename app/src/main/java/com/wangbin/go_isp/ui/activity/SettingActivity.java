package com.wangbin.go_isp.ui.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.ToastUtils;
import com.wangbin.go_isp.Constants;
import com.wangbin.go_isp.R;

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
public class SettingActivity extends AppCompatActivity {
    public boolean isFocus = false;
    public String keyText = "";

    @BindView(R.id.et_anjian)
    EditText et_anjian;

    @BindView(R.id.et_dizhi)
    EditText et_dizhi;

    @BindView(R.id.tv_baocun)
    TextView tv_baocun;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_activity);
        ButterKnife.bind(this);
        SharedPreferences userSettings = getSharedPreferences("setting", 0);
        String dizhi = userSettings.getString("dizhi","http://47.94.107.122:8001/");
        String keycode = userSettings.getString("keycode","520;");
        keyText = keycode;
        et_dizhi.setText(dizhi);
        et_anjian.setText(keyText);
        et_anjian.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                isFocus = hasFocus;
            }
        });
    }

    @OnClick({R.id.tv_baocun})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_baocun:
                SharedPreferences userSettings = getSharedPreferences("setting", 0);
                SharedPreferences.Editor editor = userSettings.edit();
                editor.putString("keycode",keyText);

                editor.putString("dizhi",et_dizhi.getText().toString());
                Constants.NET_URL = et_dizhi.getText().toString();
                editor.commit();
                ToastUtils.showShort("保存成功");
                break;
        }


    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Log.e("onKeyDown: ", keyCode+"");
        if(isFocus){
            keyText = keyText+keyCode+";";
            et_anjian.setText(keyText);
            Log.e("onKeyDown: ----", keyCode+"");
        }
        return super.onKeyDown(keyCode, event);
    }


}

