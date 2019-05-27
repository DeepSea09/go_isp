package com.wangbin.go_isp.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
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
    public String  julidiyistr="";
    public String  julidierstr="";
    @BindView(R.id.et_anjian)
    EditText et_anjian;

    @BindView(R.id.et_dizhi)
    EditText et_dizhi;
    @BindView(R.id.et_yewudizhi)
    EditText et_yewudizhi;

    @BindView(R.id.tv_baocun)
    TextView tv_baocun;

    @BindView(R.id.spiner_select)
    Spinner spiner_select;

    @BindView(R.id.spiner_selecttwo)
    Spinner spiner_selecttwo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//         <item>0.3米以下</item>
//        <item>1米以下</item>
//        <item>3米以下</item>
//        <item>5米以下</item>
//        <item>5米以上</item>
        String[] strs = new String[5];
        strs[0] = "0.3米以下";
        strs[1] = "1米以下";
        strs[2] = "3米以下";
        strs[3] = "5米以下";
        strs[4] = "5米以上";
        setContentView(R.layout.setting_activity);
        ButterKnife.bind(this);
        SharedPreferences userSettings = getSharedPreferences("setting", 0);
        String dizhi = userSettings.getString("dizhi","http://47.94.107.122:8001/");
        String yewudizhi = userSettings.getString("yewudizhi","http://47.94.107.122:8000/");
        String keycode = userSettings.getString("keycode","520;");
        String julidiyi = userSettings.getString("julidiyi","0.3米以下");
        String julidier = userSettings.getString("julidier","0.3米以下");
        Log.e("onCreate: julidiyi", julidiyi);
        Log.e("onCreate: julidier", julidier);
        for (int i = 0;i<strs.length;i++){
            Log.e("--------", strs[i]);
            if(julidiyi.equals(strs[i])){

                julidiyistr = julidiyi;
                spiner_select.setSelection(i);
            }
            if(julidier.equals(strs[i])){
                julidierstr = julidier;
                spiner_selecttwo.setSelection(i);
            }
        }
        keyText = keycode;
        et_dizhi.setText(dizhi);
        et_yewudizhi.setText(yewudizhi);
        et_anjian.setText(keyText);
        spiner_select.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
//                String cardNumber = m_Countries[arg2];
//                //设置显示当前选择的项
//                arg0.setVisibility(View.VISIBLE);
                  julidiyistr =SettingActivity.this.getResources().getStringArray(R.array.exceptions)[arg2];
//                SharedPreferences.Editor editor = userSettings.edit();
//                editor.putString("julidiyi",cardNumber);
//                editor.commit();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }

        });


        spiner_selecttwo.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
//                String cardNumber = m_Countries[arg2];
//                //设置显示当前选择的项
//                arg0.setVisibility(View.VISIBLE);
                 julidierstr =SettingActivity.this.getResources().getStringArray(R.array.exceptions)[arg2];
//                SharedPreferences.Editor editor = userSettings.edit();
//                editor.putString("julidier",cardNumber);
//                editor.commit();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }

        });
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
                editor.putString("yewudizhi",et_yewudizhi.getText().toString());
                editor.putString("dizhi",et_dizhi.getText().toString());
                editor.putString("julidiyi",julidiyistr);
                editor.putString("julidier",julidierstr);
                Constants.NET_URL = et_dizhi.getText().toString();
                Constants.NET_URLAPI = et_dizhi.getText().toString();
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

